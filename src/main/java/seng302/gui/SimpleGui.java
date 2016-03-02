package seng302.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGui {
    private JFrame mainFrame;

    public SimpleGui() {
        prepareGui();
    }

    private void prepareGui() {
        mainFrame = new JFrame("Allegro");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));
    }


    public void display(){
        mainFrame.setVisible(true);
    }
}