package com.example.codecity;


import com.example.codecity.cloner.CloneDialogue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Code City");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        CloneDialogue.deleteCodeDirectory();
        launch();
    }
}