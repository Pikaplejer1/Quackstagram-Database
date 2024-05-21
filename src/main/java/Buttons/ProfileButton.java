package Buttons;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import MainFiles.*;
import UIFiles.InstagramProfileUI;

public class ProfileButton extends JFrame implements NavigationButton {
    public void performAction(){
        InstagramProfileUI profileUI = new InstagramProfileUI(User.getInstance());
        profileUI.setVisible(true);
    }
}
