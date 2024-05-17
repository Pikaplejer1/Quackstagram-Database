package Database;

import Login.HashingUtil;

public class userSingleton {

    private userSingleton instance=null;

    userSingleton(){
        instance = new userSingleton();
    }

    private userSingleton(String username, String bio, String password) {
        this.username = username;
        this.bio = bio;
        this.password = password;
        this.postsCount = 0;
        this.followersCount = 0;
        this.followingCount = 0;
    }
    public userSingleton getInstance() {
        if(instance ==null){
            instance = new userSingleton();
        }
        return instance;
    }

    private String username;
    private String bio;
    private String password;
    private int postsCount;
    private int followersCount;
    private int followingCount;


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

}
