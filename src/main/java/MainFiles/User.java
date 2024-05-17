package MainFiles;

import Login.HashingUtil;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a user on Quackstagram.
 * This class holds user-related information including username, bio, password, and pictures.
 */
public class User {
    private final String username;
    private String bio;
    private String password;
    private int postsCount;
    private int followersCount;
    private int followingCount;
    private User instance = null;

    /**
     * Constructs a new User with the specified username, bio, and password.
     * Initializes picture list and count metrics.
     *
     * @param username The username of the user.
     * @param bio      The biography of the user.
     * @param password The password of the user.
     */
    public User(String username, String bio, String password) {
        this.username = username;
        this.bio = bio;
        this.password = password;
        this.postsCount = 0;
        this.followersCount = 0;
        this.followingCount = 0;
    }


    /**
     * Constructs a new User with the specified username.
     *
     * @param username The username of the user.
     */
    public User(String username) {
        this.username = username;
    }

    // Getter and setter methods
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public int getPostsCount() { return postsCount; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }
    public void setFollowersCount(int followersCount) { this.followersCount = followersCount; }
    public void setFollowingCount(int followingCount) { this.followingCount = followingCount; }
    public void setPostCount(int postCount) { this.postsCount = postCount; }

    /**
     * Returns a string representation of the user.
     *
     * @return A string that represents the user's details.
     */
    @Override
    public String toString() {
        return username + ":" + bio + ":" + password; // Format as needed
    }

    /**
     * Returns a string representation of the user with a hashed password.
     *
     * @return A string that represents the user's details with a hashed password.
     */
    public String toStringHash() {
        return username + ":" + bio + ":" + HashingUtil.toHash(password); // Hashes password
    }

    public User getInstance(String username, String bio, String password) {
        if(instance == null){
            instance = new User(username,bio,password);
        }
        return instance;
    }
    public User getInstance(){
        return instance;
    }

}
