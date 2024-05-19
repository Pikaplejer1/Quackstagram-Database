package UIFiles;

import MainFiles.FilePathInstance;
import MainFiles.User;
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

        File imageDir = new File("img/uploaded");

        if (imageDir.exists() && imageDir.isDirectory()) {

            File[] imageFiles = imageDir.listFiles((dir, name) -> name.matches(".*\\.(png|jpg|jpeg)"));
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

    //TODO co to lol?
    private void extractImageDetails(String imagePath ){

        // Extract image ID from the imagePath
        String imageId = new File(imagePath).getName().split("\\.")[0];

        Path detailsPath = pathFile.imageDetailsPath();
        try (Stream<String> lines = Files.lines(detailsPath)) {
            String details = lines.filter(line -> line.contains("ImageID: " + imageId)).findFirst().orElse("");
            if (!details.isEmpty()) {
                String[] parts = details.split(", ");
                username = parts[1].split(": ")[1];
                bio = parts[2].split(": ")[1];
                System.out.println(bio+"this is where you get an error "+parts[3]);
                timestampString = parts[3].split(": ")[1];
                likes = Integer.parseInt(parts[4].split(": ")[1]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle exception
        }
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

        final String finalUsername = username;
        JButton usernameLabel = new JButton(username);

        usernameLabel.addActionListener(e -> {
            User user = User.getInstance(); // Assuming User class has a constructor that takes a username
            InstagramProfileUI profileUI = new InstagramProfileUI();

            profileUI.setVisible(true);
            dispose(); // Close the current frame
        });

        return usernameLabel;
    }

    //TODO troche chujowe to liczenie czasu - zrobilam takie dla powiadomien ale musi byc typ danej timestamp a nie string
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