package com.example.codecity;

import javafx.fxml.FXML;
import com.example.codecity.cloner.CloneDialogue;
import javafx.stage.Stage;

import java.io.IOException;


public class CodeCityController {
    @FXML private javafx.scene.control.Button repoButton;
    @FXML
    protected void onRepoButtonClicked() throws Exception {
        Stage stage = (Stage) repoButton.getScene().getWindow();
        CloneDialogue.cloneRepo(stage);
        CloneDialogue.deleteCodeDirectory();
    }
}