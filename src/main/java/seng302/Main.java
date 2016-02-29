package seng302;

public class Main {
    public static void main( String[] args )
    {
        new App(new Environment()).executeCommand(String.join(" ", args));
    }
}
