package Login;

import Database.GetCredentials;
import MainFiles.*;
import UIFiles.*;
import javax.swing.*;
import java.io.*;

/**
 * Manages the sign-in process by verifying credentials and handling the login outcome.
 */
public class HandleCredentials {
    private final LoginResultListener listener;
    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    private User newUser;

    /**
     * Constructor for HandleCredentials.
     *
     * @param listener An instance of LoginResultListener to handle the result of the login process.
     */
    public HandleCredentials(LoginResultListener listener) {
        this.listener = listener;
    }

    /**
     * Handles the sign-in button click event.
     * Verifies credentials and triggers the appropriate actions based on verification results.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    void onSignInClicked(String username, String password) {
        System.out.println(username + " <-> " + password);
        if (verifyCredentials(username, password)) {
            System.out.println("It worked");
            SwingUtilities.invokeLater(() -> {
                InstagramProfileUI profileUI = new InstagramProfileUI(newUser);
                profileUI.setVisible(true);
                listener.onLoginSuccess();
            });
        } else {
            System.out.println("It Didn't");
            listener.onLoginFailure();
        }
    }

    /**
     * Verifies the provided username and password against stored credentials.
     *
     * @param username The username to be verified.
     * @param password The password to be verified.
     * @return true if credentials are valid, false otherwise.
     */
    private Boolean verifyCredentials(String username, String password) {
        GetCredentials getCredentials = new GetCredentials();
        return password.equals(getCredentials.getUserPassword(username));
    }

    /**
     * Saves user information to a designated file.
     *
     * @param user The User object containing the information to be saved.
     */
    private void saveUserInformation(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile.usersNamePath(), false))) {
            writer.write(user.toStringHash());  // A toString method in User class to represent user data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the new user object.
     *
     * @return The newly created User object.
     */
    public User getNewUser() {
        return newUser;
    }
}
