package Notifications;

import UIFiles.BaseUI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Provides the user interface for displaying notifications in Quackstagram.
 */
public class NotificationsUI extends BaseUI {

    private final NotificationsReader notificationReader;


    /**
     * Constructor for NotificationsUI.
     * Initializes the notifications reader and sets up the UI layout.
     */
    public NotificationsUI() {

        super("Notifications");
        notificationReader = new NotificationsReader();
        initializeUI();
    }

    /**
     * Initializes the components of the notifications UI.
     */
    private void initializeUI() {

        JPanel headerPanel = createHeaderPanel(" Notifications 🐥");
        JPanel navigationPanel = createNavigationPanel();
        JPanel contentPanel = createContentPanel();

        JScrollPane scrollPane = createScrollPane(contentPanel);
        displayNotifications(contentPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    /**
     * Displays the notifications in the content panel.
     * Reads notifications from the notification reader and adds them to the panel.
     *
     * @param contentPanel The panel where notifications are to be displayed.
     */
    private void displayNotifications(JPanel contentPanel) {
        NotificationsReader notificationReader = new NotificationsReader();
        List<String> notifications = notificationReader.readNotifications();

        for (String notification : notifications) {

            JPanel notificationPanel = new JPanel(new BorderLayout());

            notificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel notificationLabel = new JLabel(notification);
            notificationPanel.add(notificationLabel, BorderLayout.CENTER);

            contentPanel.add(notificationPanel);
        }
    }

}
