package MainFiles;

import Database.DatabaseInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads and sets various details related to a user's profile, including image count, followers, following, and bio.
 */
public class ProfileDetailsReader {

    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/quackstagram_db","root","marcel");
    Connection conn = database.connect();

    /**
     * Reads image details and sets the image count for the specified user.
     *
     * @param currentUser The user whose image count needs to be set.
     */
    public static void readImageDetails(User currentUser) {
        int imageCount = 0;
        // Read image_details.txt to count the number of images posted by the user
        Path imageDetailsFilePath = Paths.get("data", "image_details.txt");
        try (BufferedReader imageDetailsReader = Files.newBufferedReader(imageDetailsFilePath)) {
            String line;
            while ((line = imageDetailsReader.readLine()) != null) {
                if (line.contains("Username: " + currentUser.getUsername())) {
                    imageCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentUser.setPostCount(imageCount);
    }

    /**
     * Reads and sets the following and followers count for the specified user.
     *
     * @param currentUser The user whose following and followers count needs to be set.
     */
    public void readAndSetFollowing(User currentUser) {
        int followingCount = 0;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(username_followed) AS result FROM followers WHERE username_followed = ? ");

            preparedStatement.setString(1, currentUser.getUsername());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followingCount = resultSet.getInt("result");
                }
            }

            currentUser.setFollowingCount(followingCount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readAndSetFollowed(User currentUser) {
        int followersCount = 0;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(username_following) AS result FROM followers WHERE username_following = ? ");

            preparedStatement.setString(1, currentUser.getUsername());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followersCount = resultSet.getInt("result");
                }
            }

            currentUser.setFollowersCount(followersCount);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        /**
     * Reads and sets the bio for the specified user.
     *
     * @param currentUser The user whose bio needs to be read and set.
     */
    public void bioReader(User currentUser) {
        String bio = "";
        // Read the user's bio from credentials.txt
        Path bioDetailsFilePath = pathFile.credentialsPath();
        try (BufferedReader bioDetailsReader = Files.newBufferedReader(bioDetailsFilePath)) {
            String line;
            while ((line = bioDetailsReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(currentUser.getUsername()) && parts.length >= 3) {
                    bio = parts[2];
                    break; // Exit the loop once the matching bio is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentUser.setBio(bio);
    }
}