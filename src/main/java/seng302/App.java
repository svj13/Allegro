package seng302;

import javax.swing.*;
import java.awt.*;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to SENG302" );

        //test window

        JFrame window = new JFrame("Allegro");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(800,600);
        window.setPreferredSize(new Dimension(800,600));

        window.getContentPane().add(new JLabel("Enter command: "));
        //window.getContentPane().add(new JTextField());
        window.pack();
        window.setVisible(true);
    }
}
