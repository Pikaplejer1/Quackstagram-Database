package MainFiles;

import Buttons.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implements the FollowButtonBehaviour to provide specific actions for a Follow button.
 * This class manages the behavior of a button that allows users to follow each other.
 */
public class FollowManager{
    private final FilePathInstance pathFile = FilePathInstance.getInstance();

    /**
     * Handles the action of following a user.
     * Updates the following file to reflect the current user following another user.
     *
     * @param usernameToFollow The username of the user to be followed.
     */
    public void handleFollowAction(String usernameToFollow){
        String currentUserUsername = getCurrentUsername();
        if(!currentUserUsername.isEmpty()){
            processFollowingFile(currentUserUsername, usernameToFollow);
        }
    }
    /**
     * Handles the action of unfollowing a user.
     * Updates the following file to reflect the current user unfollowing another user.
     *
     * @param usernameToUnFollow The username of the user to be unfollowed.
     */

    public void handleUnFollowAction(String usernameToUnFollow){

        String currentUserUsername = getCurrentUsername();

        if(!currentUserUsername.isEmpty()){
            processUnFollowingFile(currentUserUsername, usernameToUnFollow);
        }
    }

    /**
     * Decides and returns the appropriate button type based on the relationship with the current user.
     *
     * @param currentUser The user whose profile is being viewed.
     * @return A JButton that reflects the relationship status with the current user.
     */
    public JButton decideType(User currentUser, JFrame window){

        JButton finalButton = new JButton();

        FollowButtonBehaviour follow = new FollowBehaviour();
        FollowButtonBehaviour editProfile = new EditProfileBehaviour();
        FollowButtonBehaviour following = new FollowingBehaviour();

        FollowButton followButton = new FollowButton(follow);
        FollowButton editProfileButton = new FollowButton(editProfile);
        FollowButton followingButton = new FollowButton(following);

        boolean thisUser = isCurrentUser(currentUser);

        if (thisUser) {

            finalButton = editProfileButton.createButton(currentUser, window);

        } else if ((!thisUser )&& (isAlreadyFollowed(currentUser))) {

            finalButton = followingButton.createButton(currentUser, window);

        } else if ((!thisUser) && (!isAlreadyFollowed(currentUser))){

            finalButton = followButton.createButton(currentUser,window);

        } else {
            System.out.println("It doesnt work");
        }

         return finalButton;
    }

    private void processUnFollowingFile(String currentUsername, String usernameToUnFollow) {
        try {
            List<String> fileContent = readFollowingFile();
            String updatedContent = updateUnFollowingContent(fileContent, currentUsername, usernameToUnFollow);
            writeFollowingFile(updatedContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String updateUnFollowingContent(List<String> fileContent, String currentUsername, String usernameToUnFollow) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : fileContent) {
            if (line.startsWith(currentUsername + ":")) {
                String[] parts = line.split(":");
                String followedUsersString = parts[1];
                String[] followedUsers = followedUsersString.split(";");
                StringBuilder updatedFollowedUsers = new StringBuilder();
                for (String followedUser : followedUsers) {
                    if (!followedUser.trim().equals(usernameToUnFollow)) {
                        updatedFollowedUsers.append(followedUser.trim()).append(";");
                    }
                }
                if (updatedFollowedUsers.length() > 0) {
                    updatedFollowedUsers.setLength(updatedFollowedUsers.length() - 1); // Remove trailing semicolon
                }
                stringBuilder.append(parts[0]).append(": ").append(updatedFollowedUsers.toString()).append("\n");
            } else {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private void processFollowingFile(String currentUsername, String usernameToFollow) {
        try {
            List<String> fileContent = readFollowingFile();
            String updatedContent = updateFollowingContent(fileContent, currentUsername, usernameToFollow);
            writeFollowingFile(updatedContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readFollowingFile() throws IOException {
        List<String> lines = new ArrayList<>();
        if (Files.exists(pathFile.followingPath())) {
            lines = Files.readAllLines(pathFile.followingPath());
        }
        return lines;
    }

    private String updateFollowingContent(List<String> fileContent, String currentUsername, String usernameToFollow) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isUserFound = false;
        for (String line : fileContent) {
            if (line.startsWith(currentUsername + ":")) {
                isUserFound = true;
                if (!line.contains(usernameToFollow)) {
                    line += ";" + usernameToFollow;
                }
            }
            stringBuilder.append(line).append("\n");
        }
        if (!isUserFound) {
            stringBuilder.append(currentUsername).append(": ").append(usernameToFollow).append("\n");
        }
        return stringBuilder.toString();
    }

    private void writeFollowingFile(String content) throws IOException {
        Files.write(pathFile.followingPath(), content.getBytes());
    }


    private String getCurrentUsername(){
        String currentUserUsername = null;
        try (BufferedReader reader = Files.newBufferedReader(pathFile.usersPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                currentUserUsername = parts[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return currentUserUsername;
    }


    public boolean isAlreadyFollowed(User currentUser){

        boolean isAlreadyFollowed = false;
        // Check if the current user is already being followed by the logged-in user
        try (BufferedReader reader = Files.newBufferedReader(pathFile.followingPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].trim().equals(loggedInUsername())) {
                    String[] followedUsers = parts[1].split(";");
                    for (String followedUser : followedUsers) {
                        if (followedUser.trim().equals(currentUser.getUsername())) {
                            isAlreadyFollowed = true;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isAlreadyFollowed;
    }

    public String loggedInUsername() {
        String loggedInUsername = "";

        // Read the logged-in user's username from the "users.txt" file
        try (BufferedReader reader = Files.newBufferedReader(pathFile.usersPath())) {
            String line = reader.readLine();
            if (line != null) {
                loggedInUsername = line.split(":")[0].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle or log the exception appropriately
        }

        return loggedInUsername;
    }

    public boolean isCurrentUser(User currentUser) {

        boolean isCurrentUser = false;

        // Check if the username matches the logged-in username
        isCurrentUser = loggedInUsername().equals(currentUser.getUsername());

        // Check if the username is present in the "users.txt" file
        try (Stream<String> lines = Files.lines(pathFile.usersPath())) {
            isCurrentUser = lines.anyMatch(line -> line.startsWith(currentUser.getUsername() + ":"));
        } catch (IOException e) {
            e.printStackTrace();  // Handle or log the exception appropriately
        }

        return isCurrentUser;
    }
}