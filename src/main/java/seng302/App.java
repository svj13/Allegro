package seng302;

import seng302.gui.SimpleGui;

public class App
{
import seng302.data.Notes;

public class App {
    public static void main( String[] args )
    {
        SimpleGui simpleGui = new SimpleGui();
        simpleGui.display();
        // Here we are taking the program arguments as the command to be executed.
        // Instead we would change this to take whatever is typed into the textfield.
        new DslExecutor(new Environment()).executeCommand(String.join(" ", args));
        new Notes();
    }
}
