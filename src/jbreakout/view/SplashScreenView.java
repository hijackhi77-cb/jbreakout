package jbreakout.view;

import jbreakout.primitive.ImageIconManager;

import javax.swing.*;
import java.awt.*;

public class SplashScreenView {
    public ImageIcon m_imageIcon = null;
    public Dimension m_dimension = null;
    public Image m_image = null;

    public SplashScreenView(String path, Dimension dimension) {
        this.m_imageIcon = ImageIconManager.getImageIcon(path);
        this.m_dimension = dimension;
        m_imageIcon = ImageIconManager.resizeIcon(
                m_imageIcon, m_dimension.width, m_dimension.height);
        m_image = m_imageIcon.getImage();
    }

    public void setImageIcon(String path) {
        m_imageIcon = ImageIconManager.getImageIcon(path);
        m_imageIcon = ImageIconManager.resizeIcon(
                m_imageIcon, m_dimension.width, m_dimension.height);
    }

    public void setSize(Dimension dimension) {
        m_dimension = dimension;
        m_imageIcon = ImageIconManager.resizeIcon(
                m_imageIcon, dimension.width, dimension.height);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(m_image, 0, 0, null);
    }
}
