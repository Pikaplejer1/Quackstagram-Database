package Login;

import Database.UpdateCredentials;
import MainFiles.FilePathInstance;
import MainFiles.User;
import java.io.*;
import java.sql.SQLException;

/**
 * Handles the creation and updating of user credentials.
 * This class provides functionality to save and update user details like username, password, and bio.
 */
public class CreateUser {
    private final FilePathInstance pathFile = FilePathInstance.getInstance();

    /**
     * Saves user credentials to a file.
     * Writes the username, password, and bio to the credentials file.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @param bio      The user's bio.
     */
    public void saveCredentials(String username, String password, String bio) {
        UpdateCredentials updateCredentials = new UpdateCredentials();
        try {
            updateCredentials.createUser(username,password,bio);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the bio of an existing user.
     * Reads the credentials file and writes the updated information to a temporary file,
     * which is then renamed to the original file name.
     *
     * @param updatedUser The user object with updated information.
     */
    public void updateUser(User updatedUser, String prevUser){
        UpdateCredentials updateUser = new UpdateCredentials();
        updateUser.updateUser(updatedUser, prevUser);
    }
}
