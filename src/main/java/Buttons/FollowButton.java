package Buttons;

import MainFiles.User;
import MainFiles.FollowManager;
import javax.swing.*;
import java.awt.*;

/**
 * Represents a FollowButton with specific behaviors and styling.
 * The button's functionality is defined by an instance of FollowButtonBehaviour.
 */
public class FollowButton extends JButton {
    private final FollowButtonBehaviour followButtonBehaviour;

    /**
     * Constructs a FollowButton with the specified behavior.
     *
     * @param followButtonBehaviour The behavior that this button should exhibit.
     */
    public FollowButton(FollowButtonBehaviour followButtonBehaviour) {
        this.followButtonBehaviour = followButtonBehaviour;
    }

    /**
     * Creates and returns a styled JButton based on the behavior specified.
     * Sets the alignment, font, maximum size, background color, and other properties for the button.
     *
     * @param user The user for whom the button is created.
     * @return A styled JButton according to the specified behavior.
     */
    public JButton createButton(User user, JFrame window) {
        JButton button = followButtonBehaviour.createButton(user, window);
        // Additional styling and properties set for the button
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setBackground(new Color(225, 228, 232));
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return button;
    }

    /**
     * Static method to handle follow or unfollow actions based on the button's current text.
     * Changes the button's text and state between "Follow" and "Following".
     *
     * @param user          The user who is the subject of the follow/unfollow action.
     * @param button        The button triggering the action.
     * @param followManager The manager handling the follow and unfollow actions.
     */
    public static void followingAction(User user, JButton button, FollowManager followManager) {
        // Implementation of the follow and unfollow functionality
        if (button.getText().equals("Follow")) {
            followManager.handleFollowAction(user.getUsername());
            button.setText("Following");
        } else {
            followManager.handleUnFollowAction(user.getUsername());
            button.setText("Follow");
        }
    }
}
