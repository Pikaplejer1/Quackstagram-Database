package Buttons;

import MainFiles.FollowManager;
import MainFiles.User;
import javax.swing.*;

/**
 * Implements FollowButtonBehaviour to manage the actions associated with a 'Following' button.
 * This class is responsible for creating a button indicating the current user is following another user.
 * The button toggles the follow state when clicked.
 */
public class FollowingBehaviour implements FollowButtonBehaviour {

    private FollowManager followManager = new FollowManager();

    /**
     * Creates and returns a JButton configured for unfollowing a user.
     * The button is initialized with the text 'Following' and an action listener is added to handle the click event.
     *
     * @param user The User object for which the following button is created.
     * @return A JButton configured with text 'Following' and an action listener.
     */
    public JButton createButton(User user, JFrame window) {
        JButton button = new JButton("Following");
        button.addActionListener(e -> {
            whenClicked(user, button, followManager);
        });
        return button;
    }

    /**
     * Defines the action to be performed when the 'Following' button is clicked.
     * The action changes the button state to 'Follow' or 'Following' and updates the follow status accordingly.
     *
     * @param user          The user who is being followed or unfollowed.
     * @param button        The JButton object triggering the action.
     * @param followManager The FollowManager instance responsible for handling the follow/unfollow logic.
     */
    private void whenClicked(User user, JButton button, FollowManager followManager) {
        FollowButton.followingAction(user, button, followManager);
    }
}
