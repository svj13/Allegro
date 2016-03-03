package seng302;

import seng302.gui.SimpleGui;
import seng302.data.Notes;
import seng302.Manager;

public class App {


    public static void main( String[] args )
    {
        Manager manager = new Manager();

        SimpleGui simpleGui = new SimpleGui(manager);
        simpleGui.display();

        //new Notes();

        Notes object = Notes.getInstance();
    }
}
