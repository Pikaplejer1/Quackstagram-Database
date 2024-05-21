package UIFiles;

import Database.PostDatabase;
import MainFiles.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Provides the user interface for the home screen of Quackstagram.
 * It displays a feed of images along with their likes and descriptions.
 */
public class QuackstagramHomeUI extends BaseUI {
    private static final int IMAGE_WIDTH = WIDTH - 60;
    private static final int IMAGE_HEIGHT = 250;
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private String[] postData;
    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel homePanel;
    private JPanel imageViewPanel;
    private ImageLikesManager likeManager;
    private LikeObserver likeObserver;

    /**
     * Constructor for QuackstagramHomeUI.
     * Initializes the UI components and sets up the layout.
     */
    public QuackstagramHomeUI() {
        super("Quakstagram Home");
        try {
            this.likeManager = new ImageLikesManager(); // Initialize the class field
            this.likeObserver = new LikeObserver(); // Initialize LikeObserver with the same ImageLikesManager instance
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
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        homePanel = createContentPanel();
        JScrollPane scrollPane = createScrollPane(homePanel);
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        populateContentPanel(homePanel, likeManager.createSampleData());

        imageViewPanel = new JPanel(new BorderLayout());

        cardPanel.add(scrollPane, "Home");
        cardPanel.add(imageViewPanel, "ImageView");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "Home");
    }

    /**
     * Populates the content panel with image posts.
     *
     * @param panel The panel to be populated.
     * @param sampleData Sample data representing the image posts.
     */
    private void populateContentPanel(JPanel panel, String[][] sampleData) {
        for (String[] postData1 : sampleData) {
            postData = postData1;
            JPanel itemPanel = createItemPanel(postData1);
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
        JButton likeButton = new JButton("❤");
        likeButton.setBackground(LIKE_BUTTON_COLOR);
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false);
        likeButton.addActionListener(e -> {
            likeObserver.update(imageId, likesLabel);
            // Update the likes label immediately
            likesLabel.setText("Likes: " + likeManager.countLikes(imageId));
            System.out.println("CLICKED LIKE");
            // Ensure the UI is updated properly
            likesLabel.revalidate();
            likesLabel.repaint();
        });
        return likeButton;
    }

    private JPanel createSpacingPanel() {
        JPanel spacingPanel = new JPanel();
        spacingPanel.setPreferredSize(new Dimension(WIDTH - 10, 5));
        spacingPanel.setBackground(new Color(230, 230, 230));
        return spacingPanel;
    }


}
