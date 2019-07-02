package jbreakout.primitive;

import javax.swing.*;

public class DialogManager {

    public static boolean showQuitGameDialog() {
        int confirmed = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to quit the program?",
                "Quit Program",
                JOptionPane.YES_NO_OPTION);

        return confirmed == JOptionPane.YES_OPTION;
    }

    public static boolean showNextLevelDialog() {
        int confirmed = JOptionPane.showConfirmDialog(null,
                "Do you want to play next level?\n",
                "Good Game!",
                JOptionPane.YES_NO_OPTION);

        return confirmed == JOptionPane.YES_OPTION;
    }

    public static boolean showPlayAgainDialog() {
        int confirmed = JOptionPane.showConfirmDialog(null,
                            "Do you want to play again?",
                            "Game Over",
                            JOptionPane.YES_NO_OPTION);

        return confirmed == JOptionPane.YES_OPTION;
    }

}
