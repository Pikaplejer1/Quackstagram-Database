package UIFiles;
import javax.swing.*;
import java.awt.*;

/**
 * Abstract base class for user interfaces related to login and registration in the application.
 * It provides common functionalities and layout configurations for login-related UI components.
 */
public abstract class LoginBaseUI extends BaseUI {


    /**
     * Constructor for LoginBaseUI.
     * Initializes the base properties for login and registration UIs.
     *
     * @param title The title of the window.
     */
    protected LoginBaseUI(String title) {
        super(title);
        // Initialize common properties for login-related UIs
    }

    /**
     * Creates a layered pane for input fields with hover text functionality.
     *
     * @param label     The label to be displayed as hover text.
     * @param component The input component (e.g., JTextField, JPasswordField).
     * @return A JLayeredPane with the label and component.
     */
    protected JLayeredPane layeredPaneForHoverText(JLabel label, JComponent component) {
        JLayeredPane layeredField = new JLayeredPane();
        layeredField.setPreferredSize(new Dimension(244, 100));
        label.setBounds(8, -18, 244, 100);
        component.setBounds(0, 0, 244, 63);
        layeredField.add(label, JLayeredPane.DEFAULT_LAYER);
        layeredField.add(component, JLayeredPane.DEFAULT_LAYER);

        return layeredField;
    }


    abstract protected void initializeComponents();

    abstract protected void layoutComponents();

    abstract protected JPanel createFieldsPanel();

    protected abstract JPanel createFooterPanel();
}
