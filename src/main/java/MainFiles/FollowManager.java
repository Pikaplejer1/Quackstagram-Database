package MainFiles;

import Buttons.*;
import Database.DatabaseInstance;
import Notifications.NotificationsCreator;

import javax.swing.*;
import java.sql.*;
import java.time.Instant;

/**
 * Implements the FollowButtonBehaviour to provide specific actions for a Follow button.
 * This class manages the behavior of a button that allows users to follow each other.
 */
public class FollowManager{
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();
    NotificationsCreator creator = new NotificationsCreator();
    User currentUser = User.getInstance();

    public JButton decideType(User userToTakActionOn, JFrame window, JPanel statsPanel){

        JButton finalButton = new JButton();

        FollowButtonBehaviour follow = new FollowBehaviour();
        FollowButtonBehaviour editProfile = new EditProfileBehaviour();
        FollowButtonBehaviour following = new FollowingBehaviour();

        FollowButton followButton = new FollowButton(follow);
        FollowButton editProfileButton = new FollowButton(editProfile);
        FollowButton followingButton = new FollowButton(following);

        boolean thisUser = isCurrentUser(userToTakActionOn);

        if (thisUser) {

            finalButton = editProfileButton.createButton(userToTakActionOn, window, statsPanel);

        } else if ((!thisUser )&& (isAlreadyFollowed(currentUser.getUsername(), userToTakActionOn.getUsername()))) {

            finalButton = followingButton.createButton(userToTakActionOn, window, statsPanel);

        } else if ((!thisUser) && (!isAlreadyFollowed(currentUser.getUsername(), userToTakActionOn.getUsername()))){

            finalButton = followButton.createButton(userToTakActionOn, window, statsPanel);

        } else {
            System.out.println("It doesnt work");
        }

        window.revalidate();
        window.repaint();

         return finalButton;
    }


    public void handleUnFollowAction(String usernameToUnFollow) {
        try {

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM followers WHERE username_followed = ? AND username_following = ?");

            preparedStatement.setString(1, usernameToUnFollow);
            preparedStatement.setString(2, currentUser.getUsername());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Unfollowed successfully.");
            } else {
                System.out.println("No matching follow record found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleFollowAction(String usernameToFollow) {

        Instant timestamp = Instant.now();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO followers (username_followed, username_following, timestamp)" +
                    "VALUES (?, ?, ?)");
            preparedStatement.setString(1, usernameToFollow);
            preparedStatement.setString(2, currentUser.getUsername());
            preparedStatement.setTimestamp(3, Timestamp.from(timestamp));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Followed successfully.");
            } else {
                System.out.println("Something went wrong");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //creator.newNotification( usernameToFollow, "follow");
    }

    private boolean isAlreadyFollowed(String currentUser, String userToFollow){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM followers WHERE username_followed = ? AND username_following = ?");

            preparedStatement.setString(1, userToFollow);
            preparedStatement.setString(2, currentUser);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private boolean isCurrentUser(User userToTakeActionOn) {

        return userToTakeActionOn.getUsername().equals(currentUser.getUsername());
    }
}