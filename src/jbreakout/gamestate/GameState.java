package jbreakout.gamestate;

import java.awt.event.MouseEvent;

public interface GameState {
    GameState ESCPressed();
    GameState spacePressed();
    GameState leftArrowPressed();
    GameState rightArrowPressed();

    GameState mouseClicked(MouseEvent e);
    GameState mouseMoved(MouseEvent e);

    GameState ballDead();
    GameState nextLevel();
}
