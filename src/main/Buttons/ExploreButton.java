package Buttons;
import javax.swing.*;

import UIFiles.ExploreUI;

public class ExploreButton extends JFrame implements NavigationButton {
    public void performAction(){
        // Open ExploreUI frame
        ExploreUI explore = new ExploreUI();
        explore.setVisible(true);
    }
}
