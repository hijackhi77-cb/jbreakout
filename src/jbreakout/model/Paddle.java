package jbreakout.model;

import jbreakout.primitive.HittableRectangle;

import java.awt.*;

public class Paddle extends HittableRectangle {

    public Paddle(int width, int height) {
        super(width, height);
    }

    public void moveLeft(int n) {
        this.x -= n;
    }

    public void moveRight(int n) {
        this.x += n;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(this.x, this.y, this.width, this.height);
        //g2.drawRect(this.x, this.y, this.width, this.height);
    }

}
