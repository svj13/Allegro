package seng302.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import seng302.DslExecutor;
import seng302.Environment;
import seng302.utility.OutputTuple;


public class SimpleGui {

    private JFrame mainFrame;
    private JButton goButton;
    private JTextField tField;
    private JTextArea outputField;
    private DslExecutor executor;
    private Environment env;
    private String path;


    public SimpleGui(Environment enviroment) {
        this.env = enviroment;
        executor = env.getExecutor();
        prepareGui();
    }

    /**
     * Go button Action
     */
    private void goAction() {
        String text = tField.getText();
        tField.setText("");
        if (text.length() > 0) {
//            outputField.append("Command: " + text + "\n");
//            env.getTranscriptManager().addText("Command: " + text + "\n");
//            executor.executeCommand(text);

            env.getTranscriptManager().setCommand(text);
            executor.executeCommand(text);
            outputField.append(env.getTranscriptManager().getLastCommand());
        } else {
            outputField.append("[ERROR] Cannot submit an empty command.\n");
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
                try {
                    JFileChooser chooser = new JFileChooser(path);
                    chooser.showOpenDialog(mainFrame);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES (*.txt)", "txt");
                    chooser.setFileFilter(filter);
                    path = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        env.getTranscriptManager().Open(path);
                        outputField.setText(env.getTranscriptManager().convertToText());
                    } catch (Exception ex) {
                        System.err.println("Not a valid file");
                    }
                } catch (Exception ex) {
                    System.err.println("file chooser error");
                }
            }
        });


        JMenuItem saveTranscript = new JMenuItem("Save Transcript"); //save transcript file option
        saveTranscript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.showSaveDialog(mainFrame);
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    ArrayList<OutputTuple> text = new ArrayList<OutputTuple>();
                    text = env.getTranscriptManager().getTranscriptTuples();
                    env.getTranscriptManager().Save(path, text);
                } catch (Exception ex) {
                    System.err.println("file chooser error");
                }
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
        outputField = new JTextArea();
        DefaultCaret caret = (DefaultCaret) outputField.getCaret();
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
        outputField.setLineWrap(true);
        outputField.setEditable(false);

//        executor = new DslExecutor(new Environment(outputField));

        JScrollPane outputPane = new JScrollPane(outputField);
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
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public void display() {
        mainFrame.setVisible(true);
    }
}
