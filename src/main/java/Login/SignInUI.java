package Login;

import UIFiles.InstagramProfileUI;
import UIFiles.LoginBaseUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Provides the user interface for signing into Quackstagram.
 * This UI includes fields for username and password entry, and buttons for signing in and registering.
 */
public class SignInUI extends LoginBaseUI implements LoginResultListener{
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private JTextField txtUsername;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JButton btnSignIn, btnRegisterNow;
    HandleCredentials loginController;

    /**
     * Constructor for SignInUI.
     * Initializes the components and layout for the sign-in interface.
     */
    public SignInUI() {
        super("Quackstagram - Sign In");
        initializeComponents();
        layoutComponents();
    }

    @Override
    protected void initializeComponents() {
        txtUsername = (JTextField) createTextField(false, "");
        txtPassword = (JPasswordField) createTextField(true, "");

        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");

        btnSignIn = createButton("Sign in!", Color.RED);
        btnRegisterNow = createButton("Register now!",Color.WHITE);

        btnSignIn.addActionListener(this::onSignInClicked);
        btnRegisterNow.addActionListener(this::onRegisterNowClicked);

    }
    @Override
    protected void layoutComponents() {
        add(createHeaderPanel("Quackstagram 🐥"),BorderLayout.NORTH);
        add(createFieldsPanel(),BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    @Override
    protected JPanel createFieldsPanel() {
        JPanel fieldsPanel = createPanel(BACKGROUND_COLOR);
        JLabel photoPanel = createPicture("img/logos/DACS.png");
        photoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLayeredPane usernamePane = layeredPaneForHoverText(lblUsername, txtUsername);
        JLayeredPane passwordPane = layeredPaneForHoverText(lblPassword, txtPassword);


        addHoverText(txtUsername, "Username", lblUsername);
        addHoverText(txtPassword, "Password", lblPassword);

        fieldsPanel.add(photoPanel);
        fieldsPanel.add(usernamePane);
        fieldsPanel.add(passwordPane);

        return fieldsPanel;
    }


    @Override
    protected JPanel createFooterPanel(){
        JPanel footerPanel = new JPanel(new BorderLayout()); // Panel to contain the register button
        footerPanel.setBackground(BACKGROUND_COLOR); // Background for the panel
        footerPanel.add(createButtonPanel(), BorderLayout.CENTER);
        return  footerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Grid layout with 1 row, 2 columns
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(btnSignIn);
        buttonPanel.add(btnRegisterNow);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignInUI frame = new SignInUI();
            frame.setVisible(true);
        });
    }

    private void onSignInClicked(ActionEvent event) {
        String enteredUsername = txtUsername.getText();
        char[] enteredPassword = txtPassword.getPassword();
        loginController = new HandleCredentials(this);
        loginController.onSignInClicked(enteredUsername, String.valueOf(enteredPassword));
    }

    private void onRegisterNowClicked(ActionEvent event) {
        // Close the SignInUI frame
        dispose();
        // Open the SignUpUI frame
        SwingUtilities.invokeLater(() -> {
            SignUpUI signUpFrame = new SignUpUI();
            signUpFrame.setVisible(true);
        });
    }

    @Override
    public void onLoginSuccess() {
        dispose();
    }
    @Override
    public void onLoginFailure() {
        System.out.println("login failed");
    }

}
