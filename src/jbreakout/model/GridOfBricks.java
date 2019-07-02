package jbreakout.model;

import jbreakout.primitive.ColorManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridOfBricks extends Rectangle {
    public Dimension m_dimension;
    public List<List<Brick>> m_bricks;
    public Color m_backgroundColor = Color.BLACK;
    public Color m_foregroundColor = null;
    public int m_totalScore;
    public Point m_upperLeft;
    public int m_fps = 60;

    public int m_numBrickRow = 7;
    public int m_numBrickCol = 12;
    public Color[] m_basicColors = {
            new Color(255, 0, 0),
            new Color(255, 165, 0 ),
            new Color(255, 255, 0),
            new Color(0, 255, 0),
            new Color(0, 127, 255),
            new Color(0, 0, 255),
            new Color(139, 0, 255 )
    };

    public GridOfBricks(Dimension dimension) {
        m_dimension = dimension;
        int brickAreaWidth = dimension.width;
        int brickAreaHeight = dimension.height / 100 * 40;
        int width = brickAreaWidth / (m_numBrickCol + 2);
        int height = brickAreaHeight / (m_numBrickRow + 2);

        m_upperLeft = new Point(width, height);
        this.x = width;
        this.y = height;
        this.width = m_numBrickCol * width;
        this.height = m_numBrickCol * height;

        while (m_foregroundColor == null
                || m_foregroundColor == m_backgroundColor
                || m_foregroundColor == Color.YELLOW) {
            m_foregroundColor = ColorManager.getRandomColor();
        }
        m_totalScore = 0;
        m_bricks = new ArrayList<>(m_numBrickRow);
        for (int i = 0; i < m_numBrickCol; ++i) {
            List<Brick> temp_col = new ArrayList<>();
            for (int j = 0; j < m_numBrickRow; ++j) {
                Brick temp_brick = new Brick(width, (height));
                temp_brick.setUpperLeft(new Point((i + 1) * width, (j + 3) * height));
                int health = new Random().nextInt(4) + 1;
                //int health = 1;
                temp_brick.setHealth(health);

                m_totalScore += health;
                //temp_brick.setForegroundColor(m_foregroundColor);
                temp_brick.setForegroundColor(m_basicColors[j]);
                temp_brick.setBackgroundColor(m_backgroundColor);
                temp_brick.setVibrateColor(Color.YELLOW);

                temp_col.add(temp_brick);
            }
            m_bricks.add(temp_col);
        }
    }

    /////////////////////////////////////////////////////////////////
    public void setFPS(int fps) {
        this.m_fps = fps;
        for (int i = 0; i < m_numBrickCol ; ++i) {
            for (int j = 0; j < m_numBrickRow; ++j) {
                m_bricks.get(i).get(j).setVibratePeriod(m_fps / 6);
            }
        }
    }

    public void setBackgroundColor(Color color) {
        this.m_backgroundColor = color;
    }
}
