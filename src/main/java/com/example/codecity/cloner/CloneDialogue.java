package com.example.codecity.cloner;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.Optional;

public class CloneDialogue {

    private static final String DEFAULT_CLONE_DIRECTORY = "./code";

    public static void cloneRepo() {
        String url = promptUserInput("Git URL", "Git Url Required", "Please enter a valid repository url.");
        if (url.isEmpty()) {
            return;
        }


        try {
            cloneRepo(url);
        } catch (GitAPIException e) {
            handleCloneException(e, url);
        }
    }

    private static void cloneRepo(String url) throws GitAPIException {
        Git.cloneRepository()
                .setURI(url)
                .setDirectory(new File(CloneDialogue.DEFAULT_CLONE_DIRECTORY))
                .call();
    }

    private static void cloneWithAuth(String url, String token) {
        try {
            Git.cloneRepository()
                    .setURI(url)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, ""))
                    .setDirectory(new File(DEFAULT_CLONE_DIRECTORY))
                    .call();
        } catch (GitAPIException e) {
            showAlert("Clone Failed", "Failed to clone repository with provided credentials.");
        }
    }

    private static void handleCloneException(GitAPIException e, String url) {
        if (e.getMessage().contains("Authentication is required")) {
            String token = promptUserInput("Authentication Required", "Authentication Required", "Please enter valid auth token.");
            if (token.isEmpty()) {
                showAlert("Clone Failed", "Authentication Token Required");
                return;
            }
            cloneWithAuth(url, token);
        } else {
            showAlert("Clone Failed", e.getMessage());
        }
    }


    private static String promptUserInput(String title, String header, String contentText) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(contentText);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    private static void showAlert(String title, String content) {
        // Implementation of alert dialog (e.g., using JavaFX Alert class) to show error messages or info to the user.
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
    }
}
