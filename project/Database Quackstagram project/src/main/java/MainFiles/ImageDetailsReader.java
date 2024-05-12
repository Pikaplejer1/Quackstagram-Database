package MainFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Handles reading and updating details related to images in the application.
 */
public class ImageDetailsReader {

    protected final FilePathInstance pathFile = FilePathInstance.getInstance();

    /**
     * Retrieves the current user's username.
     *
     * @return The username of the currently logged-in user.
     */
    protected String getCurrentUser() {
        String currentUser = "";
        try {
            currentUser = Files.lines(pathFile.usersPath())
                    .findFirst()
                    .orElse("")
                    .split(":")[0]
                    .trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }
    /**
     * Creates sample data for displaying images posted by followed users.
     *
     * @return An array of string arrays representing image data.
     */
    public String[][] createSampleData() {
        String currentUser = getCurrentUser();
        String followedUsers = getFollowedUsers(currentUser);

        String[][] tempData = new String[100][]; // Assuming a maximum of 100 posts
        int count = 0;

        try (BufferedReader reader = Files.newBufferedReader(pathFile.imageDetailsPath())) {
            String line;
            while ((line = reader.readLine()) != null && count < tempData.length) {
                String[] details = line.split(", ");
                String imagePoster = details[1].split(": ")[1];
                if (followedUsers.contains(imagePoster)) {
                    String imageID = details[0].split(": ")[1];
                    String imagePath = findImagePath(imageID);

                    String description = details[2].split(": ")[1];
                    String likes = "Likes: " + details[4].split(": ")[1];

                    tempData[count++] = new String[]{imagePoster, description, likes, imagePath};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] sampleData = new String[count][];
        System.arraycopy(tempData, 0, sampleData, 0, count);

        return sampleData;
    }

    private String findImagePath(String imageID) {
        String[] extensions = {".png", ".jpg", ".jpeg"};
        for (String ext : extensions) {
            String path = "img/uploaded/" + imageID + ext;
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }
        return null; // or a default image path
    }

    /**
     * Extracts the image ID from a given file path.
     *
     * @param path The file path of the image.
     * @return The image ID extracted from the file path.
     */
    protected String extractImageId(String path) {
        String regex = "img/uploaded/(.*)\\..*";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    /**
     * Retrieves a string containing usernames of users followed by the current user.
     *
     * @param currentUser The username of the current user.
     * @return A string of usernames separated by spaces.
     */
    protected String getFollowedUsers(String currentUser) {
        String followedUsers = "";
        try {
            followedUsers = Files.lines(Paths.get("data", "following.txt"))
                    .filter(line -> line.startsWith(currentUser + ":"))
                    .findFirst()
                    .map(line -> line.split(":")[1].trim())
                    .orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return followedUsers;
    }

    /**
     * Updates the image details file with new content.
     *
     * @param detailsPath The path to the image details file.
     * @param newContent  The content to be written to the file.
     */
    protected void updateImageDetailsFile(Path detailsPath, String newContent) {
        try (BufferedWriter writer = Files.newBufferedWriter(detailsPath)) {
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Records a like action in the notifications file.
     *
     * @param imageOwner The username of the owner of the image.
     * @param currentUser The username of the current user who liked the image.
     * @param imageId The ID of the image that was liked.
     * @param timestamp The timestamp of when the like occurred.
     */
    protected void recordLikeInNotifications(String imageOwner, String currentUser, String imageId, String timestamp) {
        String notification = String.format("%s; %s; %s; %s\n", imageOwner, currentUser, imageId, timestamp);
        try (BufferedWriter notificationWriter = Files.newBufferedWriter(Paths.get("data", "notifications.txt"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            notificationWriter.write(notification);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
