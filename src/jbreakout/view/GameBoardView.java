package jbreakout.view;

import jbreakout.gamestate.GameState;
import jbreakout.gamestate.SplashScreenState;
import jbreakout.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class GameBoardView extends JPanel implements BreakoutView {

    public final KeyStroke LEFT_ARROW_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
    public final KeyStroke RIGHT_ARROW_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
    public final KeyStroke ESC_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    public final KeyStroke SPACE_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
    public final String m_splashPath = "/res/jbreakout-splash.png";
    public final int PADDLE_MOVE_RATE = 100;
    public final Color THEME_COLOR = Color.BLACK;

    public GameState m_currGameState = null;

    public boolean m_inSplash = true;
    public boolean m_isPaused = false;
    public SplashScreenView m_splashScreenView = null;
    public Dimension m_dimension = null;
    public GridOfBricks m_gridOfBricks = null;
    public Paddle m_paddle = null;
    public Ball m_ball = null;
    public Timer m_ballRollingTimer = null;
    public Timer m_frameRefreshingTimer = null;
    public int m_fps;
    public int m_runningFps = 0;
    public int m_originalSpeed;
    public int m_currSpeed;
    public int m_score = 0;
    public int m_currLevel = 1;
    public MetaLabel m_scoreLabel = null;
    public MetaLabel m_fpsLabel = null;
    public MetaLabel m_levelLabel = null;
    public MetaLabel m_currSpeedLabel = null;
    public MetaLabel m_pauseLabel = null;
    public int m_unitWidth = 0;
    public int m_unitHeight = 0;

    public GameBoardView(Dimension dimension, int fps, int speed) {

        this.setBackground(THEME_COLOR);
        this.m_dimension = dimension;
        this.m_fps = fps;
        this.m_originalSpeed = speed;
        this.m_currSpeed = speed;

        m_currGameState = new SplashScreenState(this);

        startFrameRefreshing();

        initBricks(m_dimension);
        initPaddle(m_dimension);
        initBall(m_dimension);
        initMetaLabels(m_dimension);
        initSplashScreenView(m_dimension);

        TimerTask updateFPS = new TimerTask() {
            public void run() {
                m_fpsLabel.setText("FPS " + m_runningFps);
                m_runningFps = 0;
            }
        };

        Timer t = new Timer();
        t.scheduleAtFixedRate(updateFPS, 1000, 1000);

        this.setLayout(null);

        ///////////////////////////////////////////////////////////////////////
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                m_currGameState = m_currGameState.mouseMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                m_currGameState = m_currGameState.mouseMoved(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                m_currGameState = m_currGameState.mouseClicked(e);
            }
        });

        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                m_currGameState = m_currGameState.spacePressed();
            }
        }, SPACE_KEY, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_currGameState = m_currGameState.leftArrowPressed();
            }
        }, LEFT_ARROW_KEY, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_currGameState = m_currGameState.rightArrowPressed();
            }
        }, RIGHT_ARROW_KEY, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_currGameState = m_currGameState.ESCPressed();
            }
        }, ESC_KEY, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void initSplashScreenView(Dimension dimension) {
        m_splashScreenView = new SplashScreenView(m_splashPath,
                new Dimension(dimension.width, dimension.height));
    }

    public void initBricks(Dimension dimension) {
        m_score = 0;
        m_gridOfBricks = new GridOfBricks(dimension);
        m_gridOfBricks.setFPS(m_fps);
        m_gridOfBricks.setBackgroundColor(THEME_COLOR);
        m_unitWidth = dimension.width / (m_gridOfBricks.m_numBrickCol + 2);
        m_unitHeight = dimension.height / 100 * 40 / (m_gridOfBricks.m_numBrickRow + 2);
    }

    public void initPaddle(Dimension dimension) {
        this.m_paddle = new Paddle(m_unitWidth * 2, m_unitHeight / 2);
        this.m_paddle.setUpperLeft(new Point(
                dimension.width / 2 - m_paddle.getWidthInt() / 2,
                dimension.height - 2 * m_unitHeight));
    }

    public void initBall(Dimension dimension) {
        this.m_ball = new Ball(m_unitWidth / 10);
        this.m_ball.setOrigin(new Point(
                dimension.width / 2,
                dimension.height - 2 * m_unitHeight - m_ball.m_radius - 1));
    }

    public void initMetaLabels(Dimension dimension) {
        m_score = 0;
        m_currLevel = 1;
        m_currSpeed = m_originalSpeed;

        this.m_scoreLabel = new MetaLabel("Score " + m_score);
        this.m_scoreLabel.setUpperLeft(new Point(0, 15));

        this.m_fpsLabel = new MetaLabel("FPS " + m_fps);
        this.m_fpsLabel.setUpperLeft(new Point(0, 30));

        this.m_currSpeedLabel = new MetaLabel("Speed " + m_currSpeed);
        this.m_currSpeedLabel.setUpperLeft(new Point(0, 45));

        this.m_levelLabel = new MetaLabel("Level " + m_currLevel);
        this.m_levelLabel.setUpperLeft(new Point(0, 60));

        this.m_pauseLabel = new MetaLabel("PAUSE");
        this.m_pauseLabel.setUpperLeft(new Point(0, 75));
    }

    public void resumeGame() {
        startBallRolling();
        m_isPaused = false;
    }

    public void pauseGame() {
        stopBallRolling();
        m_isPaused = true;
    }

    public void startBallRolling() {
        m_ballRollingTimer = new Timer();

        m_ballRollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                m_ball.wander(m_dimension);

                if (m_paddle.hitTop(m_ball)) {
                    m_ball.setDy(-1);

                } else if (m_paddle.hitBottom(m_ball)) {
                    m_ball.setDy(1);

                } else if (m_paddle.hitLeft(m_ball)) {
                    m_ball.setDx(-1);

                } else if (m_paddle.hitRight(m_ball)) {
                    m_ball.setDx(1);

                } else if (m_ball.m_origin.y + m_ball.m_radius >= m_dimension.height) {
                    m_currGameState = m_currGameState.ballDead();

                } else {
                    int x = m_ball.m_origin.x;
                    int y = m_ball.m_origin.y;
                    int r = m_ball.m_radius;
                    if (m_gridOfBricks.contains(x + r, y)
                            || m_gridOfBricks.contains(x - r, y)
                            || m_gridOfBricks.contains(x, y + r)
                            || m_gridOfBricks.contains(x, y - r)) {

                        for (int i = 0; i < m_gridOfBricks.m_numBrickCol; ++i) {
                            for (int j = 0; j < m_gridOfBricks.m_numBrickRow; ++j) {
                                if (m_gridOfBricks.m_bricks.get(i).get(j).getHealth() > 0) {
                                    if (m_gridOfBricks.m_bricks.get(i).get(j).hitTop(m_ball)) {
                                        m_ball.setDy(-1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).decreaseHealth(1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).setVibrate(true);
                                        ++m_score;

                                    } else if (m_gridOfBricks.m_bricks.get(i).get(j).hitBottom(m_ball)) {
                                        m_ball.setDy(1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).decreaseHealth(1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).setVibrate(true);
                                        ++m_score;

                                    } else if (m_gridOfBricks.m_bricks.get(i).get(j).hitLeft(m_ball)) {
                                        m_ball.setDx(-1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).decreaseHealth(1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).setVibrate(true);
                                        ++m_score;

                                    } else if (m_gridOfBricks.m_bricks.get(i).get(j).hitRight(m_ball)) {
                                        m_ball.setDx(1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).decreaseHealth(1);
                                        m_gridOfBricks.m_bricks.get(i).get(j).setVibrate(true);
                                        ++m_score;
                                    }

                                    if (m_score >= m_gridOfBricks.m_totalScore) {
                                        m_currGameState = m_currGameState.nextLevel();
                                    }
                                }
                            }
                        }
                    }
                }

                m_scoreLabel.setText("Score " + m_score);

            } // end of run()
        }, 0, 1000 / (m_currSpeed * 200));
    }

    public void stopBallRolling() {
        if (m_ballRollingTimer == null) return;
        m_ballRollingTimer.cancel();
    }

    private void startFrameRefreshing() {
        m_frameRefreshingTimer = new Timer();
        m_frameRefreshingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
                ++m_runningFps;
            }
        }, 0, 1000 / m_fps);
    }

    private void stopFrameRefreshing() {
        m_frameRefreshingTimer.cancel();
        m_frameRefreshingTimer = null;
    }

   /* @Override
    public void setSize(Dimension dimension) {

        int brickAreaWidth = dimension.width;
        int brickAreaHeight = dimension.height / 100 * 40;
        int width = brickAreaWidth / (m_gridOfBricks.m_numBrickCol + 2);
        int height = brickAreaHeight / (m_gridOfBricks.m_numBrickRow + 2);

        initSplashScreenView(dimension);

        m_gridOfBricks.setSize(dimension);

        for (int i = 0; i < m_gridOfBricks.m_numBrickRow; ++i) {
            for (int j = 0; j < m_gridOfBricks.m_numBrickCol; ++j) {
                m_gridOfBricks.m_bricks.get(i).get(j).setSize(new Dimension(width, height));
                m_gridOfBricks.m_bricks.get(i).get(j).setUpperLeft(new Point((i + 1) * width, (j + 1) * height));
            }
        }

        this.m_paddle.setSize(new Dimension(width, height / 2));
        this.m_paddle.setUpperLeft(new Point(
                dimension.width / 2 - m_paddle.getWidthInt() / 2,
                dimension.height - 2 * m_unitHeight));

        this.m_ball.setRadius(width / 10);

        // TODO: detect when the ball hits the corner of the rectangles
        //float ratioX = m_ball.m_origin.x / m_dimension.width;
        //float ratioY = m_ball.m_origin.y / m_dimension.height;
        //System.out.println(m_ball.m_origin.x + " " + m_ball.m_origin.y + " " + m_dimension.width + " " + m_dimension.height);
        //System.out.println(ratioX + " " + ratioY);

        // TODO: customized starting position
        if (m_isStarted) {
            //this.m_ball.setOrigin(new Point(ratioX * dimension.width, ratioY * dimension.height));
        } else {
            this.m_ball.setOrigin(new Point(
                    dimension.width / 2,
                    dimension.height - 2 * m_unitHeight - m_ball.m_radius - 1));
        }

        m_dimension = dimension;
    }*/

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < m_gridOfBricks.m_numBrickCol; ++i) {
            for (int j = 0; j < m_gridOfBricks.m_numBrickRow; ++j) {
                m_gridOfBricks.m_bricks.get(i).get(j).draw(g2);
            }
        }

        m_paddle.draw(g2);
        m_ball.draw(g2);

        m_scoreLabel.draw(g2);
        m_fpsLabel.draw(g2);
        m_currSpeedLabel.draw(g2);
        m_levelLabel.draw(g2);

        if (m_isPaused) m_pauseLabel.draw(g2);
        if (m_inSplash) m_splashScreenView.draw(g2);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void showSplashScreen() {
        m_inSplash = true;
    }

    public void hideSplashScreen() {
        m_inSplash = false;
    }
}
