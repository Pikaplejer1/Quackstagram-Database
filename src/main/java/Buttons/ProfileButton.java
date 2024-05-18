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
        // Open InstagramProfileUI frame
        String loggedInUsername = "";

        // Read the logged-in user's username from users.txt
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "users.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                loggedInUsername = line.split(":")[0].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = new User(loggedInUsername);
        InstagramProfileUI profileUI = new InstagramProfileUI();
        profileUI.setVisible(true);
    }
}
