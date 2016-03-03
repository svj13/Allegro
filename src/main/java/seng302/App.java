package seng302;

import seng302.gui.SimpleGui;

public class App {


    public static void main( String[] args )
    {
        Manager manager = new Manager();

        SimpleGui simpleGui = new SimpleGui(manager);
        simpleGui.display();


    }
}
