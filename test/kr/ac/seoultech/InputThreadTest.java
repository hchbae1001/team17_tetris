package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class InputThreadTest {
    static private JFXPanel panel;
    @Test
    void waitAndrun() {
        try {
            panel = new JFXPanel();
            Tetris.itemModeBool = false;
            Tetris.isTest = true;
            Tetris tetris = new Tetris();
            tetris.createTetrisThread();
            tetris.createInputThread();
            tetris.setNewGame();


            Field inputQueue = tetris.getClass().getDeclaredField("inputQueue");
            inputQueue.setAccessible(true);
            Queue<KeyCode> _inputQueue = (Queue<KeyCode>) inputQueue.get(tetris);

            _inputQueue.add(KeyCode.T);

            Field game = tetris.getClass().getDeclaredField("game");
            game.setAccessible(true);
            game.set(tetris,false);

            Thread.sleep(20);
            Field inputThread = tetris.getClass().getDeclaredField("inputThread");
            inputThread.setAccessible(true);
            Thread _inputThread = (Thread) inputThread.get(tetris);
            _inputThread.interrupt();
            tetris.deleteOldGame();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}