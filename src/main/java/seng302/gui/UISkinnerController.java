package seng302.gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.Environment;

import java.io.*;
import java.lang.Math;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;

public class UISkinnerController {

    @FXML
    private HBox settings;

    @FXML
    ColorPicker colourPicker;

    @FXML
    void skinGui(ActionEvent event) {
        skinNode();
    }

    private AnchorPane baseNode;
    private Environment env;
    private ArrayList<String> rules = new ArrayList<String>();

    public UISkinnerController() {
    }

    public void create(Environment env, AnchorPane node) {
        this.env = env;
        this.baseNode = node;
        generateRules();
    }

    private String toRGBString(Color color) {
        int red = (int) (color.getRed()*255);
        int blue = (int) (color.getBlue()*255);
        int green = (int) (color.getGreen()*255);
        return (String.format("rgb(%s, %s, %s)", red, green, blue));
    }

    private Color getComplementaryColour(Color color) {
        Color comp_color;
        int red = (int) (color.getRed()*255);
        int blue = (int) (color.getBlue()*255);
        int green = (int) (color.getGreen()*255);
        double newRed = Math.sqrt(Math.pow(255, 2)-Math.pow(red, 2)) / 255;
        double newBlue = Math.sqrt(Math.pow(255, 2)-Math.pow(blue, 2)) / 255;
        double newGreen = Math.sqrt(Math.pow(255, 2)-Math.pow(green, 2)) / 255;
        comp_color = new Color(newRed, newBlue, newGreen, 1);
        return comp_color;
    }


    private void skinNode() {
        Color base = colourPicker.getValue();
        String baseRgb = toRGBString(base);
        Color comp_colour = getComplementaryColour(base);
        String complementary_rgb = toRGBString(comp_colour);
        String styleString = "";
        generateStyleSheet(baseRgb);

        baseNode.getStylesheets().clear();
        String filePath = "userstyle.css";
        File f = new File(filePath);
        baseNode.getStylesheets().clear();
        baseNode.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

//        for (String rule : rules) {
//            styleString = styleString.concat(String.format(rule, baseRgb) + "; ");
//        }
//        baseNode.setStyle(styleString);
    }

    private void generateRules() {
        //rules.add(".text { -fx-fill: %s}");
        rules.add("-fx-border-color: %s");
        rules.add("-fx-background: %s");
    }

    private void generateStyleSheet(String rgb) {
        ArrayList<String> templateCSS = new ArrayList<String>();

        String line = null;

        try {
            FileReader fileReader =
                    new FileReader(getClass().getResource("/css/templatecss.txt").getFile());

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("{0}")) {
                    templateCSS.add(MessageFormat.format(line, rgb));
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
}
