package Buttons;

import MainFiles.User;
import UIFiles.ProfileEditUI;
import javax.swing.*;

/**
 * Implements the FollowButtonBehaviour to provide a specific action for an Edit Profile button.
 * When clicked, this button opens the user's profile editing interface.
 */
public class EditProfileBehaviour implements FollowButtonBehaviour {

    /**
     * Creates and returns a JButton with an action listener.
     * When clicked, it opens a profile editing UI for the specified user.
     *
     * @param user The User object for whom the profile editing UI should be opened.
     * @return A JButton configured with text and an action listener.
     */
    public JButton createButton(User user, JFrame window, JPanel statsPanel) {

        JButton button = new JButton("Edit Profile");
        button.addActionListener(e -> {
            window.dispose();
            whenClicked(user, button);
        });
        return button;
    }

    /**
     * Defines the action to be taken when the button is clicked.
     * Opens the ProfileEditUI for the given user.
     *
     * @param user   The user whose profile is to be edited.
     * @param button The JButton object that triggers this action.
     */
    private void whenClicked(User user, JButton button) {
        ProfileEditUI profileEditUI = new ProfileEditUI(user);
        profileEditUI.setVisible(true);
    }
}
