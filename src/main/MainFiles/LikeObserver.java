package MainFiles;

import javax.swing.*;
import java.io.IOException;

/**
 * Observer implementation for handling like actions on images.
 * It observes the like state and updates the UI based on like/unlike actions.
 */
public class LikeObserver implements Observer {

    /**
     * Constructor for LikeObserver.
     */
    public LikeObserver() {
    }
    public void update(String postId, JLabel likesLabel) {
        ImageLikesManager likeManager;
        try {
            likeManager = new ImageLikesManager(); // Instance of ImageLikesManager to handle like actions
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //set likes label


        if (!likeManager.isAlreadyLiked(postId)) {

            likeManager.handleLikeAction(postId); // Handles like action

        } else if (likeManager.isAlreadyLiked(postId)) {

            likeManager.handleUnlikeAction(postId); // Handles unlike action

        } else {
            System.out.println("Like is not working");
        }

        int likes = likeManager.countLikes(postId);
        likesLabel.setText("Likes: " + likes);
    }
}
