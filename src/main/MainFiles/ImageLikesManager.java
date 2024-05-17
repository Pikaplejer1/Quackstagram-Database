package MainFiles;

import Database.DatabaseInstance;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages like actions on images and notifies observers of any changes in the likes state.
 * Extends ImageDetailsReader to leverage its image-related functionalities.
 */
public class ImageLikesManager extends ImageDetailsReader {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.connect();

    private final List<Observer> observers;

    /**
     * Constructor for ImageLikesManager.
     * Initializes a FileManager for handling likes data and registers observers for updates.
     *
     * @throws IOException If there is an error in reading likes from the file.
     */
    public ImageLikesManager() throws IOException {
        observers = new ArrayList<>();
    }

    /**
     * Registers an observer to be notified of like state changes.
     *
     * @param observer The observer to be registered.
     */
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifies all registered observers of a like state change.
     *
     * @param imageId    The ID of the image that was liked or unliked.
     * @param likesLabel The JLabel displaying the number of likes.
     */
    public void notifyObservers(String imageId, JLabel likesLabel) {
        for (Observer observer : observers) {
            observer.update(imageId, likesLabel);
        }
    }

    protected void handleLikeAction(String postId) {

        //TODO get current User from singleton

        Instant timestamp = Instant.now();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO likes (Post_id, Username_who_liked, Timestamp)" +
                            "VALUES (?, ?, ?)");

            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, CURRENT_USER);
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
    }




    protected void handleUnlikeAction(String postId) {

        try {

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM likes WHERE Post_id = ? AND Username_who_liked = ?");

            preparedStatement.setString(1, postId);
            preparedStatement.setString(2, currentUsername);

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

    protected boolean isAlreadyLiked(String postId){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM likes WHERE Post_id = ? AND Username_who_liked = ?");

            preparedStatement.setString(1, postId);
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

    protected int countLikes(String postId){
        int likes = 0;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(Post_id) AS result FROM likes WHERE Post_id = ? ");

            preparedStatement.setString(1, postId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    likes = resultSet.getInt("total");
                }
            }

            return likes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


