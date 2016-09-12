package seng302.managers;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.layout.AnchorPane;

/**
 * This class is responsible for setting the GUI to display in a user's theme colours.
 */
public class ThemeHandler {

    AnchorPane baseNode;

    String primaryColour, secondaryColour;

    /**
     * Sets window to which attach the developed css.
     *
     * @param node The Node to which apply the CSS class.
     */
    public void setBaseNode(AnchorPane node) {
        this.baseNode = node;

    }

    /**
     * Creates a theme CSS file given a primary and secondary colours, then links the created CSS to
     * a Node (this.baseNode)
     *
     * @param primary   primary theme colour
     * @param secondary secondary theme colour
     */
    public void setTheme(String primary, String secondary) {

        this.primaryColour = primary;
        this.secondaryColour = secondary;
        generateStyleSheet(primary, secondary);
        setNodeCss(this.baseNode);

    }

    /**
     * Links the created css to a specified node.
     */
    private void setNodeCss(AnchorPane node) {
        if (node != null) {
            node.getStylesheets().clear();
            String filePath = "userstyle.css";
            File f = new File(filePath);
            node.getStylesheets().clear();
            node.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        }

    }


    /**
     * Returns a suitable font colour (black or white) depending on the given background colour.
     * @param colString background colour to determine suitable font colour from.
     * @return colour string (either 'black' or 'white'
     */
    private static String generateComplementFontColor(String colString){


        Function<Integer, Integer> validateRGB = i -> i > 255 ? 255 : i;

        String complCol;

        System.out.println(colString);
        Pattern p = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = p.matcher(colString);

        java.awt.Color col;

        if (m.matches()) {
            int r  = validateRGB.apply(Integer.valueOf(m.group(1)));
            int g  = validateRGB.apply(Integer.valueOf(m.group(2)));
            int b  = validateRGB.apply(Integer.valueOf(m.group(3)));

            col = new Color(r, g, b);
        } else {
            try {
                col = java.awt.Color.decode(colString);
            } catch (Exception e) {
                col = Color.decode("#1E88E5"); //Default blue
            }
        }



        if ((float) ((float) ((float) col.getRed() * 0.299f) + (float) (col.getGreen() * 0.587f) + (float) (col.getBlue() * 0.144f)) > 186) {

            return "black";

        } else return "white";


    }


    /**
     * Creates the stylesheet for the application to use.
     *
     * @param baseRGB color the user selected
     * @param ldRGB   lighter or darker color generated by the theme
     */
    public void generateStyleSheet(String baseRGB, String ldRGB) {
        this.primaryColour = baseRGB;
        this.secondaryColour = ldRGB;


        String primaryFont;
        String secondaryFont;

        primaryFont = generateComplementFontColor(baseRGB);

        secondaryFont = generateComplementFontColor(ldRGB);

        ArrayList<String> templateCSS = new ArrayList<String>();

        String line = null;

        try {
            FileReader fileReader =
                    new FileReader(getClass().getResource("/css/templatecss.txt").getFile());

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("{0}{1}{2}{3}")) { //Primary, Secondary, Primary font colour, secondary font colour.

                    templateCSS.add(MessageFormat.format(line, "", "", "", secondaryFont));
                } else if (line.contains("{0}{1}{2}")) {

                    templateCSS.add(MessageFormat.format(line, "", "", primaryFont));
                } else if (line.contains("{0}{1}")) {
                    templateCSS.add(MessageFormat.format(line, "", ldRGB));
                } else if (line.contains("{0}")) {
                    templateCSS.add(MessageFormat.format(line, baseRGB));

                } else {
                    templateCSS.add(line);
                }
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String fileName = "userstyle.css";

        try {
            FileWriter fileWriter =
                    new FileWriter(fileName);

            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            for (String rule : templateCSS) {
                bufferedWriter.write(rule);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPrimaryColour() {
        return primaryColour;
    }

    public String getSecondaryColour() {
        return secondaryColour;
    }

    /**
     * Updates the theme handler to use the default colours of orange and white.
     */
    public void setDefaultTheme() {
        setTheme("#1E88E5", "white");
    }


}
