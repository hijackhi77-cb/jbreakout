package jbreakout.primitive;

import javax.swing.*;
import java.awt.*;

public class ImageIconManager {

    public static ImageIcon getImageIcon(String path) {
        //java.net.URL imgURL = getClass().getResource(path);
        java.net.URL imgURL = ImageIcon.class.getResource(path);
        if (imgURL == null) {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
        return new ImageIcon(imgURL);
    }

    public static ImageIcon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        if (icon == null) {
            System.err.println("resizeIcon: icon is null");
            return null;
        }
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
