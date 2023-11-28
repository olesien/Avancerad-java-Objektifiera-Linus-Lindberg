package edu.object.java23object;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent parent = loader.load();//Load the fxml

        Controller controller = loader.getController(); //Get controller ref before scene is made
        controller.setStage(stage); //Set the stage
        Scene scene = new Scene(parent, 420, 370); //Based on the loaded fxml, set the scene
        stage.setScene(scene);
        controller.init(); //Initialize the controller code, this is to load the things that are supposed to happen after start
        stage.setTitle("Order Overview");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}