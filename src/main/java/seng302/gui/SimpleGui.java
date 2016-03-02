package seng302.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleGui {
    private JFrame mainFrame;

    public SimpleGui() {
        prepareGui();
    }

    private void prepareGui() {
        mainFrame = new JFrame("Allegro");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 2));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Creates and sets the menu bar
        final JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new exitApp());
        fileMenu.add(quit);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        // Creates the area for displaying all previous commands
        JTextArea outputPane = new JTextArea();
        outputPane.setEditable(false);
        mainFrame.add(outputPane);
        final JTextField tField = new JTextField();
        mainFrame.add(tField);
        Button go = new Button("Go");
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //This is where we call the method to read from text field.
                System.out.println("go");
            }
        });
        mainFrame.add(go);

        // Creates listener for the textField and sets focus
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                tField.requestFocus();
            }
        });
    }

    static class exitApp implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    public void display(){
        mainFrame.setVisible(true);
    }
}