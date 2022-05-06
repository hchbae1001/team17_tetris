package kr.ac.seoultech;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.Queue;

public class InputThread implements Runnable{

    Queue<KeyCode> inputQueue;
    Tetris tetris;
    private boolean isWaitESCAPE;

    public InputThread(Queue<KeyCode> queue, Tetris _tetris){
        inputQueue = queue;
        tetris = _tetris;
        isWaitESCAPE = false;
    }
    @Override
    public void run(){
        System.out.println("input thread start");

        waitAndrun();

        System.out.println("input thread end");
    }

    public void waitAndrun(){
        while (!Thread.currentThread().isInterrupted()) {
            if(isWaitESCAPE){
                System.out.println("isWaitESCAPE");
                if (!inputQueue.isEmpty()) {
                    KeyCode keyCode = inputQueue.poll();
                    //System.out.println(keyCode);
                    if(keyCode == KeyCode.ESCAPE) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tetris.goToMenu();
                            }
                        });
                        break;
                    }
                }
            }else if(!tetris.getGame()){
                tetris.cpModeEnd();
                tetris.player2.cpModeEnd();
                isWaitESCAPE = true;
            }
            if (!inputQueue.isEmpty() && tetris.getGame()) {
                KeyCode keyCode = inputQueue.poll();
                //System.out.println(keyCode);
                if (!tetris.getItemAnim() && !tetris.getTimeStop()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            tetris.WASDKeyCodeFunc(keyCode, tetris.getObject());
                        }
                    });

                    //tetris.arrowKeyCodeFunc(keyCode, tetris.getObject());
                }
            } else {
                //System.out.println("input Queue is Empty");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    break;
                }
            }
        }
    }
}