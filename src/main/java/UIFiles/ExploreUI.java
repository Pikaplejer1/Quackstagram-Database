package UIFiles;

import Database.GetCredentials;
import MainFiles.FilePathInstance;
import MainFiles.User;
import Utils.PostDataType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;


/**
 * Provides the user interface for the Explore section in Quackstagram.
 * It includes functionalities to display images and their associated details.
 */
public class ExploreUI extends BaseUI {

    private static final int IMAGE_SIZE = WIDTH / 3; // Size for each image in the grid
    private String username = "";
    private String bio = "";
    private String timestampString = "";
    private int likes = 0;
    private final FilePathInstance pathFile = FilePathInstance.getInstance();

    /**
     * Constructor for ExploreUI.
     * Initializes the UI and sets up the layout.
     */
    public ExploreUI() {

        super("Explore");
        initializeUI();
    }

    /**
     * Initializes the components of the Explore UI.
     */
    private void initializeUI() {

        //clear and reset all components
        clear();

        JPanel headerPanel = createHeaderPanel(" Explore 🐥");
        JPanel navigationPanel = createNavigationPanel();
        JPanel mainContentPanel = createMainContentPanel();

        // Add panels to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the main content panel for the Explore UI.
     * This includes the search panel and the image grid panel.
     *
     * @return The main content JPanel.
     */
    private JPanel createMainContentPanel() {

        JPanel mainContentPanel = createContentPanel();// Create the main content panel with search and image grid
        JPanel searchPanel = createSearchPanel();// Search bar at the top

        // Image Grid
        JPanel imageGridPanel = new JPanel(new GridLayout(0, 3, 2, 2)); // 3 columns, auto rows
        JScrollPane scrollPane = createScrollPane(imageGridPanel);

        loadImages(imageGridPanel);// Load images from the uploaded folder

        mainContentPanel.add(searchPanel);
        mainContentPanel.add(scrollPane); // This will stretch to take up remaining space

        return mainContentPanel;
    }

    public void loadImages(JPanel imageGridPanel) {
        File postsDir = new File("posts");

        if (postsDir.exists() && postsDir.isDirectory()) {
            File[] userDirs = postsDir.listFiles(File::isDirectory);
            if (userDirs != null) {
                for (File userDir : userDirs) {
                    loadUserImages(userDir, imageGridPanel);
                }
            }
        }
    }

    private void loadUserImages(File userDir, JPanel imageGridPanel) {
        File[] imageFiles = userDir.listFiles((dir, name) -> name.matches(".*\\.(png|jpg|jpeg)"));
        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageFile.getPath()).getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        displayImage(imageFile.getPath()); // Call method to display the clicked image
                    }
                });
                imageGridPanel.add(imageLabel);
            }
        }
    }


    private static JPanel createSearchPanel() {
        JTextField searchField = new JTextField("Search Users");
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchField.getPreferredSize().height)); // Limit the height
        return searchPanel;
    }

    private void displayImage(String imagePath) {

        clear();
        initializeUI();

        JPanel imageViewerPanel = new JPanel(new BorderLayout());
        JPanel containerPanel = createContainerPanel(imagePath);
        JPanel backButtonPanel = createBackButtonPanel();// Panel for the back button

        // Add the container panel and back button panel to the frame
        imageViewerPanel.add(containerPanel, BorderLayout.CENTER);
        imageViewerPanel.add(backButtonPanel, BorderLayout.NORTH);

        setContentPane(imageViewerPanel);
        refresh();
    }

    private void extractImageDetails(String imagePath ){
        GetCredentials getPostData = new GetCredentials();
        System.out.println("-------------------");
        username = getPostData.getPostData(imagePath, PostDataType.USERNAME);
        System.out.println(username);
        bio = getPostData.getPostData(imagePath, PostDataType.BIO);
        System.out.println(bio);
        timestampString = getPostData.getPostData(imagePath,PostDataType.TIMESTAMP);
        System.out.println(timestampString);
        likes = Integer.parseInt(getPostData.getPostData(imagePath, PostDataType.LIKES));
        System.out.println(likes);
    }


    public JPanel createContainerPanel(String imagePath) {

        extractImageDetails(imagePath);

        // Container panel for image and details
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(createTopPanel(username, timestampString), BorderLayout.NORTH);// Top panel for username and time since posting
        containerPanel.add(createImageLabel(imagePath), BorderLayout.CENTER);// Prepare the image for display
        containerPanel.add(createBottomPanel(bio, likes), BorderLayout.SOUTH);// Bottom panel for bio and likes
        return containerPanel;
    }

    public JPanel createBackButtonPanel() {
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = createBackButton();
        backButtonPanel.add(backButton);

        return backButtonPanel;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(WIDTH-20, backButton.getPreferredSize().height)); // Make the button take up the full width


        backButton.addActionListener(e -> {
            getContentPane().removeAll();
            initializeUI();
            refresh();
        });
        return backButton;
    }

    private static JPanel createBottomPanel(String bio, int likes) {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextArea bioTextArea = new JTextArea(bio);
        bioTextArea.setEditable(false);

        JLabel likesLabel = new JLabel("Likes: " + likes);
        bottomPanel.add(bioTextArea, BorderLayout.CENTER);
        bottomPanel.add(likesLabel, BorderLayout.SOUTH);
        return bottomPanel;
    }

    private static JLabel createImageLabel(String imagePath) {

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            ImageIcon imageIcon = new ImageIcon(originalImage);
            imageLabel.setIcon(imageIcon);

        } catch (IOException ex) {
            imageLabel.setText("Image not found");
        }
        return imageLabel;
    }

    private JPanel createTopPanel(String username, String timestampString) {
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton usernameLabel = createUsernameLabel(username);

        JLabel timeLabel = createTimeLabel(timestampString);

        topPanel.add(usernameLabel, BorderLayout.WEST);
        topPanel.add(timeLabel, BorderLayout.EAST);

        return topPanel;
    }

    private JButton createUsernameLabel(String username) {

        String finalUsername = username;
        User newUser = new User(finalUsername);
        JButton usernameLabel = new JButton(username);

        usernameLabel.addActionListener(e -> {
            InstagramProfileUI profileUI = new InstagramProfileUI(newUser);

            profileUI.setVisible(true);
            dispose(); // Close the current frame
        });

        return usernameLabel;
    }
    private static JLabel createTimeLabel(String timestampString) {
        String timeSincePosting = calculateTimeSincePosting(timestampString);
        JLabel timeLabel = new JLabel(timeSincePosting);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        return timeLabel;
    }

    private static String calculateTimeSincePosting(String timestampString) {
        // Calculate time since posting
        String timeSincePosting = "Unknown";
        if (!timestampString.isEmpty()) {
            LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime now = LocalDateTime.now();
            long days = ChronoUnit.DAYS.between(timestamp, now);
            timeSincePosting = days + " day" + (days != 1 ? "s" : "") + " ago";
        }
        return timeSincePosting;
    }
}