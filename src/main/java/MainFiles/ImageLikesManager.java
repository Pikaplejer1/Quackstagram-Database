package MainFiles;

import Database.DatabaseInstance;
import Notifications.NotificationsCreator;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;

/**
 * Manages like actions on images and notifies observers of any changes in the likes state.
 * Extends ImageDetailsReader to leverage its image-related functionalities.
 */
public class ImageLikesManager extends ImageDetailsReader {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    NotificationsCreator creator = new NotificationsCreator();
    Connection conn = database.getConn();
    User currentUser = User.getInstance();

    private final List<Observer> observers;

    public ImageLikesManager() throws IOException {
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String imageId, JLabel likesLabel) {
        for (Observer observer : observers) {
            observer.update(imageId, likesLabel);
        }
    }

    protected void handleLikeAction(String postId) {

        Instant timestamp = Instant.now();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO likes (Post_id, Username_who_liked, Timestamp)" +
                            "VALUES (?, ?, ?)");

            preparedStatement.setString(1, getPostId(postId));
            preparedStatement.setString(2, currentUser.getUsername());
            preparedStatement.setTimestamp(3, Timestamp.from(timestamp));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Liked successfully.");
            } else {
                System.out.println("Something went wrong");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //creator.newNotification(getPostsUser(postId), "like");
    }

    public String getPostId(String postDir){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT Post_id FROM Post WHERE post_photo_dir = ? ");

            preparedStatement.setString(1, postDir);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String username = resultSet.getString("Post_id");
                    return username ;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private String getPostsUser(String postId){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT username FROM Post WHERE Post_id = ? ");

            preparedStatement.setString(1, postId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    return username ;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }



    protected void handleUnlikeAction(String postId) {

        try {

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM likes WHERE Post_id = ? AND Username_who_liked = ?");

            preparedStatement.setString(1, getPostId(postId));
            preparedStatement.setString(2, currentUser.getUsername());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 1) {
                System.out.println("dua");
            } else {
                System.out.println("No matching follow record found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isAlreadyLiked(String postId){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM likes WHERE Post_id = ? AND Username_who_liked = ?");

            preparedStatement.setString(1, getPostId(postId));
            preparedStatement.setString(2, currentUser.getUsername());

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

    public int countLikes(String postId){
        int likes = 0;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(Post_id) AS result FROM likes WHERE Post_id = ? ");

            preparedStatement.setString(1, postId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    likes = resultSet.getInt("result");
                }
            }

            return likes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


