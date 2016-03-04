package seng302.gui;

import seng302.DslExecutor;
import seng302.Environment;
import seng302.Manager;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.*;

public class SimpleGui {

    private JFrame mainFrame;
    private JButton goButton;
    private JTextField tField;
    private  JTextArea outputText;
    private DslExecutor executor;
    private Manager manager;
    
    public SimpleGui(Manager manager1) {
        manager = manager1;
        prepareGui();
    }

    private void goAction(){
        String text = tField.getText();
        tField.setText("");
        if (text.length() > 0) {
            outputText.append("Command: " + text + "\n");
            manager.transcriptManager.addText("Command: " + text + "\n");
            executor.executeCommand(text);
        } else {
            outputText.append("[ERROR] Cannot submit an empty command.\n");
        }
    }

    private void prepareGui() {

        mainFrame = new JFrame("Allegro");
        mainFrame.setSize(400, 400);

        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        mainFrame.add(pane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creates and sets the menu bar
        final JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        //launches file browser when open/save transcript is selected from file menu
        JMenuItem openTranscript = new JMenuItem("Open Transcript"); //open transcript file option
        openTranscript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(mainFrame);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                chooser.setFileFilter(filter);
                String path = chooser.getSelectedFile().getAbsolutePath();
                ArrayList<String> text = new ArrayList<String>();
                text = manager.transcriptManager.getText();
                manager.transcriptManager.Open(path);
            }
        });


        JMenuItem saveTranscript = new JMenuItem("Save Transcript"); //save transcript file option
        saveTranscript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                chooser.showSaveDialog(mainFrame);
                String path = chooser.getSelectedFile().getAbsolutePath();
                ArrayList<String> text = new ArrayList<String>();
                text = manager.transcriptManager.getText();
                manager.transcriptManager.Save(path,text);
            }
        });
        JMenuItem quit = new JMenuItem("Quit"); //quit file option
        quit.addActionListener(new exitApp());
        fileMenu.add(openTranscript);
        fileMenu.add(saveTranscript);
        fileMenu.add(quit);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        // Text Field Input
        tField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(tField, c);

        // Creates the area for displaying all previous commands
        outputText = new JTextArea();
        DefaultCaret caret = (DefaultCaret)outputText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // Button
        goButton = new JButton("Go");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goAction();
            }
        });
        c.weightx = 0.2;
        c.gridx = 1;
        pane.add(goButton, c);


        // Sets up OutputText Field
        outputText.setLineWrap(true);
        outputText.setEditable(false);

        executor = new DslExecutor(new Environment(outputText));

        JScrollPane outputPane = new JScrollPane(outputText);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = 1.0;   //request any extra vertical space
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(outputPane, c);

        // Creates listener for the textField and sets focus
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                tField.requestFocusInWindow();
            }
        });

        // Sets button on enter
        SwingUtilities.getRootPane(goButton).setDefaultButton(goButton);
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
