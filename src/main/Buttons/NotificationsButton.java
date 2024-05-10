package Buttons;

import javax.swing.*;

import Notifications.NotificationsUI;

public class NotificationsButton extends JFrame implements NavigationButton {
    public void performAction(){
        // Open NotificationsUI frame
        NotificationsUI notificationsUI = new NotificationsUI();
        notificationsUI.setVisible(true);
    }
}