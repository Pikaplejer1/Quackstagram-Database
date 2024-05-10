package MainFiles;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages like actions on images and notifies observers of any changes in the likes state.
 * Extends ImageDetailsReader to leverage its image-related functionalities.
 */
public class ImageLikesManager extends ImageDetailsReader {

    private final List<Observer> observers;
    private final String likesFilePath = "data/likes.txt";
    private final FileManager fileManager;
    private Map<String, Set<String>> likesMap;

    /**
     * Constructor for ImageLikesManager.
     * Initializes a FileManager for handling likes data and registers observers for updates.
     *
     * @throws IOException If there is an error in reading likes from the file.
     */
    public ImageLikesManager() throws IOException {
        fileManager = new FileManager(likesFilePath);
        likesMap = fileManager.readLikes();
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

    /**
     * Handles the action of liking an image.
     * Updates the image details to reflect the new like count and notifies observers.
     *
     * @param imageId    The ID of the image being liked.
     * @param likesLabel The JLabel displaying the number of likes.
     */
    protected void handleLikeAction(String imageId, JLabel likesLabel) {

        Path detailsPath = pathFile.imageDetailsPath();
        StringBuilder newContent = new StringBuilder();
        boolean updated = false;
        String currentUser = getCurrentUser();
        String imageOwner = "";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        try (BufferedReader reader = Files.newBufferedReader(detailsPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ImageID: " + imageId)) {
                    String[] parts = line.split(", ");
                    imageOwner = parts[1].split(": ")[1];
                    int likes = Integer.parseInt(parts[4].split(": ")[1]);
                    likes++;
                    parts[4] = "Likes: " + likes;
                    line = String.join(", ", parts);
                    likesLabel.setText("Likes: " + likes);
                    updated = true;
                }
                newContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (updated) {
            updateImageDetailsFile(detailsPath, newContent.toString());
            recordLikeInNotifications(imageOwner, currentUser, imageId, timestamp);
        }
    }

    /**
     * Handles the action of unliking an image.
     * Updates the image details to reflect the new like count.
     *
     * @param imageId    The ID of the image being unliked.
     * @param likesLabel The JLabel displaying the number of likes.
     */
    protected void handleUnlikeAction(String imageId, JLabel likesLabel) {
        Path detailsPath = pathFile.imageDetailsPath();
        StringBuilder newContent = new StringBuilder();
        boolean updated = false;
        String currentUser = getCurrentUser();
        String imageOwner = "";

        try (BufferedReader reader = Files.newBufferedReader(detailsPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ImageID: " + imageId)) {
                    String[] parts = line.split(", ");
                    imageOwner = parts[1].split(": ")[1];
                    int likes = Integer.parseInt(parts[4].split(": ")[1]);
                    if (likes > 0) {
                        likes--;
                        parts[4] = "Likes: " + likes;
                        line = String.join(", ", parts);
                        likesLabel.setText("Likes: " + likes);
                        updated = true;
                    }
                }
                newContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (updated) {
            updateImageDetailsFile(detailsPath, newContent.toString());
            // Since unliking doesn't need to record notifications, we don't call recordLikeInNotifications method here.
        }
    }

}


