package seng302.gui;

import com.jfoenix.controls.JFXColorPicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng302.Environment;
import seng302.utility.ColourUtils;


import java.io.*;
import java.lang.Math;

import java.text.MessageFormat;
import java.util.ArrayList;


public class UISkinnerController {

    @FXML
    private HBox settings;

    @FXML
    ColorPicker colourPicker;


    @FXML
    private JFXColorPicker jfxColourPicker;





    // RGB colour values
    private String baseRGB;
    private String compRGB;
    private float[] lighterRGB;
    private float[] darkerRGB;
    private String lighterRGBString;
    private String darkerRGBString;

    private AnchorPane baseNode;
    private Environment env;
    private ArrayList<String> rules = new ArrayList<String>();



    /**
     * initialises parameters needed for the gui
     *
     * @param env  Environment
     * @param node Node to style (main pane)
     */
    public void create(Environment env, AnchorPane node) {
        this.env = env;
        //this.baseNode = node;
        env.getThemeHandler().setBaseNode(env.getRootController().paneMain);

    }


    /**
     * Applies css to the node given in create. Generates colours based on user selected colour.
     */
    @FXML
    void changeColour(ActionEvent event) {
        Color base = jfxColourPicker.getValue();
        String baseRgb = ColourUtils.toRGBString(base);
        Color comp_colour = ColourUtils.getComplementaryColourString(base);
        setDarkerRGB(base);
        setLighterRGB(base);
        String complementary_rgb = ColourUtils.toRGBString(comp_colour);
        String styleString = "";
        String lighterOrDarker;
        double luma = 0.2126 * (base.getRed() * 255) + 0.7152 * (base.getGreen() * 255) + 0.0722 * (base.getBlue() * 255);
        if (luma < 126) {
            lighterOrDarker = floatToRGBString(lighterRGB);
        } else {
            lighterOrDarker = floatToRGBString(darkerRGB);
        }


        env.getThemeHandler().setTheme(baseRgb, lighterOrDarker);
        env.getUserHandler().getCurrentUser().saveProperties();

    }



    /**
     * Sets a colour lighter than the user selected colour to be used in the theme
     * @param color Color vaue
     */
    private void setLighterRGB(Color color) {
        float[] hsl = ColourUtils.rgbToHsl(ColourUtils.colorToRgb(color));
        float lightness = hsl[2];
        double lCoefficient;
        if (lightness > 0.8) {
            lCoefficient = 3;
        } else if (lightness < 0.6) {
            lCoefficient = 2;
        } else {
            lCoefficient = 1.5;
        }
        float[] lighterHslArray = new float[3];
        lighterHslArray[0] = hsl[0];
        lighterHslArray[1] = hsl[1];
        if (hsl[2] < 0.05) {
            lighterHslArray[2] = (float) 0.15;
        } else {
            lighterHslArray[2] = (float) (hsl[2] * lCoefficient);
        }

        float[] lighterRGBArray = ColourUtils.hslToRgb(lighterHslArray);

        lighterRGB = lighterRGBArray;
    }

    /**
     * Sets a colour darker than the user selected colour to be used in the theme
     * @param color Color value
     */
    private void setDarkerRGB(Color color) {
        float[] hsl = ColourUtils.rgbToHsl(ColourUtils.colorToRgb(color));
        float lightness = hsl[2];
        double lCoefficient;
        if (lightness > 0.8) {
            lCoefficient = 0.7;
        } else if (lightness > 0.4) {
            lCoefficient = 0.5;
        } else {
            lCoefficient = 0.2;
        }
        float[] darkerHslArray = new float[3];
        darkerHslArray[0] = hsl[0];
        darkerHslArray[1] = hsl[1];
        darkerHslArray[2] = (float) (hsl[2] * lCoefficient);


        float[] darkerRGBArray = ColourUtils.hslToRgb(darkerHslArray);


        darkerRGB = darkerRGBArray;
    }


    /**
     * Converts an array of floats to a formatted string.
     * @param rgbArray
     * @return
     */
    private String floatToRGBString(float[] rgbArray) {
        return (String.format("rgb(%s, %s, %s)", rgbArray[0], rgbArray[1], rgbArray[2]));
    }

}
