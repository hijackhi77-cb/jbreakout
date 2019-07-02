package jbreakout.gamestate;

import jbreakout.primitive.DialogManager;
import jbreakout.view.GameBoardView;

import java.awt.event.MouseEvent;

public class StartedState implements GameState {

    private GameBoardView m_gameBoardView = null;

    public StartedState(GameBoardView gameBoardView) {
        m_gameBoardView = gameBoardView;
    }

    @Override
    public GameState ESCPressed() {
        m_gameBoardView.pauseGame();
        if (DialogManager.showQuitGameDialog()) {
            System.out.println("StartedState + ESC (YES) -> Sys.exit");
            System.exit(0);
        }
        System.out.println("StartedState + ESC (NO) -> PausedState");
        return new PausedState(m_gameBoardView);
    }

    @Override
    public GameState leftArrowPressed() {
        int i = 0;
        while (i < m_gameBoardView.PADDLE_MOVE_RATE) {

            m_gameBoardView.m_paddle.moveLeft(1);

            // TODO: customized starting position
            /*if (!m_isStarted) {
                m_ball.setOrigin(new Point(m_paddle.getUpperLeft().x + m_paddle.getWidthInt() / 2,
                        m_paddle.getUpperLeft().y - m_ball.m_radius - 2));
            }*/

            if (0 > m_gameBoardView.m_paddle.getUpperLeft().x) {
                m_gameBoardView.m_paddle.getUpperLeft().x = 0;
                break;
            }
            ++i;
        }


        return this;
    }

    @Override
    public GameState rightArrowPressed() {
        int paddleWidth = m_gameBoardView.m_paddle.getWidthInt();
        int i = 0;

        while (i < m_gameBoardView.PADDLE_MOVE_RATE) {

            m_gameBoardView.m_paddle.moveRight(1);

            // TODO: customized starting position
            /*if (!m_isStarted) {
                m_ball.setOrigin(new Point(m_paddle.getUpperLeft().x + m_paddle.getWidthInt() / 2,
                m_paddle.getUpperLeft().y - m_ball.m_radius - 2));
            }*/

            if (m_gameBoardView.m_paddle.getUpperLeft().x + paddleWidth > m_gameBoardView.getSize().width) {
                m_gameBoardView.m_paddle.getUpperLeft().x = m_gameBoardView.getSize().width - paddleWidth;
                break;
            }


            ++i;
        }

        return this;
    }

    @Override
    public GameState spacePressed() {
        m_gameBoardView.pauseGame();
        System.out.println("StartedState + space -> PausedState");
        return new PausedState(m_gameBoardView);
    }

    @Override
    public GameState mouseClicked(MouseEvent e) {
        m_gameBoardView.pauseGame();
        System.out.println("StartedState + mouseClicked -> PausedState");
        return new PausedState(m_gameBoardView);
    }

    @Override
    public GameState mouseMoved(MouseEvent e) {
        //System.out.println("StartedState + mouseMoved -> StartedState");
        if (m_gameBoardView.m_paddle.getWidth() / 2 < e.getPoint().x
                && e.getPoint().x < m_gameBoardView.getSize().width -
                m_gameBoardView.m_paddle.getWidth() / 2) {
            m_gameBoardView.m_paddle.moveTo(e.getPoint());
            //if (!m_isStarted) m_ball.m_origin.x = e.getPoint().x;
        }
        return this;
    }

    @Override
    public GameState ballDead() {
        if (!DialogManager.showPlayAgainDialog()) {
            System.exit(0);
        }
        m_gameBoardView.stopBallRolling();
        m_gameBoardView.pauseGame();
        m_gameBoardView.initBricks(m_gameBoardView.m_dimension);
        m_gameBoardView.initPaddle(m_gameBoardView.m_dimension);
        m_gameBoardView.initBall(m_gameBoardView.m_dimension);
        m_gameBoardView.initMetaLabels(m_gameBoardView.m_dimension);
        return new PausedState(m_gameBoardView);
    }

    @Override
    public GameState nextLevel() {
        m_gameBoardView.pauseGame();
        if (!DialogManager.showNextLevelDialog()) {
            System.exit(0);
        }
        m_gameBoardView.initBricks(m_gameBoardView.m_dimension);
        m_gameBoardView.initBall(m_gameBoardView.m_dimension);
        m_gameBoardView.initPaddle(m_gameBoardView.m_dimension);
        ++m_gameBoardView.m_currLevel;
        m_gameBoardView.m_levelLabel.setText("Level " + m_gameBoardView.m_currLevel);
        if (m_gameBoardView.m_currSpeed < 3) {
            ++m_gameBoardView.m_currSpeed;
            m_gameBoardView.m_currSpeedLabel.setText("Speed " + m_gameBoardView.m_currSpeed);
        }

        return new PausedState(m_gameBoardView);
    }
}
