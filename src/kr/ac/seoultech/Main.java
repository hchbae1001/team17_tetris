package kr.ac.seoultech;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("Tetris Team 17");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(12*26+10, 26*23+25);
        f.setVisible(true);
        final Tetris game = new Tetris();
        game.init();
        f.add(game);
// Keyboard controls
        f.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        game.rotate(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.rotate(+1);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.move(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.move(+1);
                        break;
//                    case KeyEvent.VK_ESCAPE:
//                        f.remove(game);
//                        break;
//                    case KeyEvent.VK_F1:
//                        f.add(game);
//                        break;
//                    case KeyEvent.VK_SPACE:
//                        game.dropDown();
//                        game.score += 1;
//                        break;
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        });
// Make the falling piece drop every second
        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        game.dropDown();
                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();
    }
}

