package com.example.codecity.cloner;

import com.example.codecity.Window;
import com.example.codecity.jparser.JParser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.Optional;

/**
 * The {@code CloneDialogue} class provides functionalities to clone Git repositories using JGit.
 * It supports cloning both public repositories and those requiring authentication.
 * The class uses JavaFX components to interact with the user, prompting for repository URLs
 * and authentication tokens when necessary.
 */
public class CloneDialogue {


    /**
     * The default directory where the cloned repository will be stored.
     */
    private static final String DEFAULT_CLONE_DIRECTORY = "./code";


    public static void deleteCodeDirectory() {
        File directory = new File(DEFAULT_CLONE_DIRECTORY);
        deleteDirectoryContents(directory);
        // Optionally delete the directory itself if you want
        directory.deleteOnExit();
    }

    private static void deleteDirectoryContents(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectoryContents(file);
                }
                file.delete();
            }
        }
    }

    /**
     * Prompts the user for a Git repository URL and initiates the cloning process.
     * If the repository requires authentication, the user will be prompted for an authentication token.
     * Errors during the cloning process, including authentication issues, are handled and displayed to the user.
     */
    public static void cloneRepo(Stage stage) throws Exception {
        String url = promptUserInput("Git URL", "Git Url Required", "Please enter a valid repository url.");
        if (url.isEmpty()) {
            return;
        }

        try {
            cloneRepo(url);
            stage.close();
            File path = new File("code");
            JParser dir = new JParser(path);
            dir.parseAll();
            Stage newStage = new Stage();
            Window.start(newStage, dir);
        } catch (GitAPIException e) {
            handleCloneException(e, url);
        }
        deleteCodeDirectory();
    }

    /**
     * Clones a Git repository to a predefined directory.
     *
     * @param url The URL of the Git repository to clone.
     * @throws GitAPIException If an error occurs during the cloning process.
     */
    private static void cloneRepo(String url) throws GitAPIException {
        Git.cloneRepository()
                .setURI(url)
                .setDirectory(new File(CloneDialogue.DEFAULT_CLONE_DIRECTORY))
                .call();
    }

    /**
     * Clones a Git repository using an authentication token. This method is called if the initial cloning attempt fails due to authentication requirements.
     *
     * @param url   The URL of the Git repository to clone.
     * @param token The authentication token used for cloning the repository.
     */
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

    /**
     * Handles exceptions thrown during the repository cloning process. If the exception indicates that authentication is required,
     * prompts the user for an authentication token. Otherwise, displays an error message to the user.
     *
     * @param e   The exception encountered during cloning.
     * @param url The URL of the Git repository that was being cloned.
     */
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

    /**
     * Prompts the user for input using a dialog window.
     *
     * @param title       The title of the dialog window.
     * @param header      The header text for the dialog window.
     * @param contentText The content text displayed in the dialog window, prompting the user for input.
     * @return The user's input as a {@code String}. Returns an empty string if the user cancels or closes the dialog.
     */
    private static String promptUserInput(String title, String header, String contentText) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(contentText);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    /**
     * Displays an alert dialog to the user. This can be used to show error messages or other information.
     *
     * @param title   The title of the alert dialog.
     * @param content The content text to be displayed in the alert dialog.
     */
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
    }
}
