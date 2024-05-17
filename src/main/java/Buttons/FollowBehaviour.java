package Buttons;

import MainFiles.FollowManager;
import MainFiles.User;
import javax.swing.*;

/**
 * Implements the FollowButtonBehaviour to provide specific actions for a Follow button.
 * This class manages the behavior of a button that allows users to follow each other.
 */
public class FollowBehaviour implements FollowButtonBehaviour {

    // Instance of FollowManager to handle follow actions.
    private FollowManager followManager = new FollowManager();

    /**
     * Creates and returns a JButton with an action listener for follow functionality.
     * When the button is clicked, it performs follow action for the specified user.
     *
     * @param user The user to be followed or unfollowed.
     * @return A configured JButton with the text "Follow" and an action listener.
     */
    @Override
    public JButton createButton(User user, JFrame window) {
        JButton button = new JButton("Follow");

        button.addActionListener(e -> {
            whenClicked(user, button, followManager);
        });

        return button;
    }

    /**
     * Defines the action to be taken when the follow button is clicked.
     * Calls the followAction method from FollowButton to toggle the follow state for the current user.
     *
     * @param currentUser   The user who will perform the follow/unfollow action.
     * @param button        The JButton object that triggers this action.
     * @param followManager The manager that handles the follow functionality.
     */
    private void whenClicked(User currentUser, JButton button, FollowManager followManager) {
        FollowButton.followingAction(currentUser, button, followManager);
    }
}
