package UIFiles;

import Buttons.ButtonFactory;
import Buttons.ButtonType;
import Buttons.NavigationButton;
import MainFiles.FilePathInstance;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.nio.file.Path;

/**
 * Base class for creating user interfaces in Quackstagram.
 * Provides common UI components and functionality used across different screens.
 */
public abstract class BaseUI extends JFrame {

    protected static final int WIDTH = 300;
    protected static final int HEIGHT = 500;
    private static final int NAV_ICON_SIZE = 20; // Corrected static size for bottom icons
    private final FilePathInstance pathFile = FilePathInstance.getInstance();


    /**
     * Constructor for BaseUI.
     *
     * @param text The title text for the UI window.
     */
    protected BaseUI (String text){
        setTitle(text);
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }


    protected static JPanel createHeaderPanel(String text) {

        // Header with the Register label
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
        JLabel lblRegister = new JLabel(text);

        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE); // Set the text color to white

        headerPanel.add(lblRegister);
        headerPanel.setPreferredSize(new Dimension(WIDTH, 40)); // Give the header a fixed height

        return headerPanel;
    }


    protected JPanel createNavigationPanel() {

        ButtonFactory factory = new ButtonFactory();
        // Create and return the navigation panel

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        navigationPanel.add(createIconButton(pathFile.homeIconNamePath(), factory.createButton(ButtonType.HOME)));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(pathFile.searchIconNamePath(),factory.createButton(ButtonType.EXPLORE)));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(pathFile.addIconNamePath(),factory.createButton(ButtonType.IMAGE_UPLOAD)));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(pathFile.heartIconNamePath(),factory.createButton(ButtonType.NOTIFICATIONS)));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton(pathFile.profileIconNamePath(), factory.createButton(ButtonType.PROFILE)));

        return navigationPanel;
    }


    protected JButton createIconButton(String iconPath, NavigationButton buttonType) {

        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            this.dispose();
            buttonType.performAction();
        });

        return button;
    }
    protected JScrollPane createScrollPane(JComponent imageGridPanel){

        JScrollPane scrollPane = new JScrollPane(imageGridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }
    protected JComponent createTextField(boolean isPassword, String text) {
        JComponent field;

        if(!isPassword){
            field = new JTextField(text);
        } else {
            field = new JPasswordField(text);
        }
        setColors(field);
        field.setToolTipText(text);

        return field;
    }

    protected JLabel createPicture(String imagePath) {
        JLabel photo = new JLabel();
        photo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        photo.setPreferredSize(new Dimension(80,55));
        photo.setHorizontalAlignment(JLabel.CENTER);
        photo.setVerticalAlignment(JLabel.CENTER);
        photo.setIcon(new ImageIcon(imagePath));
        Image img = new ImageIcon(imagePath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        photo.setIcon(new ImageIcon(img));
        JPanel photoPanel = new JPanel(); // Use a panel to center the photo label
        photoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        photoPanel.add(photo);

        return photo;
    }
    protected JPanel createPanel(Color backgroundColor){
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        return panel;
    }
    protected JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        return button;
    }

    protected JPanel createContentPanel(){

        // Content Panel for notifications
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        return contentPanel;
    }

    private void setColors(JComponent field) {
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
    }
    protected void clear(){
        getContentPane().removeAll(); // Clear existing components
        setLayout(new BorderLayout()); // Reset the layout manager
    }
    protected void refresh(){
        repaint();
        revalidate();
    }

    protected void calculateImageDimensions(Path destPath, JLabel imagePreviewLabel) {
        ImageIcon imageIcon = new ImageIcon(destPath.toString());

        // Check if imagePreviewLabel has a valid size
        if (imagePreviewLabel.getWidth() > 0 && imagePreviewLabel.getHeight() > 0) {
            Image image = imageIcon.getImage();

            // Calculate the dimensions for the image preview
            int previewWidth = imagePreviewLabel.getWidth();
            int previewHeight = imagePreviewLabel.getHeight();
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            double widthRatio = (double) previewWidth / imageWidth;
            double heightRatio = (double) previewHeight / imageHeight;
            double scale = Math.min(widthRatio, heightRatio);
            int scaledWidth = (int) (scale * imageWidth);
            int scaledHeight = (int) (scale * imageHeight);

            // Set the image icon with the scaled image
            imageIcon.setImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        }

        imagePreviewLabel.setIcon(imageIcon);
    }
    protected void addHoverText(JTextComponent component, String placeholder){
        component.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(component.getText().equals(placeholder)){
                    component.setText("");
                    component.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(component.getText().isEmpty()){
                    component.setForeground(Color.GRAY);
                    component.setText(placeholder);
                }
            }
        });
    }

    protected void addHoverText(JComponent component, String placeHolder, JLabel textFocused) {

        if (component instanceof JPasswordField) {
            JPasswordField passwordComponent = (JPasswordField) component;
            //textFocused.setText(placeHolder);
            passwordComponent.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // Clear the text when the field gains focus
                    if (new String(passwordComponent.getPassword()).isEmpty()) {
                        textFocused.setText("");
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    // Restore the text when the field loses focus
                    if (new String(passwordComponent.getPassword()).isEmpty()) {
                        textFocused.setText(placeHolder);
                    }
                }
            });
        }

        else if (component instanceof JTextField) {
            JTextField usernameComponent = (JTextField) component;
            usernameComponent.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (usernameComponent.getText().isEmpty()) {
                        textFocused.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (usernameComponent.getText().isEmpty()) {
                        textFocused.setText(placeHolder);
                    }
                }
            });
        }
    }

    protected JFileChooser configureFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
        return fileChooser;
    }
}
