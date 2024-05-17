package Login;

import Database.GetCredentials;
import MainFiles.FilePathInstance;

/**
 * Manages profile-related functionalities such as registration and checking if a username exists.
 * Extends ProfilePictureManager to leverage its profile picture handling capabilities.
 */
public class ProfileManager extends ProfilePictureManager {

    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    private final CreateUser createUser = new CreateUser();
    private final SignUpResultListener listener;

    /**
     * Constructor for ProfileManager.
     *
     * @param listener A SignUpResultListener to handle the outcomes of registration attempts.
     */
    public ProfileManager(SignUpResultListener listener) {
        this.listener = listener;
    }

    /**
     * Handles the register button click event.
     * Registers a new user if the input is valid, and notifies the listener of the result.
     *
     * @param username The desired username for registration.
     * @param password The desired password for registration.
     * @param bio      The bio information for the user.
     */
    public void onRegisterClicked(String username, String password, String bio) {
        GetCredentials getCredentials = new GetCredentials();
        if(doesUsernameExists(username)){
            createUser.saveCredentials(username,password,bio);
            listener.onSuccess(username,password,bio);
        } else
            listener.onFailure();



//        if (isValidInput(username)) {
//            createUser.saveCredentials(username, HashingUtil.toHash(password), bio);
//            listener.onSuccess(username, password, bio);
//        } else {
//            listener.onFailure();
//        }
    }

    public boolean doesUsernameExists(String username) {
        GetCredentials getCredentials = new GetCredentials();
        return getCredentials.doesUsernameExists(username);
    }
}
