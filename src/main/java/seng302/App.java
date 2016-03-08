package seng302;

import seng302.gui.SimpleGui;

public class App {


    public static void main(String[] args) {
        Environment environment = new Environment();
        SimpleGui simpleGui = new SimpleGui(environment);
        simpleGui.display();
    }
}
