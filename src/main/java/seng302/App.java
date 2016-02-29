package seng302;

public class App {
    public static void main( String[] args )
    {
        // Here we are taking the program arguments as the command to be executed.
        // Instead we would change this to take whatever is typed into the textfield.
        new DslExecutor(new Environment()).executeCommand(String.join(" ", args));
    }
}
