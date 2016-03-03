package seng302.gui;

import seng302.DslExecutor;
import seng302.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGui {
    private DslExecutor executor;
    private JFrame mainFrame;
    private JButton goButton;
    private JTextField tField;
    private  JTextArea outputText;
    private DslExecutor executor;


    public SimpleGui() {
        prepareGui();
    }

    private void goAction(){
        String text = tField.getText();
        tField.setText("");
        outputText.append("Command: " + text + "\n");
        executor.executeCommand(text);
    }

    private void prepareGui() {
        mainFrame = new JFrame("Allegro");
        mainFrame.setSize(400, 400);

        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        mainFrame.add(pane);
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

        // Text Field Input
        final JTextField tField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(tField, c);

        // Creates the area for displaying all previous commands
        final JTextArea outputText = new JTextArea();

        // Button
        JButton go = new JButton("Go");
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goAction();
            }
        });
        c.weightx = 0.2;
        c.gridx = 1;
        pane.add(go, c);


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
        SwingUtilities.getRootPane(go).setDefaultButton(go);
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