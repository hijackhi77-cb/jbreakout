package jbreakout.gamestate;

import jbreakout.primitive.DialogManager;
import jbreakout.view.GameBoardView;

import java.awt.event.MouseEvent;

public class SplashScreenState implements GameState {

    private GameBoardView m_gameBoardView = null;

    public SplashScreenState(GameBoardView gameBoardView) {
        m_gameBoardView = gameBoardView;
    }

    @Override
    public GameState spacePressed() {
        m_gameBoardView.hideSplashScreen();
        m_gameBoardView.pauseGame();
        System.out.println("SplashScreenState + space -> PausedState");
        return new PausedState(m_gameBoardView);
    }

    @Override
    public GameState ESCPressed() {
        if (DialogManager.showQuitGameDialog()) {
            System.out.println("SplashScreenState + ESC (YES) -> Sys.exit");
            System.exit(0);
        }
        System.out.println("SplashScreenState + ESC (NO) -> SplashScreenState");
        return this;
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
        System.out.println("SplashScreenState + mouseClicked -> SplashScreenState");
        return this;
    }

    @Override
    public GameState mouseMoved(MouseEvent e) {
        //System.out.println("SplashScreenState + mouseMoved -> SplashScreenState");
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
