package UIFiles;

import Database.DatabaseInstance;
import MainFiles.FollowManager;
import MainFiles.User;
import MainFiles.ProfileDetailsReader;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Provides the user interface for displaying a user's profile in Quackstagram.
 * It includes functionalities for viewing profile details, images, and managing follow actions.
 */
public class InstagramProfileUI extends BaseUI {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram", "root", "julia");
    Connection conn = database.getConn();

    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    private final JPanel contentPanel; // Panel to display the image grid or the clicked image
    private User user; // User object to store the current user's information
    private ProfileDetailsReader profileDetailsReader = new ProfileDetailsReader();

    /**
     * Constructor for InstagramProfileUI.
     */
    public InstagramProfileUI(User user) {
        super("");
        this.user = user;
        setSize(WIDTH, HEIGHT);
        contentPanel = new JPanel();
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        profileDetailsReader.readAndSetFollowing(user);
        profileDetailsReader.readAndSetFollowed(user);
        profileDetailsReader.readImageDetails(user);
        profileDetailsReader.bioReader(user);

        System.out.println("Bio for " + user.getUsername() + ": " + user.getBio());
        System.out.println(user.getPostsCount());
        initializeUI();
    }

    /**
     * Initializes the UI components for the profile interface.
     */
    public void initializeUI() {
        getContentPane().removeAll(); // Clear existing components

        // Re-add the header and navigation panels
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createNavigationPanel(), BorderLayout.SOUTH);

        // Initialize the image grid
        initializeImageGrid();
        refresh();
    }

    /**
     * Creates the header panel including profile details and follow button.
     *
     * @return The header panel JPanel.
     */
    private JPanel createHeaderPanel() {
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);

        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = createTopHeaderPanel(user);
        headerPanel.add(topHeaderPanel);

        // Profile Name and Bio Panel
        JPanel profileNameAndBioPanel = createProfileAndBioPanel(user);
        headerPanel.add(profileNameAndBioPanel);

        return headerPanel;
    }

    private JPanel createTopHeaderPanel(User currentUser) {
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Profile Image
        JLabel profileImage = createProfileImage(currentUser);
        topHeaderPanel.add(profileImage, BorderLayout.WEST);

        // Add Stats and Follow Button to a combined Panel
        JPanel statsFollowPanel = createStatsFollowPanel(currentUser);
        topHeaderPanel.add(statsFollowPanel, BorderLayout.CENTER);

        return topHeaderPanel;
    }

    private JPanel createStatsPanel(User currentUser) {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user: " + currentUser.getPostsCount());
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getPostsCount()), "Posts"));
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getFollowersCount()), "Followers"));
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getFollowingCount()), "Following"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding

        return statsPanel;
    }


    private JPanel createProfileAndBioPanel(User currentUser) {
        JPanel profileNameAndBioPanel = new JPanel();
        profileNameAndBioPanel.setLayout(new BorderLayout());
        profileNameAndBioPanel.setBackground(new Color(249, 249, 249));

        JLabel profileNameLabel = createProfileNameLabel(currentUser);
        JTextArea profileBio = createProfileBio(currentUser);

        profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);
        profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

        return profileNameAndBioPanel;
    }

    private JLabel createProfileImage(User currentUser) {
        String[] possibleExtensions = {".png", ".jpg", ".jpeg", ".gif"};
        ImageIcon profileIcon = null;

        for (String extension : possibleExtensions) {
            File file = new File("pfp/" + currentUser.getUsername() + extension);
            if (file.exists()) {
                profileIcon = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
                break;
            }
        }

        if (profileIcon == null) {
            // Handle the case where no image is found, possibly set a default image
            profileIcon = new ImageIcon(new ImageIcon("default-profile.png").getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
        }

        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return profileImage;
    }

    private JTextArea createProfileBio(User currentUser) {
        JTextArea profileBio = new JTextArea(currentUser.getBio());
        System.out.println("This is the bio: " + currentUser.getUsername());
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
        profileBio.setBackground(new Color(249, 249, 249));
        profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding on the sides

        return profileBio;
    }

    private JLabel createProfileNameLabel(User currentUser) {
        JLabel profileNameLabel = new JLabel(currentUser.getUsername());
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding on the sides

        return profileNameLabel;
    }

    private JPanel createStatsFollowPanel(User currentUser) {
        JPanel statsFollowPanel = new JPanel();
        statsFollowPanel.setLayout(new BoxLayout(statsFollowPanel, BoxLayout.Y_AXIS));

        // Stats Panel
        JPanel statsPanel = createStatsPanel(currentUser);
        statsFollowPanel.add(statsPanel);

        FollowManager followManager = new FollowManager();

        JButton button = followManager.decideType(currentUser, this, statsPanel);
        // Adds specific button based on certain conditions
        statsFollowPanel.add(button);

        return statsFollowPanel;
    }

    private void initializeImageGrid() {
        contentPanel.removeAll(); // Clear existing content
        contentPanel.setLayout(new GridLayout(0, 3, 5, 5)); // Grid layout for image grid

        displayUserImages(user.getUsername());

        JScrollPane scrollPane = createScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center

        refresh();
    }

    public void displayUserImages(String username) {
        ArrayList<String> imagePaths = fetchUserImagePaths(username);

        for (String path : imagePaths) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(GRID_IMAGE_SIZE, GRID_IMAGE_SIZE, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    displayImage(imageIcon); // Call method to display the clicked image
                }
            });
            contentPanel.add(imageLabel); // Add imageLabel to the main contentPanel
        }
    }

    private ArrayList<String> fetchUserImagePaths(String username) {
        ArrayList<String> imagePaths = new ArrayList<>();

        String query = "SELECT post_photo_dir FROM Post WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    imagePaths.add(rs.getString("post_photo_dir"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception (e.g., show a message or log)
        }
        return imagePaths;
    }

    private void displayImage(ImageIcon imageIcon) {
        clear();
        initializeUI();

        JPanel imageViewerPanel = new JPanel(new BorderLayout());
        JButton backButtonPanel = new JButton("Back"); // back button
        backButtonPanel.addActionListener(e -> {
            getContentPane().removeAll();
            initializeUI();
            refresh();
        });

        imageViewerPanel.add(new JLabel(imageIcon), BorderLayout.CENTER);
        imageViewerPanel.add(backButtonPanel, BorderLayout.NORTH);

        setContentPane(imageViewerPanel);
        refresh();
    }

    private JLabel createStatLabel(String number, String text) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JScrollPane createScrollPane(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    public void refresh() {
        revalidate();
        repaint();
    }
}
