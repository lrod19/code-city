package com.example.codecity;

import javafx.fxml.FXML;
import com.example.codecity.cloner.CloneDialogue;

public class CodeCityController {

    @FXML
    protected void onRepoButtonClicked() {
        CloneDialogue.cloneRepo();
    }
}