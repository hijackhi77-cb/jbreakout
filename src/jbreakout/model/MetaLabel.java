package jbreakout.model;

import javax.swing.*;
import java.awt.*;

public class MetaLabel extends JLabel {
    private Point m_upperLeft = null;

    public MetaLabel(String text) {
        super(text);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Copperplate", Font.BOLD, 15));
        g2.drawString(this.getText(), m_upperLeft.x, m_upperLeft.y);
    }

    /////////////////////////////////////////////////////////////////
    public void setUpperLeft(Point p) {
        m_upperLeft = p;
    }

    public Point getUpperLeft() {
        return this.m_upperLeft;
    }
}
