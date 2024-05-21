package Buttons;

import Database.UpdateCredentials;
import MainFiles.User;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import UIFiles.ImageUploadUI;

/**
 * Custom JButton for uploading user profile images.
 * This button allows users to select and upload a new profile image.
 */
public class UploadImageButton extends JButton implements ActionListener {

    private String newFileName;
    private User user;
    private File newProfilePicture = null;
    private Path destPath;
    private JLabel imagePreviewLabel;

    /**
     * Constructs an UploadImageButton with specified text, user, and image preview label.
     *
     * @param text             The text to display on the button.
     * @param user             The user who is uploading the profile picture.
     * @param imagePreviewLabel A JLabel to display the preview of the selected image.
     */
    public UploadImageButton(String text, User user, JLabel imagePreviewLabel) {
        super(text);
        this.user = user;
        this.imagePreviewLabel = imagePreviewLabel;
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.addActionListener(this);
    }

    /**
     * Invoked when an action occurs on the button.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        uploadAction();
    }

    /**
     * Handles the action of uploading a profile picture.
     * Lets the user select an image file, saves it, and updates the image preview.
     */
    public void uploadAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            deleteOldProfilePicture();
            newProfilePicture = fileChooser.getSelectedFile();
            try {
                // Construct the final file name and path
                String fileExtension = ImageUploadUI.getFileExtension(newProfilePicture);
                newFileName = user.getUsername() + "." + fileExtension;
                String relativePath = Paths.get("pfp", newFileName).toString();
                destPath = Paths.get("pfp", newFileName);
                System.out.println(destPath);

                // Directly copy the file to the destination path
                Files.copy(newProfilePicture.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

                UpdateCredentials updatePhotoDir = new UpdateCredentials();
                updatePhotoDir.updateProfilePicture(relativePath, User.getInstance().getUsername());

                // Update the image preview
                ImageIcon imageIcon = new ImageIcon(destPath.toString());
                updateImagePreview(imageIcon);

                JOptionPane.showMessageDialog(this, "Image uploaded and preview updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteOldProfilePicture() {
        String[] possibleExtensions = {".png", ".jpg", ".jpeg", ".gif"};
        boolean fileDeleted = false;

        for (String extension : possibleExtensions) {
            Path path = Paths.get("img/storage/profile/" + user.getUsername() + extension);
            System.out.println("Checking for file: " + path); // Debug output

            if (Files.exists(path)) {
                try {
                    Files.delete(path);
                    System.out.println("Deleted file: " + path); // Debug output
                    fileDeleted = true;
                    break; // Exit the loop once the file is found and deleted
                } catch (Exception e) {
                    System.err.println("Failed to delete file: " + path); // Detailed logging
                    e.printStackTrace(); // Detailed logging
                }
            }
        }

        if (!fileDeleted) {
            System.out.println("No profile picture found to delete for user: " + user.getUsername());
        }
    }

    /**
     * Updates the image preview label with a scaled version of the selected image.
     *
     * @param imageIcon ImageIcon of the newly selected image.
     */
    private void updateImagePreview(ImageIcon imageIcon) {
        if (imagePreviewLabel.getWidth() > 0 && imagePreviewLabel.getHeight() > 0) {
            Image image = imageIcon.getImage();
            // Calculate and set the scaled image
            int scaledWidth, scaledHeight;
            double scale = calculateScale(image.getWidth(null), image.getHeight(null));
            scaledWidth = (int) (scale * image.getWidth(null));
            scaledHeight = (int) (scale * image.getHeight(null));
            imageIcon.setImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        }
        imagePreviewLabel.setIcon(imageIcon);
    }

    /**
     * Calculates the scale factor for the image to fit into the preview label.
     *
     * @param imageWidth  The width of the original image.
     * @param imageHeight The height of the original image.
     * @return The scale factor to fit the image within the preview label.
     */
    private double calculateScale(int imageWidth, int imageHeight) {
        int previewWidth = imagePreviewLabel.getWidth();
        int previewHeight = imagePreviewLabel.getHeight();
        double widthRatio = (double) previewWidth / imageWidth;
        double heightRatio = (double) previewHeight / imageHeight;
        return Math.min(widthRatio, heightRatio);
    }
}
