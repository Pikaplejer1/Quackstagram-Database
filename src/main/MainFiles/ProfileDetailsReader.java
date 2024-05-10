package MainFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Reads and sets various details related to a user's profile, including image count, followers, following, and bio.
 */
public class ProfileDetailsReader {

    private final FilePathInstance pathFile = FilePathInstance.getInstance();

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
        int followersCount = 0;

        // Read following.txt to calculate followers and following
        Path followingFilePath = pathFile.followingPath();
        try (BufferedReader followingReader = Files.newBufferedReader(followingFilePath)) {
            String line;
            while ((line = followingReader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String[] followingUsers = parts[1].split(";");
                    if (username.equals(currentUser.getUsername())) {
                        followingCount = followingUsers.length;
                    } else {
                        for (String followingUser : followingUsers) {
                            if (followingUser.trim().equals(currentUser.getUsername())) {
                                followersCount++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentUser.setFollowersCount(followersCount);
        currentUser.setFollowingCount(followingCount);
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
