package MainFiles;

import javax.swing.*;
import java.io.IOException;

/**
 * Observer implementation for handling like actions on images.
 * It observes the like state and updates the UI based on like/unlike actions.
 */
public class LikeObserver implements Observer {

    private int isLiked = 0; // Track the like status within the observer

    /**
     * Constructor for LikeObserver.
     */
    public LikeObserver() {
    }

    /**
     * Updates the like status of an image based on the observer's state.
     * Handles the UI updates for like/unlike actions.
     *
     * @param imageId    The ID of the image being liked or unliked.
     * @param likesLabel The JLabel displaying the number of likes.
     */
    public void update(String imageId, JLabel likesLabel) {
        ImageLikesManager likeManager;
        try {
            likeManager = new ImageLikesManager(); // Instance of ImageLikesManager to handle like actions
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageId = likeManager.extractImageId(imageId); // Extracts the image ID from the given path

        if (isLiked == 0) {
            likeManager.handleLikeAction(imageId, likesLabel); // Handles like action
            isLiked = 1; // Updates the like status to 'liked'
        } else if (isLiked == 1) {
            likeManager.handleUnlikeAction(imageId, likesLabel); // Handles unlike action
            isLiked = 0; // Updates the like status to 'unliked'
        } else {
            System.out.println("Like is not working");
        }
    }
}
