package jbreakout.gamestate;

import jbreakout.primitive.DialogManager;
import jbreakout.view.GameBoardView;

import java.awt.event.MouseEvent;

public class PausedState implements GameState {

    private GameBoardView m_gameBoardView = null;

    public PausedState(GameBoardView gameBoardView) {
        m_gameBoardView = gameBoardView;
    }

    @Override
    public GameState ESCPressed() {
        if (DialogManager.showQuitGameDialog()) {
            System.out.println("PausedState + ESC (YES) -> Sys.exit");
            System.exit(0);
        }
        System.out.println("PausedState + ESC (NO) -> PausedState");
        return this;
    }

    @Override
    public GameState spacePressed() {
        m_gameBoardView.resumeGame();
        System.out.println("PausedState + space -> StartedState");
        return new StartedState(m_gameBoardView);
    }

    @Override
    public GameState leftArrowPressed() {
        // No effect
        return this;
    }

    @Override
    public GameState rightArrowPressed() {
        // No effect
        return this;
    }

    @Override
    public GameState mouseClicked(MouseEvent e) {
        m_gameBoardView.resumeGame();
        System.out.println("PausedState + mouseClicked -> StartedState");
        return new StartedState(m_gameBoardView);
    }

    @Override
    public GameState mouseMoved(MouseEvent e) {
        //System.out.println("PausedState + mouseMoved -> PausedState");
        return this;
    }

    @Override
    public GameState ballDead() {
        // Impossible
        return null;
    }

    @Override
    public GameState nextLevel() {
        // Impossible
        return null;
    }
}
