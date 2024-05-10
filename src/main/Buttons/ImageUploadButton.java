package Buttons;

import javax.swing.*;

import UIFiles.ImageUploadUI;

public class ImageUploadButton extends JFrame implements NavigationButton {
    public void performAction(){
        // Open ImageUploadUI frame
        ImageUploadUI upload = new ImageUploadUI();
        upload.setVisible(true);
    }
}
