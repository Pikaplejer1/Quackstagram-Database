package Login;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import Database.GetCredentials;
import MainFiles.FilePathInstance;
import Utils.CredentialsDataType;

/**
 * Manages profile-related functionalities such as registration and checking if a username exists.
 * Extends ProfilePictureManager to leverage its profile picture handling capabilities.
 */
public class ProfileManager extends ProfilePictureManager {

    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    private final CreateUser createUser = new CreateUser();
    private final SignUpResultListener listener;

    /**
     * Constructor for ProfileManager.
     *
     * @param listener A SignUpResultListener to handle the outcomes of registration attempts.
     */
    public ProfileManager(SignUpResultListener listener) {
        this.listener = listener;
    }

    /**
     * Checks if a given username already exists in the credentials file.
     *
     * @param username The username to check for existence.
     * @return true if the username exists, false otherwise.
     */
    public boolean doesUsernameExist(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFile.credentialsNamePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validates if the given username is valid and not already in use.
     *
     * @param username The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    public boolean isValidInput(String username) {
        GetCredentials getUsername = new GetCredentials();
        return !username.equals(getUsername.getUserData(username, CredentialsDataType.USERNAME));
    }

    /**
     * Handles the register button click event.
     * Registers a new user if the input is valid, and notifies the listener of the result.
     *
     * @param username The desired username for registration.
     * @param password The desired password for registration.
     * @param bio      The bio information for the user.
     */
    public void onRegisterClicked(String username, String password, String bio) {
        if (isValidInput(username)) {
            createUser.saveCredentials(username, HashingUtil.toHash(password), bio);
            listener.onSuccess(username, password, bio);
            createFolder(username);
        } else {
            listener.onFailure();
        }
    }

    public static void createFolder(String username) {
        Path path = Paths.get("posts/" + username);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Folder created successfully at " + path.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to create folder: " + e.getMessage());
            }
        } else {
            System.out.println("Folder already exists at " + path.toAbsolutePath());
        }
    }
}
