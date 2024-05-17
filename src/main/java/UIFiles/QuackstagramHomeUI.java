package UIFiles;

import MainFiles.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Provides the user interface for the home screen of Quackstagram.
 * It displays a feed of images along with their likes and descriptions.
 */
public class QuackstagramHomeUI extends BaseUI {
    private static final int IMAGE_WIDTH = WIDTH - 60;
    private static final int IMAGE_HEIGHT = 250;
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel homePanel;
    private JPanel imageViewPanel;
    //private User user = HandleCredentials.getMainUser();
    private ImageLikesManager likeManager;

    private LikeObserver likeObserver; // Declare LikeObserver as a field

    /**
     * Constructor for QuackstagramHomeUI.
     * Initializes the UI components and sets up the layout.
     */
    public QuackstagramHomeUI() {
        super("Quakstagram Home");
        try {
            this.likeManager = new ImageLikesManager(); // Initialize the class field
            this.likeObserver = new LikeObserver(); // Initialize LikeObserver
            this.likeManager.registerObserver(likeObserver); // Register LikeObserver
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeUI();
        createHeaderPanel("🐥 Quackstagram 🐥");
        add(createNavigationPanel(), BorderLayout.SOUTH);
    }

    /**
     * Initializes the components of the home UI.
     */
    private void initializeUI() {
        JPanel contentPanel = createContentPanel();
        JScrollPane scrollPane = createScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        populateContentPanel(contentPanel, likeManager.createSampleData());
    }

    /**
     * Populates the content panel with image posts.
     *
     * @param panel The panel to be populated.
     * @param sampleData Sample data representing the image posts.
     */
    private void populateContentPanel(JPanel panel, String[][] sampleData) {
        for (String[] postData : sampleData) {
            JPanel itemPanel = createItemPanel(postData);
            panel.add(itemPanel);
            panel.add(createSpacingPanel());
        }
    }

    /**
     * Creates a panel for each image post item.
     *
     * @param postData Data for a single image post.
     * @return The item panel JPanel.
     */
    private JPanel createItemPanel(String[] postData) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(BACKGROUND_COLOR);
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel nameLabel = new JLabel(postData[0]);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel imageLabel = createImageLabel(postData[3]);
        JPanel textPanel = createTextPanel(postData[1]);
        JPanel likesPanel = createLikesPanel(postData[2], postData[3]);

        itemPanel.add(nameLabel);
        itemPanel.add(imageLabel);
        itemPanel.add(textPanel);
        itemPanel.add(likesPanel);

        return itemPanel;
    }

    private JLabel createImageLabel(String imagePath) {
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (imagePath == null || imagePath.isEmpty()) {
            imageLabel.setText("Image path is invalid");
            return imageLabel;
        }

        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            imageLabel.setText("Image not found");
            return imageLabel;
        }

        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            BufferedImage resizedImage = resizeImage(originalImage);
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            imageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            imageLabel.setText("Error loading image");
        }

        return imageLabel;
    }


    private BufferedImage resizeImage(BufferedImage originalImage) {
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
        int newWidth = IMAGE_WIDTH;
        int newHeight = (int) (IMAGE_WIDTH / aspectRatio);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    private JPanel createTextPanel(String description) {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setBackground(BACKGROUND_COLOR);
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(descriptionLabel);
        return textPanel;
    }

    private JPanel createLikesPanel(String likes, String imageId) {
        JPanel likesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        likesPanel.setBackground(BACKGROUND_COLOR);
        JLabel likesLabel = new JLabel(likes);
        JButton likeButton = createLikeButton(imageId, likesLabel);
        likesPanel.add(likeButton);
        likesPanel.add(likesLabel);
        return likesPanel;
    }

    private JButton createLikeButton(String imageId, JLabel likesLabel) {


        try {
            ImageLikesManager likeManager = new ImageLikesManager();
            Observer observer = new LikeObserver();
            likeManager.registerObserver(observer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JButton likeButton = new JButton("❤");
        likeButton.setBackground(LIKE_BUTTON_COLOR);
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false);
        likeButton.addActionListener(e -> {
            likeManager.notifyObservers(imageId, likesLabel);
            System.out.println("CLICKED LIKE");
        });
        return likeButton;
    }

    private JPanel createSpacingPanel() {
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(WIDTH - 10, 5));
        spacingPanel.setBackground(new Color(230, 230, 230));
        return spacingPanel;
    }


    private void displayImage(String[] postData) {
        imageViewPanel.removeAll();

        String imageId = new File(postData[3]).getName().split("\\.")[0];
        JLabel likesLabel = new JLabel(postData[2]);

        JLabel fullSizeImageLabel = new JLabel();
        fullSizeImageLabel.setHorizontalAlignment(JLabel.LEFT);

        try {
            ImageLikesManager likeManager = new ImageLikesManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedImage originalImage = ImageIO.read(new File(postData[3]));
            BufferedImage croppedImage = originalImage.getSubimage(0, 0, Math.min(originalImage.getWidth(), WIDTH - 20), Math.min(originalImage.getHeight(), HEIGHT - 40));
            ImageIcon imageIcon = new ImageIcon(croppedImage);
            fullSizeImageLabel.setIcon(imageIcon);
        } catch (IOException ex) {
            fullSizeImageLabel.setText("Image not found");
        }

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel userName = new JLabel(postData[0]);
        userName.setFont(new Font("Arial", Font.BOLD, 18));
        userPanel.add(userName);

        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR);
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false);
        likeButton.addActionListener(e -> {
            //likeManager.handleLikeAction(imageId, likesLabel);
            refreshDisplayImage(postData, imageId);
        });

        JPanel infoPanel = new JPanel(new BorderLayout());
        JLabel descriptionLabel = new JLabel(postData[1]);
        likesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(descriptionLabel, BorderLayout.WEST);
        infoPanel.add(likesLabel, BorderLayout.CENTER);
        infoPanel.add(likeButton, BorderLayout.EAST);

        imageViewPanel.add(fullSizeImageLabel, BorderLayout.CENTER);
        imageViewPanel.add(infoPanel, BorderLayout.SOUTH);
        imageViewPanel.add(userPanel, BorderLayout.NORTH);

        imageViewPanel.revalidate();
        imageViewPanel.repaint();

        cardLayout.show(cardPanel, "ImageView");
    }

    private void refreshDisplayImage(String[] postData, String imageId) {
        try (BufferedReader reader = Files.newBufferedReader(pathFile.imageDetailsPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ImageID: " + imageId)) {
                    String likes = line.split(", ")[4].split(": ")[1];
                    postData[2] = "Likes: " + likes;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayImage(postData);
    }
}




