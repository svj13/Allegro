package seng302;

import seng302.gui.SimpleGui;

public class App
{
    public static void main( String[] args )
    {
        SimpleGui simpleGui = new SimpleGui();
        simpleGui.display();
        new Notes();
    }
}
