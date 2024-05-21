package UIFiles;


import javax.swing.*;

import Buttons.ButtonFactory;
import Buttons.SaveButton;
import Buttons.UploadImageButton;
import MainFiles.User;
import java.awt.*;

/**
 * Provides the user interface for editing a user's profile in Quackstagram.
 * It includes functionalities for updating the profile image and bio.
 */
public class ProfileEditUI extends BaseUI {
    private User user;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private JLabel imagePreviewLabel;

    /**
     * Constructor for ProfileEditUI.
     *
     * @param user The User object representing the profile being edited.
     */
    public ProfileEditUI(User user){

        //taken from: image upload UI
        super("Edit Profile");
        this.user = user;
        initializeUI();
    }

    /**
     * Initializes the components for the profile editing interface.
     */
    private void initializeUI() {

        JPanel headerPanel = createHeaderPanel(" Edit Profile 🐥");
        JPanel navigationPanel = createNavigationPanel();

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Image preview
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setAlignmentX(CENTER_ALIGNMENT);
        imagePreviewLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));

        // Set an initial empty icon to the imagePreviewLabel
        ImageIcon emptyImageIcon = new ImageIcon();
        imagePreviewLabel.setIcon(emptyImageIcon);

        contentPanel.add(imagePreviewLabel);

        // Bio text area
        JTextArea bioTextArea = createTextArea("Enter bio");
        JScrollPane bioScrollPane = createScrollPane(bioTextArea);
        contentPanel.add(bioScrollPane);

        // Upload button
        UploadImageButton uploadButton = new UploadImageButton("Upload image", user, imagePreviewLabel);
        contentPanel.add(uploadButton);

        // Save button (for bio)
        SaveButton saveButton = new SaveButton("Save changes", user, bioTextArea, null);
        contentPanel.add(saveButton);


        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private JTextArea createTextArea(String text){

        JTextArea textArea = new JTextArea(text);
        textArea.setAlignmentX(CENTER_ALIGNMENT);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        return textArea;
    }



}