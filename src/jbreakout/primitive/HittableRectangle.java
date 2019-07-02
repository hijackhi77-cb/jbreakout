package jbreakout.primitive;

import jbreakout.model.Ball;

import java.awt.*;

public class HittableRectangle extends Rectangle {

    public HittableRectangle(int width, int height) {
        super(width, height);
    }

    public boolean hitTop(Ball ball) {
        int radius = ball.m_radius;
        int origin_x = ball.m_origin.x;
        int origin_y = ball.m_origin.y;
        return contains(origin_x, origin_y + radius);
    }

    public boolean hitBottom(Ball ball) {
        int radius = ball.m_radius;
        int origin_x = ball.m_origin.x;
        int origin_y = ball.m_origin.y;
        return contains(origin_x, origin_y - radius);
    }

    public boolean hitLeft(Ball ball) {
        int radius = ball.m_radius;
        int origin_x = ball.m_origin.x;
        int origin_y = ball.m_origin.y;
        return contains(origin_x + radius, origin_y);
    }

    public boolean hitRight(Ball ball) {
        int radius = ball.m_radius;
        int origin_x = ball.m_origin.x;
        int origin_y = ball.m_origin.y;
        return contains(origin_x - radius, origin_y);
    }

    public void moveTo(Point p) {
        this.x = p.x - this.width / 2;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawRect(this.x, this.y, this.width, this.height);
    }

    /////////////////////////////////////////////////////////////////
    public void setSize(Dimension dimension) {
        this.width = dimension.width;
        this.height = dimension.height;
    }

    public void setUpperLeft(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point getUpperLeft() {
        return new Point(this.x, this.y);
    }

    public int getWidthInt() {
        return this.width;
    }

    public int getHeightInt() {
        return this.height;
    }
}
