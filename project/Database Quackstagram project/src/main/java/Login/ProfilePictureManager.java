package Login;
import MainFiles.FilePathInstance;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manages the profile picture for user profiles.
 * Includes functionalities for uploading, saving, deleting, and updating profile pictures.
 */
public class ProfilePictureManager {
    private static final String IMAGE_FILE_EXTENSION = "png";
    private static final String IMAGE_FILE_TYPE = "Image file";
    private static String PROFILE_IMAGE_STORAGE_PATH;
    private final FilePathInstance pathFile = FilePathInstance.getInstance();

    /**
     * Constructor for ProfilePictureManager.
     * Initializes the storage path for profile images.
     */
    ProfilePictureManager() {
        PROFILE_IMAGE_STORAGE_PATH = pathFile.storageProfileNamePath();
    }

    /**
     * Handles the process of uploading and updating a new profile picture.
     *
     * @param frame    The parent JFrame for the file chooser dialog.
     * @param username The username of the user uploading the picture.
     */
    protected void handleProfilePictureUpload(JFrame frame, String username) {
        JFileChooser fileChooser = configureFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            updateImage(username, selectedFile);
        }
    }

    /**
     * Configures a file chooser for selecting an image file.
     *
     * @return A configured JFileChooser.
     */
    private JFileChooser configureFileChooser() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(IMAGE_FILE_TYPE, ImageIO.getReaderFileSuffixes());
        jFileChooser.setFileFilter(filter);
        return jFileChooser;
    }

    /**
     * Saves the selected profile picture to the storage path.
     *
     * @param selectedFile The file selected by the user.
     * @param username     The username of the user to whom the picture will be associated.
     * @throws IOException If an error occurs during file saving.
     */
    private static void saveProfilePicture(File selectedFile, String username) throws IOException {
        BufferedImage image = ImageIO.read(selectedFile);
        Path outputPath = Paths.get(PROFILE_IMAGE_STORAGE_PATH, username + "." + IMAGE_FILE_EXTENSION);
        ImageIO.write(image, IMAGE_FILE_EXTENSION, outputPath.toFile());
    }

    /**
     * Deletes an existing profile picture file.
     *
     * @param fileName The name of the file to be deleted.
     */
    private static void deleteFile(String fileName) {
        File fileToDelete = new File(fileName);
        if (fileToDelete.exists()) {
            if (!fileToDelete.delete()) {
                System.out.println("Error deleting file " + fileName);
            }
        }
    }

    /**
     * Updates the user's profile picture with a new image file.
     *
     * @param username The username of the user whose profile image is being updated.
     * @param newImage The new image file to be set as the profile picture.
     */
    public static void updateImage(String username, File newImage) {
        try {
            String existingFilePath = Paths.get(PROFILE_IMAGE_STORAGE_PATH, username + "." + IMAGE_FILE_EXTENSION).toString();
            deleteFile(existingFilePath); // Delete the existing image file
            saveProfilePicture(newImage, username); // Save the new image file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
