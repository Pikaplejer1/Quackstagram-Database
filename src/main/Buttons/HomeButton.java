package Buttons;
import javax.swing.*;
import UIFiles.QuackstagramHomeUI;

public class HomeButton extends JFrame implements NavigationButton {
    public void performAction() {
        // Open QuackstagramHomeUI frame
        QuackstagramHomeUI homeUI = new QuackstagramHomeUI();
        homeUI.setVisible(true);

    }
}
