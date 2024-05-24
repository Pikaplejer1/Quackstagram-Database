package Login;

import UIFiles.LoginBaseUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Provides the user interface for registering on Quackstagram.
 * This UI includes fields for username, password, bio entry, and buttons for registration and photo upload.
 */
public class SignUpUI extends LoginBaseUI implements  SignUpResultListener{

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color REGISTER_BUTTON_COLOR = Color.RED;
    private static final Color SIGNIN_BUTTON_COLOR = Color.YELLOW;
    private static final Color UPLOAD_BUTTON_COLOR = Color.YELLOW;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtBio;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblBio;
    private JButton btnRegister, btnUploadPhoto, btnSignIn;

    private ProfileManager profileManager;

    /**
     * Constructor for SignUpUI.
     * Initializes the components and layout for the sign-up interface.
     */
    public SignUpUI() {
        super("Qackstagram - Sign In");
        this.profileManager = new ProfileManager(this);
        initializeComponents();
        layoutComponents();
    }

    @Override
    protected void initializeComponents() {
        txtUsername = (JTextField) createTextField(false, "");
        txtPassword = (JPasswordField) createTextField(true, "");
        txtBio = (JTextField) createTextField(false, "");

        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        lblBio = new JLabel("Bio");


        btnRegister = createButton("Register!", REGISTER_BUTTON_COLOR);
        btnRegister.setForeground(SIGNIN_BUTTON_COLOR);
        btnSignIn = createButton("Have an account? Sign in!", SIGNIN_BUTTON_COLOR);
        btnUploadPhoto = createButton("Upload Photo!", UPLOAD_BUTTON_COLOR);

        btnRegister.addActionListener(this::onRegisterClicked);
        btnUploadPhoto.addActionListener(this::onUploadButtonClicked);
        btnSignIn.addActionListener(this::openSignInUI);
    }

    @Override
    protected void layoutComponents() {
        add(createHeaderPanel("Quackstagram - register"), BorderLayout.NORTH);
        add(createFieldsPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    @Override
    protected JPanel createFieldsPanel() {

        JPanel fieldsPanel = createPanel(BACKGROUND_COLOR);
        JLabel photoPanel = createPicture("img/logos/DACS.png");
        photoPanel.setAlignmentX(CENTER_ALIGNMENT);

        JLayeredPane usernamePane = layeredPaneForHoverText(lblUsername, txtUsername);
        JLayeredPane passwordPane = layeredPaneForHoverText(lblPassword, txtPassword);
        JLayeredPane bioPane = layeredPaneForHoverText(lblBio, txtBio);

        addHoverText(txtUsername, "Username", lblUsername);
        addHoverText(txtPassword, "Password", lblPassword);
        addHoverText(txtBio, "Bio", lblBio);


        fieldsPanel.add(photoPanel);
        fieldsPanel.add(usernamePane);
        fieldsPanel.add(passwordPane);
        fieldsPanel.add(bioPane);
        fieldsPanel.setLayout(new GridLayout(4,1,10,10));

        return fieldsPanel;
    }

    @Override
    protected JPanel createFooterPanel() {
        JPanel footerPanel = createPanel(BACKGROUND_COLOR); // Panel to contain the register button
        footerPanel.setLayout(new BorderLayout());
        footerPanel.add(createButtonPanel(), BorderLayout.CENTER);

        return footerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = createPanel(BACKGROUND_COLOR);
        buttonPanel.setLayout(new GridLayout(3,1,5,5));
        buttonPanel.add(btnUploadPhoto);
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSignIn);

        return buttonPanel;
    }

    private void onRegisterClicked(ActionEvent event) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String bio = txtBio.getText();
        if(profileManager.isValidInput(username))
            profileManager.onRegisterClicked(username,password, bio);
    }

    public void onUploadButtonClicked(ActionEvent event){
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username before uploading a photo");
        } else if (profileManager.doesUsernameExist(username)) {
            JOptionPane.showMessageDialog(this, "This username already exists");
        } else{
            profileManager.handleProfilePictureUpload(this, username);
        }
    }

    private void openSignInUI(ActionEvent event) {
        dispose();
        SwingUtilities.invokeLater(() -> {
            SignInUI signInFrame = new SignInUI();
            signInFrame.setVisible(true);
        });
    }

    @Override
    public void onSuccess(String username, String password, String bio) {
        dispose();
        openSignInUI(null);
    }

    @Override
    public void onFailure() {
        JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
