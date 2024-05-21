package Buttons;

import Database.UpdateCredentials;
import Login.CreateUser;
import MainFiles.FilePathInstance;
import MainFiles.User;
import UIFiles.InstagramProfileUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * Custom JButton for saving user profile changes.
 * This button handles saving actions such as updating the user's bio and profile picture.
 */
public class SaveButton extends JButton implements ActionListener {

    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    private File newProfilePicture;
    private Path destPath = Path.of(pathFile.storageProfileNamePath());
    private JTextArea bioTextArea;
    private User user;
    private CreateUser createUser = new CreateUser();

    /**
     * Constructs a SaveButton with specified text, user, and bio text area.
     *
     * @param text        The text to display on the button.
     * @param user        The user whose profile is being edited.
     * @param bioTextArea The JTextArea containing the user's bio to be saved.
     */
    public SaveButton(String text, User user, JTextArea bioTextArea, File file) {
        super(text);
        this.user = user;
        this.bioTextArea = bioTextArea;
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.addActionListener(this);
        setNewProfilePicture(file);
    }

    /**
     * Invoked when an action occurs on the button.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        saveProfileAction();
    }

    /**
     * Performs the action of saving the user's profile.
     * Updates the user's profile picture and bio based on the current state.
     * On successful update, the Instagram profile UI is displayed.
     */
    private void saveProfileAction() {
        try {
            // Update user's bio
            User updatedUser = new User(user.getUsername(), bioTextArea.getText(), null);
            createUser.updateUser(updatedUser,User.getInstance().getUsername());
            // Display updated profile UI
            SwingUtilities.invokeLater(() -> {
                InstagramProfileUI profileUI = new InstagramProfileUI(updatedUser);
                profileUI.setVisible(true);
            });

            // Dispose the current frame
            SwingUtilities.getWindowAncestor(this).dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void setNewProfilePicture(File newProfilePicture) {
        this.newProfilePicture = newProfilePicture;
    }
}
