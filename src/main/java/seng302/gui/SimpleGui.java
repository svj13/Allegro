package seng302.gui;

import seng302.App;
import seng302.DslExecutor;
import seng302.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleGui {
    DslExecutor executor = new DslExecutor(new Environment());
    private JFrame mainFrame;


    public SimpleGui() {
        prepareGui();
    }

    private void prepareGui() {
        mainFrame = new JFrame("Allegro");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 2));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonFrame = new JPanel(); //adding a panel for the "Go" button
        buttonFrame.setPreferredSize(new Dimension(40, 40));
        mainFrame.add(buttonFrame);
        //buttonFrame.setSize(40,40);
        //buttonFrame.setLayout(new Gridlayout(1,1));

        // Creates and sets the menu bar
        final JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openTranscript = new JMenuItem("Open Transcript"); //open transcript file option
        JMenuItem saveTranscript = new JMenuItem("Save Transcript"); //save transcript file option
        JMenuItem quit = new JMenuItem("Quit"); //quit file option
        quit.addActionListener(new exitApp());
        fileMenu.add(openTranscript);
        fileMenu.add(saveTranscript);
        fileMenu.add(quit);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        // Creates the area for displaying all previous commands
        JTextArea outputText = new JTextArea();
        outputText.setLineWrap(true);
        outputText.setEditable(false);
        JScrollPane outputPane = new JScrollPane(outputText);
        mainFrame.add(outputPane);
        final JTextField tField = new JTextField();
        mainFrame.add(tField);
        Button go = new Button("Go");
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //This is where we call the method to read from text field.
                String text = tField.getText();
                tField.setText("");
                executor.executeCommand(text);
            }
        });
        buttonFrame.add(go);

        // Creates listener for the textField and sets focus
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                tField.requestFocusInWindow();
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