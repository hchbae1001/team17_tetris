package kr.ac.seoultech;

import java.awt.event.KeyEvent;
import java.util.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import javafx.scene.text.Text;

import javax.swing.*;
import java.lang.reflect.Field;


 import java.util.Random;
import java.util.Timer;


import static org.junit.jupiter.api.Assertions.*;

class TetrisTest {
    static private JFXPanel panel;

    @BeforeAll
    static void beforAll(){
        try {
            panel = new JFXPanel();
            Tetris tetris = new Tetris();
            Tetris.itemModeBool = false;
            Tetris.isTest = true;

            tetris.main(null);
            tetris.deleteOldGame();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    @Test
    void Start() {
        try {
            Tetris tetris = new Tetris();
            Tetris.itemModeBool = false;
            Tetris.isTest = true;

            tetris.main(null);
            tetris.deleteOldGame();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

     */

    @Test
    void arrayKeyCodeFunc() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field isPaused = tetris.getClass().getDeclaredField("isPaused");
            isPaused.setAccessible(true);

            Field pauseSelected = tetris.getClass().getDeclaredField("pauseSelected");
            pauseSelected.setAccessible(true);

            Field pause_max = tetris.getClass().getDeclaredField("pause_max");
            pause_max.setAccessible(true);
            Integer p_max = (Integer)pause_max.get(tetris);

            Field pauseCount = tetris.getClass().getDeclaredField("pauseCount");
            pauseCount.setAccessible(true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane _group = (Pane) group.get(tetris);

            Field weightIsLocked = tetris.getClass().getDeclaredField("weightIsLocked");
            weightIsLocked.setAccessible(true);


            tetris.setNewGame();

            isPaused.set(tetris, true);
            Form form = Controller.makeRect("o");
            tetris.arrowKeyCodeFunc(KeyCode.ESCAPE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Continue");
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Restart");
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

/*
            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Go To Menu");
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Quit game");
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

 */


            isPaused.set(tetris, true);
            for(int i = p_max - 1; i >= 0; i--){
                assertEquals(i,pauseCount.get(tetris));
                tetris.arrowKeyCodeFunc(KeyCode.DOWN,form);
            }
            assertEquals(p_max - 1,pauseCount.get(tetris));

            pauseCount.set(tetris, 0);
            for(int i = 0; i <= p_max - 1; i++){
                assertEquals(i,pauseCount.get(tetris));
                tetris.arrowKeyCodeFunc(KeyCode.UP,form);
            }
            assertEquals(0,pauseCount.get(tetris));


            isPaused.set(tetris, false);
            _group.getChildren().clear();
            tetris.arrowKeyCodeFunc(KeyCode.ESCAPE,form);
            System.out.println(_group.getChildren().get(0));
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            tetris.arrowKeyCodeFunc(KeyCode.UP,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            weightIsLocked.set(tetris, true);
            tetris.arrowKeyCodeFunc(KeyCode.RIGHT,form);
            weightIsLocked.set(tetris, false);
            tetris.arrowKeyCodeFunc(KeyCode.RIGHT,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            tetris.arrowKeyCodeFunc(KeyCode.DOWN,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            weightIsLocked.set(tetris, true);
            tetris.arrowKeyCodeFunc(KeyCode.LEFT,form);
            weightIsLocked.set(tetris, false);
            tetris.arrowKeyCodeFunc(KeyCode.LEFT,form);
            tetris.deleteOldGame();



        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void WASDKeyCodeFunc() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field isPaused = tetris.getClass().getDeclaredField("isPaused");
            isPaused.setAccessible(true);

            Field pauseSelected = tetris.getClass().getDeclaredField("pauseSelected");
            pauseSelected.setAccessible(true);

            Field pause_max = tetris.getClass().getDeclaredField("pause_max");
            pause_max.setAccessible(true);
            Integer p_max = (Integer)pause_max.get(tetris);

            Field pauseCount = tetris.getClass().getDeclaredField("pauseCount");
            pauseCount.setAccessible(true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane _group = (Pane) group.get(tetris);

            Field weightIsLocked = tetris.getClass().getDeclaredField("weightIsLocked");
            weightIsLocked.setAccessible(true);

            tetris.setNewGame();

            isPaused.set(tetris, true);
            Form form = Controller.makeRect("o");
            tetris.WASDKeyCodeFunc(KeyCode.ESCAPE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Continue");
            tetris.WASDKeyCodeFunc(KeyCode.SPACE,form);

            tetris.deleteOldGame();

            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Restart");
            tetris.WASDKeyCodeFunc(KeyCode.SPACE,form);

            tetris.deleteOldGame();

/*
            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Go To Menu");
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, true);
            pauseSelected.set(tetris, "Quit game");
            tetris.arrowKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

 */
            isPaused.set(tetris, true);
            for(int i = p_max - 1; i >= 0; i--){
                assertEquals(i, pauseCount.get(tetris));
                tetris.WASDKeyCodeFunc(KeyCode.S,form);
            }
            assertEquals(p_max - 1,pauseCount.get(tetris));

            pauseCount.set(tetris, 0);
            for(int i = 0; i <= p_max - 1; i++){
                assertEquals(i,pauseCount.get(tetris));
                tetris.WASDKeyCodeFunc(KeyCode.W,form);
            }
            assertEquals(0,pauseCount.get(tetris));

            isPaused.set(tetris, false);
            _group.getChildren().clear();
            tetris.WASDKeyCodeFunc(KeyCode.ESCAPE,form);
            System.out.println(_group.getChildren().get(0));
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            tetris.WASDKeyCodeFunc(KeyCode.SPACE,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            tetris.WASDKeyCodeFunc(KeyCode.W,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            weightIsLocked.set(tetris, true);
            tetris.WASDKeyCodeFunc(KeyCode.D,form);
            weightIsLocked.set(tetris, false);
            tetris.WASDKeyCodeFunc(KeyCode.D,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            tetris.WASDKeyCodeFunc(KeyCode.S,form);
            tetris.deleteOldGame();

            isPaused.set(tetris, false);
            weightIsLocked.set(tetris, true);
            tetris.WASDKeyCodeFunc(KeyCode.A,form);
            weightIsLocked.set(tetris, false);
            tetris.WASDKeyCodeFunc(KeyCode.A,form);
            tetris.deleteOldGame();



            //tetris.arrowKeyCodeFunc(KeyCode.UP,form);
            //tetris.arrowKeyCodeFunc(KeyCode.RIGHT,form);
            //tetris.arrowKeyCodeFunc(KeyCode.DOWN,form);
            //tetris.arrowKeyCodeFunc(KeyCode.LEFT,form);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void MoveTurn(){
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();



            Field object = tetris.getClass().getDeclaredField("object");
            object.setAccessible(true);
            for(int i = 0; i < 100; i++){
                Form form = Controller.makeRect("o");
                assertEquals(1, form.form);
                tetris.MoveTurn(form);
                assertEquals(2, form.form);
                tetris.MoveTurn(form);
                assertEquals(3, form.form);
                tetris.MoveTurn(form);
                assertEquals(4, form.form);
                tetris.MoveTurn(form);
                assertEquals(1, form.form);
            }




            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void RemoveRows(){
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field object = tetris.getClass().getDeclaredField("object");
            object.setAccessible(true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane pane = (Pane) group.get(tetris);

            tetris.setNewGame();

            for(int i = 0; i < Tetris.XMAX/Tetris.MOVE; i++) {
                    pane.getChildren().add(new NewShape(i*Tetris.MOVE, 10*Tetris.MOVE, "o"));
                    Tetris.MESH[i][10] = 1;
            }

            tetris.RemoveRows(pane, 0);
            for(int i = 0; i < Tetris.XMAX/Tetris.MOVE; i++) {
                //System.out.println(Tetris.MESH[i][10]);
                assertEquals(0, Tetris.MESH[i][10]);
            }




            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void MoveDown() {
        Tetris tetris = new Tetris();
        NewShape newShape = new NewShape(0,Tetris.YMAX - Tetris.MOVE*2,"o");
        assertEquals(Tetris.YMAX - Tetris.MOVE*2, newShape.getY());
        tetris.MoveDown(newShape);
        assertEquals(Tetris.YMAX - Tetris.MOVE, newShape.getY());
        tetris.MoveDown(newShape);
        assertEquals(Tetris.YMAX - Tetris.MOVE, newShape.getY());
    }

    @Test
    void MoveRight(){
        Tetris tetris = new Tetris();
        NewShape newShape = new NewShape(Tetris.XMAX - Tetris.MOVE*2,0,"o");
        assertEquals(Tetris.XMAX - Tetris.MOVE*2, newShape.getX());
        tetris.MoveRight(newShape);
        assertEquals(Tetris.XMAX - Tetris.MOVE, newShape.getX());
        tetris.MoveRight(newShape);
        assertEquals(Tetris.XMAX - Tetris.MOVE, newShape.getX());
    }

    @Test
    void MoveLeft() {
        Tetris tetris = new Tetris();
        NewShape newShape = new NewShape(Tetris.MOVE,0,"o");
        assertEquals(Tetris.MOVE, newShape.getX());
        tetris.MoveLeft(newShape);
        assertEquals(0, newShape.getX());
        tetris.MoveLeft(newShape);
        assertEquals(0, newShape.getX());
    }

    @Test
    void MoveUp() {
        Tetris tetris = new Tetris();
        NewShape newShape = new NewShape(0,Tetris.MOVE*2,"o");
        assertEquals(Tetris.MOVE*2, newShape.getY());
        tetris.MoveUp(newShape);
        assertEquals(Tetris.MOVE, newShape.getY());
        tetris.MoveUp(newShape);
        assertEquals(Tetris.MOVE, newShape.getY());
    }

    @Test
    void BlockDown() {
        //
    }

    @Test
    void MakeObject() {
        try {
            Tetris tetris = new Tetris();

            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field nextObj = tetris.getClass().getDeclaredField("nextObj");
            nextObj.setAccessible(true);

            Field itemModeBool = tetris.getClass().getDeclaredField("itemModeBool");
            itemModeBool.setAccessible(true);

            Field linesNo = tetris.getClass().getDeclaredField("linesNo");
            linesNo.setAccessible(true);

            Field itemCount = tetris.getClass().getDeclaredField("itemCount");
            itemCount.setAccessible(true);

            tetris.setNewGame();

            itemModeBool.set(tetris, false);
            tetris.MakeObject();
            Form form = (Form) nextObj.get(tetris);
            String name = form.getName();
            System.out.println(name);
            assertTrue(name == "j" || name == "l" || name == "o" || name == "s" || name == "t" || name == "z" || name == "i");
            // 이는 (일반)MakeObject 호출로 생성된 블럭

            linesNo.set(tetris, 0);
            itemCount.set(tetris, 0);
            itemModeBool.set(tetris, true);
            tetris.MakeObject();
            form = (Form) nextObj.get(tetris);
            name = form.getName();
            System.out.println(name);
            assertTrue(name == "j" || name == "l" || name == "o" || name == "s" || name == "t" || name == "z" || name == "i");
            // 이는 (아이템)MakeObject 호출이지만 라인수에 의해 일반 블럭 생성됨.

            for(int i = 0; i < 100; i++) {
                linesNo.set(tetris, 10);
                itemCount.set(tetris, 0);
                itemModeBool.set(tetris, true);
                tetris.MakeObject();
                form = (Form) nextObj.get(tetris);
                name = form.getName();
                switch (name) {
                    case "weight":
                        System.out.println(name);
                        break;
                    default:
                        if (form.a.getText() == form.b.getText() && form.b.getText() == form.c.getText() && form.c.getText() == form.d.getText()) {
                            throw new AssertionFailedError("add not equals");
                        } else {
                            String itemText;
                            if (form.a.getText() == form.b.getText() && form.b.getText() == form.c.getText()) {
                                itemText = form.d.getText();
                            } else if (form.a.getText() == form.b.getText() && form.b.getText() == form.d.getText()) {
                                itemText = form.c.getText();
                            } else if (form.a.getText() == form.c.getText() && form.c.getText() == form.d.getText()) {
                                itemText = form.b.getText();
                            } else if (form.b.getText() == form.c.getText() && form.c.getText() == form.d.getText()) {
                                itemText = form.a.getText();
                            } else {
                                throw new AssertionFailedError("add not equals");
                            }
                            System.out.println(itemText);
                            assertTrue(itemText == "B" || itemText == "S" || itemText == "F" || itemText == "L");
                        }
                }
                // 이는 (아이템)MakeObject 호출로 아이템 블럭 생성됨.
            }

            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    void moveA() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            Form form = new Form(new NewShape(0,0,"o"), new NewShape(), new NewShape(), new NewShape(), "test");
            assertFalse(tetris.moveA(form));
            tetris.MESH[(int) form.a.getX() / tetris.SIZE][((int) form.a.getY() / tetris.SIZE) + 1] = 1;
            assertTrue(tetris.moveA(form));
            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void moveB() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            Form form = new Form(new NewShape(), new NewShape(0,0,"o"), new NewShape(), new NewShape(), "test");
            assertFalse(tetris.moveB(form));
            tetris.MESH[(int) form.b.getX() / tetris.SIZE][((int) form.b.getY() / tetris.SIZE) + 1] = 1;
            assertTrue(tetris.moveB(form));
            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void moveC() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            Form form = new Form(new NewShape(), new NewShape(), new NewShape(0,0,"o"), new NewShape(), "test");
            assertFalse(tetris.moveC(form));
            tetris.MESH[(int) form.c.getX() / tetris.SIZE][((int) form.c.getY() / tetris.SIZE) + 1] = 1;
            assertTrue(tetris.moveC(form));
            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void moveD() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            Form form = new Form(new NewShape(), new NewShape(), new NewShape(), new NewShape(0,0,"o"), "test");
            assertFalse(tetris.moveD(form));
            tetris.MESH[(int) form.d.getX() / tetris.SIZE][((int) form.d.getY() / tetris.SIZE) + 1] = 1;
            assertTrue(tetris.moveD(form));
            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void cB() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            assertFalse(tetris.cB(new NewShape(0, 0, "o"), -1, 0));
            assertFalse(tetris.cB(new NewShape(0, 0, "o"), 0, 1));
            assertFalse(tetris.cB(new NewShape(tetris.XMAX - tetris.MOVE, 0, "o"), 1, 0));
            assertFalse(tetris.cB(new NewShape(0, tetris.YMAX - tetris.MOVE, "o"), 0, -1));
            tetris.MESH[0][1] = 1;
            assertFalse(tetris.cB(new NewShape(0, 0, "o"), 0, -1));
            tetris.MESH[0][1] = 0;

            assertTrue(tetris.cB(new NewShape(0, 0, "o"), 1, 0));
            assertTrue(tetris.cB(new NewShape(0, 0, "o"), 0, -1));
            assertTrue(tetris.cB(new NewShape(tetris.XMAX - tetris.MOVE, 0, "o"), -1, 0));
            assertTrue(tetris.cB(new NewShape(0, tetris.YMAX - tetris.MOVE, "o"), 0, 1));
            assertTrue(tetris.cB(new NewShape(0, 0, "o"), 0, -1));

            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Test
    void isOverlap() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();

            Form form = new Form(new NewShape(tetris.MOVE,tetris.MOVE*5,"o"),
                    new NewShape(tetris.MOVE*2,tetris.MOVE*5,"o"),
                    new NewShape(tetris.MOVE*3,tetris.MOVE*5,"o"),
                    new NewShape(tetris.MOVE*4,tetris.MOVE*5,"o"), "test");

            Field object = tetris.getClass().getDeclaredField("object");
            object.setAccessible(true);
            object.set(tetris, form);

            tetris.MESH[1][5] = 1;
            tetris.MESH[2][4] = 1;
            tetris.MESH[3][3] = 1;
            tetris.MESH[4][2] = 1;

            tetris.isOverlap();

            assertEquals(tetris.MOVE,form.a.getY());
            assertEquals(tetris.MOVE,form.b.getY());
            assertEquals(tetris.MOVE,form.c.getY());
            assertEquals(tetris.MOVE,form.d.getY());

            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkGameover() {
        try{
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            tetris.MESH[0][Tetris.DEADLINEGAP - 1] = 1;
            Field top = tetris.getClass().getDeclaredField("top");
            top.setAccessible(true);
            assertFalse((Boolean) top.get(tetris));
            tetris.checkGameover();
            assertTrue((Boolean) top.get(tetris));

            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    void showGameover() {
        try{
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();

            tetris.showGameover();


            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void speedUp(){
        Tetris tetris = new Tetris();
        try {
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field bonusScore = tetris.getClass().getDeclaredField("bonusScore");
            bonusScore.setAccessible(true);

            Field dropPeriod = tetris.getClass().getDeclaredField("dropPeriod");
            dropPeriod.setAccessible(true);

            Field limitDropPeriod = tetris.getClass().getDeclaredField("limitDropPeriod");
            limitDropPeriod.setAccessible(true);

            Field level = tetris.getClass().getDeclaredField("level");
            level.setAccessible(true);

            // 이지
            level.set(tetris, Tetris.Difficulty.Easy);
            tetris.setNewGame();
            while(true){
                int tempSpeed = (int)dropPeriod.get(tetris);
                tetris.speedUp();

                //System.out.println(dropPeriod.get(tetris));

                if((int)dropPeriod.get(tetris) == (int)limitDropPeriod.get(tetris)){
                    tetris.speedUp();
                    assertEquals((int)limitDropPeriod.get(tetris), (int)dropPeriod.get(tetris));
                    break;
                }else{
                    assertEquals(tempSpeed - Tetris.SpeedUpRate*0.8, (int)dropPeriod.get(tetris));
                }
            }

            // 노말
            level.set(tetris, Tetris.Difficulty.Normal);
            tetris.deleteOldGame();
            tetris.setNewGame();
            while(true){
                int tempSpeed = (int)dropPeriod.get(tetris);
                tetris.speedUp();

                //System.out.println(dropPeriod.get(tetris));

                if((int)dropPeriod.get(tetris) == (int)limitDropPeriod.get(tetris)){
                    tetris.speedUp();
                    assertEquals((int)limitDropPeriod.get(tetris), (int)dropPeriod.get(tetris));
                    break;
                }else{
                    assertEquals(tempSpeed - Tetris.SpeedUpRate, (int)dropPeriod.get(tetris));
                }
            }

            // 하드
            level.set(tetris, Tetris.Difficulty.Hard);
            tetris.deleteOldGame();
            tetris.setNewGame();
            while(true){
                int tempSpeed = (int)dropPeriod.get(tetris);
                tetris.speedUp();

                //System.out.println(dropPeriod.get(tetris));

                if((int)dropPeriod.get(tetris) == (int)limitDropPeriod.get(tetris)){
                    tetris.speedUp();
                    assertEquals((int)limitDropPeriod.get(tetris), (int)dropPeriod.get(tetris));
                    break;
                }else{
                    assertEquals(tempSpeed - Tetris.SpeedUpRate*1.2, (int)dropPeriod.get(tetris));
                }
            }

            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Test
    void startTtimer(){
        try {
            Tetris tetris = new Tetris();

            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field isPaused = tetris.getClass().getDeclaredField("isPaused");
            isPaused.setAccessible(true);

            tetris.setNewGame();

            isPaused.set(tetris, true);
            Timer t1 = tetris.startTimer(0);

            isPaused.set(tetris, false);
            Timer t2 = tetris.startTimer(0);

            //Thread.sleep(1000);

            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void continueGame() {
        //
    }

    @Test
    void updateScoretext() {
        Tetris tetris = new Tetris();
        try {
            Field score = tetris.getClass().getDeclaredField("score");
            score.setAccessible(true);

            Field linesNo = tetris.getClass().getDeclaredField("linesNo");
            linesNo.setAccessible(true);

            Field scoretext = tetris.getClass().getDeclaredField("scoretext");
            scoretext.setAccessible(true);

            Field linetesxt = tetris.getClass().getDeclaredField("linetesxt");
            linetesxt.setAccessible(true);

            score.set(tetris, 10000);
            linesNo.set(tetris, 100);
            tetris.updateScoretext();

            Text st = (Text)scoretext.get(tetris);
            Text lt = (Text)linetesxt.get(tetris);

            assertEquals("Score: 10000", st.getText());
            assertEquals("Lines: 100", lt.getText());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setNewGame() {
        //
    }


    @Test
    void deleteOldGame() {
        Tetris tetris = new Tetris();
        try {
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);

            Field nextObjPane = tetris.getClass().getDeclaredField("nextObjPane");
            nextObjPane.setAccessible(true);

            Field pausePane = tetris.getClass().getDeclaredField("pausePane");
            pausePane.setAccessible(true);


            tetris.setNewGame();
            tetris.deleteOldGame();

            Pane _group = (Pane)group.get(tetris);
            Pane _nextObjPane = (Pane)nextObjPane.get(tetris);
            Pane _pausePane = (Pane)pausePane.get(tetris);

            assertEquals(0, _group.getChildren().stream().count());
            assertEquals(0, _nextObjPane.getChildren().stream().count());
            assertEquals(0, _pausePane.getChildren().stream().count());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void pauseColorReset() {
        Tetris tetris = new Tetris();
        try {
            Field pause3_continue = tetris.getClass().getDeclaredField("pause3_continue");
            pause3_continue.setAccessible(true);
            Text _pause3_continue = (Text) pause3_continue.get(tetris);

            Field pause2_restart = tetris.getClass().getDeclaredField("pause2_restart");
            pause2_restart.setAccessible(true);
            Text _pause2_restart = (Text) pause2_restart.get(tetris);

            Field pause1_menu = tetris.getClass().getDeclaredField("pause1_menu");
            pause1_menu.setAccessible(true);
            Text _pause1_menu = (Text) pause1_menu.get(tetris);

            Field pause0_quit = tetris.getClass().getDeclaredField("pause0_quit");
            pause0_quit.setAccessible(true);
            Text _pause0_quit = (Text) pause0_quit.get(tetris);

            _pause3_continue.setFill(Color.RED);
            _pause2_restart.setFill(Color.RED);
            _pause1_menu.setFill(Color.RED);
            _pause0_quit.setFill(Color.RED);

            assertNotEquals(Color.BLACK,_pause3_continue.getFill());
            assertNotEquals(Color.BLACK,_pause2_restart.getFill());
            assertNotEquals(Color.BLACK,_pause1_menu.getFill());
            assertNotEquals(Color.BLACK,_pause0_quit.getFill());

            tetris.pauseColorReset();

            assertEquals(Color.BLACK,_pause3_continue.getFill());
            assertEquals(Color.BLACK,_pause2_restart.getFill());
            assertEquals(Color.BLACK,_pause1_menu.getFill());
            assertEquals(Color.BLACK,_pause0_quit.getFill());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void pauseColoring() {
        Tetris tetris = new Tetris();
        try {
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field pause3_continue = tetris.getClass().getDeclaredField("pause3_continue");
            pause3_continue.setAccessible(true);
            Text _pause3_continue = (Text) pause3_continue.get(tetris);

            Field pause2_restart = tetris.getClass().getDeclaredField("pause2_restart");
            pause2_restart.setAccessible(true);
            Text _pause2_restart = (Text) pause2_restart.get(tetris);

            Field pause1_menu = tetris.getClass().getDeclaredField("pause1_menu");
            pause1_menu.setAccessible(true);
            Text _pause1_menu = (Text) pause1_menu.get(tetris);

            Field pause0_quit = tetris.getClass().getDeclaredField("pause0_quit");
            pause0_quit.setAccessible(true);
            Text _pause0_quit = (Text) pause0_quit.get(tetris);

            Field pauseCount = tetris.getClass().getDeclaredField("pauseCount");
            pauseCount.setAccessible(true);


            _pause3_continue.setFill(Color.BLUE);
            _pause2_restart.setFill(Color.BLUE);
            _pause1_menu.setFill(Color.BLUE);
            _pause0_quit.setFill(Color.BLUE);

            pauseCount.set(tetris, 3);
            tetris.pauseColoring();
            assertEquals(Color.RED,_pause3_continue.getFill());
            assertEquals(Color.BLACK,_pause2_restart.getFill());
            assertEquals(Color.BLACK,_pause1_menu.getFill());
            assertEquals(Color.BLACK,_pause0_quit.getFill());

            pauseCount.set(tetris, 2);
            tetris.pauseColoring();
            assertEquals(Color.BLACK,_pause3_continue.getFill());
            assertEquals(Color.RED,_pause2_restart.getFill());
            assertEquals(Color.BLACK,_pause1_menu.getFill());
            assertEquals(Color.BLACK,_pause0_quit.getFill());

            pauseCount.set(tetris, 1);
            tetris.pauseColoring();
            assertEquals(Color.BLACK,_pause3_continue.getFill());
            assertEquals(Color.BLACK,_pause2_restart.getFill());
            assertEquals(Color.RED,_pause1_menu.getFill());
            assertEquals(Color.BLACK,_pause0_quit.getFill());

            pauseCount.set(tetris, 0);
            tetris.pauseColoring();
            assertEquals(Color.BLACK,_pause3_continue.getFill());
            assertEquals(Color.BLACK,_pause2_restart.getFill());
            assertEquals(Color.BLACK,_pause1_menu.getFill());
            assertEquals(Color.RED,_pause0_quit.getFill());

            pauseCount.set(tetris, 3);
            tetris.pauseColoring();
            assertEquals(Color.RED,_pause3_continue.getFill());
            assertEquals(Color.BLACK,_pause2_restart.getFill());
            assertEquals(Color.BLACK,_pause1_menu.getFill());
            assertEquals(Color.BLACK,_pause0_quit.getFill());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void waitForTimer() {
        //
    }

    @Test
    void deleteAnim() {
        Tetris tetris = new Tetris();
        try {
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            tetris.itemModeBool = true;
            tetris.itemModeInt = 1;

            Field object = tetris.getClass().getDeclaredField("object");
            object.setAccessible(true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane _group = (Pane) group.get(tetris);

            NewShape a = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "o");

            tetris.deleteAnim1(a, _group, 1);

            NewShape b = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "o");

            tetris.deleteAnim2(b, _group, 1);


            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteAnim2() {
        //
    }

    // 아이템 모드용 메서드들

    @Test
    void weightDown() {
        Tetris tetris = new Tetris();
        try {
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);
            tetris.setNewGame();
            tetris.itemModeBool = true;
            tetris.itemModeInt = 1;

            Field timer = tetris.getClass().getDeclaredField("timer");
            timer.setAccessible(true);

            timer.set(tetris, new Timer());

            Field object = tetris.getClass().getDeclaredField("object");
            object.setAccessible(true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane _group = (Pane) group.get(tetris);

            NewShape a = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), b = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), c = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"),
                    d = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), e = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), f = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M");
            a.setX(Tetris.XMAX / 2 - Tetris.SIZE - Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            e.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            e.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 2));
            f.setX(Tetris.XMAX / 2);
            f.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 2));
            FormSix weight = new FormSix(a, b, c, d, e, f, "weight");

            _group.getChildren().addAll(weight.a, weight.b, weight.c, weight.d, weight.e, weight.f);

            object.set(tetris,weight);
            Tetris.isWeightBlock = true;
            Form _object = (Form) object.get(tetris);

            for(int i = Tetris.DEADLINEGAP + 3; i < Tetris.YMAX/Tetris.SIZE - 1; i++) {
                for(int j = Tetris.XMAX/(2*Tetris.SIZE) -2; j <= Tetris.XMAX/(2*Tetris.SIZE) + 1; j++) {
                    tetris.MESH[j][i] = 1;
                }
            }


            while(true){
                tetris.BlockDown(_object);
                /*
                for(int i = 0; i < Tetris.YMAX/Tetris.SIZE - 1; i++) {
                    for(int j = 0; j <= Tetris.XMAX/Tetris.SIZE - 1; j++) {
                        System.out.print(tetris.MESH[j][i]);
                    }
                    System.out.println(" ");
                }
                System.out.println("끝");
                 */
                if(!tetris.isWeightBlock)
                    break;
            }

            for(int i = 0; i < Tetris.YMAX/Tetris.SIZE - 1; i++) {
                for(int j = 0; j <= Tetris.XMAX/Tetris.SIZE - 1; j++) {
                    assertEquals(0, tetris.MESH[j][i]);
                }
                System.out.println(" ");
            }


            tetris.deleteOldGame();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void weightDelete() {
        //
    }

    @Test
    void lineClearItem() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane pane =(Pane) group.get(tetris);

            tetris.setNewGame();
            pane.getChildren().add(new NewShape(Tetris.MOVE,Tetris.MOVE*10,"L"));
            Tetris.MESH[1][10] = 1;
            pane.getChildren().add(new NewShape(Tetris.MOVE*3,Tetris.MOVE*10,"o"));
            Tetris.MESH[3][10] = 1;
            pane.getChildren().add(new NewShape(Tetris.MOVE*5,Tetris.MOVE*10,"o"));
            Tetris.MESH[5][10] = 1;
            pane.getChildren().add(new NewShape(Tetris.MOVE*7,Tetris.MOVE*10,"o"));
            Tetris.MESH[7][10] = 1;

            tetris.lineClearItem(pane);

            for(int i = 0; i < Tetris.XMAX/Tetris.MOVE; i++) {
                //System.out.println(i + " and " + j);
                assertEquals(0, Tetris.MESH[i][10]);
            }


            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void bombItem() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane pane =(Pane) group.get(tetris);

            tetris.setNewGame();
            pane.getChildren().add(new NewShape(Tetris.MOVE*4,Tetris.MOVE*10,"B"));

            for(int i = 2; i < 7; i++) {
                for(int j = 8; j < 13; j++) {
                    pane.getChildren().add(new NewShape(i*Tetris.MOVE, j*Tetris.MOVE, "o"));
                    Tetris.MESH[i][j] = 1;
                }
            }
            tetris.bombItem(pane);

            for(int i = 2; i < 7; i++) {
                for(int j = 8; j < 13; j++) {
                    //System.out.println(i + " and " + j);
                    assertEquals(0, Tetris.MESH[i][j]);
                }
            }


            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void fillItem() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane pane =(Pane) group.get(tetris);

            tetris.setNewGame();
            pane.getChildren().add(new NewShape(Tetris.MOVE*4,Tetris.MOVE*10,"F"));

            tetris.fillItem(pane);
            assertEquals(1,Tetris.MESH[3][9]);
            assertEquals(1,Tetris.MESH[3][10]);
            assertEquals(1,Tetris.MESH[3][11]);
            assertEquals(1,Tetris.MESH[4][9]);
            assertEquals(1,Tetris.MESH[4][10]);
            assertEquals(1,Tetris.MESH[4][11]);
            assertEquals(1,Tetris.MESH[5][9]);
            assertEquals(1,Tetris.MESH[5][10]);
            assertEquals(1,Tetris.MESH[5][11]);


            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void socreItem() {
        try {
            Tetris tetris = new Tetris();
            Field isTest = tetris.getClass().getDeclaredField("isTest");
            isTest.setAccessible(true);
            isTest.set(tetris, true);

            Field group = tetris.getClass().getDeclaredField("group");
            group.setAccessible(true);
            Pane pane =(Pane) group.get(tetris);

            tetris.setNewGame();
            pane.getChildren().add(new NewShape(0,Tetris.MOVE,"S"));
            pane.getChildren().add(new NewShape(0,Tetris.MOVE*2,"S"));

            tetris.scoreItem(pane, Tetris.MOVE, 0, 1);
            assertEquals(100000, Tetris.score);

            tetris.scoreItem(pane, Tetris.MOVE, Tetris.MOVE*2, 0, 1);
            assertEquals(300000, Tetris.score);


            tetris.deleteOldGame();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }





}