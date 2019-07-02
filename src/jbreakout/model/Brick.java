package jbreakout.model;

import jbreakout.primitive.ColorManager;
import jbreakout.primitive.HittableRectangle;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Brick extends HittableRectangle {

    public final int VIBRATE_ITERATION = 4;
    public final int VIBRATE_RANGE = 2;
    public int m_vibratePeriod = 2;

    public int m_health = 1;
    public Color m_foregroundColor = Color.WHITE;
    public Color m_backgroundColor = Color.WHITE;
    public Color m_vibrateColor = Color.YELLOW;
    public boolean m_inVibrate = false;
    public int m_vibratePaintCounter = 0;
    public int m_vibrateIterationCounter = 0;

    public Brick(int width, int height) {
        super(width, height);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (m_health <= 0) {
            g2.setColor(m_backgroundColor);
        } else {
            g2.setColor(ColorManager.applyAlpha(m_foregroundColor, 0.25f * m_health));
        }

        if (m_inVibrate) {
            if (m_vibratePaintCounter < m_vibratePeriod / 2) {
                g2.setColor(m_vibrateColor);
                g2.fillRect(this.x - VIBRATE_RANGE, this.y - VIBRATE_RANGE, this.width, this.height);
                g2.setColor(Color.WHITE);
                g2.drawRect(this.x - VIBRATE_RANGE, this.y - VIBRATE_RANGE, this.width, this.height);

            } else if (m_vibratePaintCounter < m_vibratePeriod) {
                g2.setColor(m_vibrateColor);
                g2.fillRect(this.x + VIBRATE_RANGE, this.y + VIBRATE_RANGE, this.width, this.height);
                g2.setColor(Color.WHITE);
                g2.drawRect(this.x + VIBRATE_RANGE, this.y + VIBRATE_RANGE, this.width, this.height);

            } else {
                g2.setColor(m_vibrateColor);
                g2.fillRect(this.x, this.y, this.width, this.height);
                g2.setColor(Color.WHITE);
                g2.drawRect(this.x, this.y, this.width, this.height);

                m_vibratePaintCounter = 0;
                if (m_vibrateIterationCounter >= VIBRATE_ITERATION) {
                    m_inVibrate = false;
                    m_vibrateIterationCounter = 0;
                } else {
                    ++m_vibrateIterationCounter;
                }
            }
            ++m_vibratePaintCounter;
        } else {
            g2.fillRect(this.x, this.y, this.width, this.height);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(6));
            g2.drawRect(this.x, this.y, this.width, this.height);
            g2.setStroke(new BasicStroke(1));
        }
    }

    /////////////////////////////////////////////////////////////////
    public void setHealth(int health) {
        this.m_health = health;
    }

    public int getHealth() {
        return this.m_health;
    }

    public void decreaseHealth(int n) {
        this.m_health -= n;
    }

    public void setVibrate(boolean bool) {
        this.m_inVibrate = bool;
    }

    public void setVibratePeriod(int period) {
        this.m_vibratePeriod = period;
    }

    public void setVibrateColor(Color color) {
        this.m_vibrateColor = color;
    }

    public void setBackgroundColor(Color color) {
        this.m_backgroundColor = color;
    }

    public void setForegroundColor(Color color) {
        this.m_foregroundColor = color;
    }
}
