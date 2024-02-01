package com.example.codecity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.codecity.cloner.CloneDialogue;
import org.eclipse.jgit.api.errors.GitAPIException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onRepoButtonClicked() {
        CloneDialogue.cloneRepo();
    }
}