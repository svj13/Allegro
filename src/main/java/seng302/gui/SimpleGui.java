package seng302.gui;

import seng302.DslExecutor;
import seng302.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGui {
    //DslExecutor executor = new DslExecutor(new Environment());
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
        outputText = new JTextArea();
        outputText.setLineWrap(true);
        outputText.setEditable(false);
        executor = new DslExecutor(new Environment(outputText));
        JScrollPane outputPane = new JScrollPane(outputText);
        mainFrame.add(outputPane);
        this.tField = new JTextField();
        mainFrame.add(tField);
        Button go = new Button("Go");


        mainFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"goEvent");
        //Binds the enter key to the goAction.
        mainFrame.getRootPane().getActionMap().put("goEvent",new AbstractAction(){
            public void actionPerformed(ActionEvent ae){
                goAction();

            }
        });


        //Click listener;
        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goAction();
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