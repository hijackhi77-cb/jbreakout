package jbreakout;

import jbreakout.view.GameBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JBreakout extends JFrame {

    public static void main(String[] args) {

        int fps = 60;
        int speed = 2;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-fps")) {
                fps = Integer.parseInt(args[++i]);
            } else if (args[i].equals("-speed")) {
                speed = Integer.parseInt(args[++i]);
            }
        }

        System.out.println("-fps " + fps + " -speed " + speed);
        JBreakout game = new JBreakout(fps, speed);
    }


    private Dimension m_frameDimension = new Dimension(1000, 600);
    private GameBoardView m_gameBoardView;

    public JBreakout(int fps, int speed) {

        super("JBreakout");
        this.setPreferredSize(m_frameDimension);
        this.setMaximumSize(new Dimension(1000, 600));
        this.setMinimumSize(new Dimension(800, 480));
        this.setLayout(new BorderLayout());

        m_gameBoardView = new GameBoardView(m_frameDimension, fps, speed);
        this.getContentPane().add(m_gameBoardView);

        this.setVisible(true);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.pack();

        // TODO: window resizing
        /*this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                m_gameBoardView.setSize(e.getComponent().getSize());
            }
        });*/
    }
}
