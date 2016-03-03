package seng302;

import seng302.gui.SimpleGui;
import seng302.data.Notes;

public class App {


    public static void main( String[] args )
    {
        SimpleGui simpleGui = new SimpleGui();
        simpleGui.display();

        //new Notes();

        Notes object = Notes.getInstance();
    }
}
