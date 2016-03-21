package seng302;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seng302.gui.RootController;


public class App extends Application {
    Stage primaryStage = new Stage();
    Environment env;

    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        env = new Environment();
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/newGui.fxml"));

            Parent root = loader.load();


            //Parent root = loader;
            Scene scene = new Scene(root);



            primaryStage.setScene(scene);
            primaryStage.setTitle("Allegro");

            RootController controller = loader.getController();
            if(controller == null ) System.out.println("Controller is null");
            controller.setEnvironment(new Environment());
            controller.setStage(primaryStage);
            primaryStage.show();




        } catch (Exception e){
            e.printStackTrace();
        }

    }



}
