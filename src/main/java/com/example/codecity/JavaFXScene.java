package com.example.codecity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class JavaFXScene extends Application{

    public static void start(Stage stage, String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Code City");
        stage.setScene(scene);
        stage.show();
    }
}
