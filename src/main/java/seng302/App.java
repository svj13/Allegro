package seng302;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seng302.gui.RootController;


public class App extends Application {
    Stage primaryStage ;
    Environment env;

    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        env = new Environment();
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/newGui.fxml"));

            Parent root = loader.load();


            //Parent root = loader;
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            //Font.loadFont(ClassLoader.getSystemResource("/css/fonts/Roboto-Medium.tff").toExternalForm(), 24);


            primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Allegro - Unsaved Project");
            primaryStage.setMinHeight(450);
            primaryStage.setMinWidth(450);

            RootController controller = loader.getController();
            if (controller == null) System.err.println("Controller is null");
            controller.setEnvironment(new Environment());
            controller.setStage(primaryStage);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
