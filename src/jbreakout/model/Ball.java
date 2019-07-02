package jbreakout.model;

import java.awt.*;

public class Ball extends BreakoutSubject {

    public Point m_origin = null;
    public int m_radius = 20;
    public int m_dx = -1;
    public int m_dy = -1;

    public Ball(int radius) {
        m_radius = radius;
    }

    public void wander(Dimension d) {
        if (m_origin.x-m_radius < 0 ) {
            m_dx = 1;

        } else if (m_origin.x+m_radius > d.width) {
            m_dx = -1;

        } else if (m_origin.y-m_radius < 0) {
            m_dy = 1;

        } else if (m_origin.y+m_radius > d.height) {
            m_dy = -1;
        }

        m_origin.x += m_dx;
        m_origin.y += m_dy;
    }

    public void bounceX() {
        m_dx *= -1;
    }

    public void bounceY() {
        m_dy *= -1;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillOval(m_origin.x-m_radius, m_origin.y-m_radius, m_radius*2, m_radius*2);
        g2.setColor(Color.BLACK);
        g2.drawOval(m_origin.x-m_radius, m_origin.y-m_radius, m_radius*2, m_radius*2);
    }

    /////////////////////////////////////////////////////////////////
    public void setDy(int dy) {
        this.m_dy = dy;
    }

    public void setDx(int dx) {
        this.m_dx = dx;
    }

    public void setOrigin(Point p) {
        m_origin = p;
    }

    public void setRadius(int r) {
        this.m_radius = r;
    }
}
