package Login;

import Database.GetCredentials;
import MainFiles.*;
import UIFiles.*;
import Utils.DataType;

import javax.swing.*;
import java.io.*;

/**
 * Manages the sign-in process by verifying credentials and handling the login outcome.
 */
public class HandleCredentials {
    private final LoginResultListener listener;

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
                InstagramProfileUI profileUI = new InstagramProfileUI();
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
        if(password.equals(getCredentials.getUserData(username,DataType.PASSWORD))){
            String bio = getBio(username);
            User user = User.getInstance(username,bio,password);
            return true;
        }
        return false;
    }

    private String getBio(String username) {
        GetCredentials getBio = new GetCredentials();
        return getBio.getUserData(username, DataType.BIO);
    }
}