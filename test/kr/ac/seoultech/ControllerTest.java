package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.opentest4j.AssertionFailedError;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ControllerTest {
    private JFXPanel panel = new JFXPanel();

    @Test
    void moveRight() {
        Form form = Controller.makeRect("o");
        int tempMax = Tetris.XMAX - Tetris.SIZE;
        while(true){
            Controller.MoveRight(form);
            if (form.a.getX() == tempMax) {
                System.out.println(form.a.getX());
                Controller.MoveRight(form);
                System.out.println(form.a.getX());
                assertEquals(form.a.getX(),tempMax);
                break;
            }else if(form.b.getX() == tempMax){
                System.out.println(form.b.getX());
                Controller.MoveRight(form);
                System.out.println(form.b.getX());
                assertEquals(form.b.getX(),tempMax);
                break;
            }else  if(form.c.getX() == tempMax){
                System.out.println(form.c.getX());
                Controller.MoveRight(form);
                System.out.println(form.c.getX());
                assertEquals(form.c.getX(),tempMax);
                break;
            }else if(form.d.getX() == tempMax){
                System.out.println(form.d.getX());
                Controller.MoveRight(form);
                System.out.println(form.d.getX());
                assertEquals(form.d.getX(),tempMax);
                break;
            }
        }

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

        while(true){
            Controller.MoveRight(weight);
            if (form.a.getX() == tempMax) {
                System.out.println(form.a.getX());
                Controller.MoveRight(form);
                System.out.println(form.a.getX());
                assertEquals(form.a.getX(),tempMax);
                break;
            }else if(form.b.getX() == tempMax){
                System.out.println(form.b.getX());
                Controller.MoveRight(form);
                System.out.println(form.b.getX());
                assertEquals(form.b.getX(),tempMax);
                break;
            }else  if(form.c.getX() == tempMax){
                System.out.println(form.c.getX());
                Controller.MoveRight(form);
                System.out.println(form.c.getX());
                assertEquals(form.c.getX(),tempMax);
                break;
            }else if(form.d.getX() == tempMax){
                System.out.println(form.d.getX());
                Controller.MoveRight(form);
                System.out.println(form.d.getX());
                assertEquals(form.d.getX(),tempMax);
                break;
            }
        }
    }

    @Test
    void moveLeft() {
        Form form = Controller.makeRect("o");
        while(true){
            Controller.MoveLeft(form);
            if (form.a.getX() == 0) {
                System.out.println(form.a.getX());
                Controller.MoveLeft(form);
                System.out.println(form.a.getX());
                assertEquals(form.a.getX(),0.0);
                break;
            }else if(form.b.getX() == 0){
                System.out.println(form.b.getX());
                Controller.MoveLeft(form);
                System.out.println(form.b.getX());
                assertEquals(form.b.getX(),0.0);
                break;
            }else  if(form.c.getX() == 0){
                System.out.println(form.c.getX());
                Controller.MoveLeft(form);
                System.out.println(form.c.getX());
                assertEquals(form.c.getX(),0.0);
                break;
            }else if(form.d.getX() == 0){
                System.out.println(form.d.getX());
                Controller.MoveLeft(form);
                System.out.println(form.d.getX());
                assertEquals(form.d.getX(),0.0);
                break;
            }
        }

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

        while(true){
            Controller.MoveLeft(weight);
            if (form.a.getX() == 0) {
                System.out.println(form.a.getX());
                Controller.MoveLeft(form);
                System.out.println(form.a.getX());
                assertEquals(form.a.getX(),0.0);
                break;
            }else if(form.b.getX() == 0){
                System.out.println(form.b.getX());
                Controller.MoveLeft(form);
                System.out.println(form.b.getX());
                assertEquals(form.b.getX(),0.0);
                break;
            }else  if(form.c.getX() == 0){
                System.out.println(form.c.getX());
                Controller.MoveLeft(form);
                System.out.println(form.c.getX());
                assertEquals(form.c.getX(),0.0);
                break;
            }else if(form.d.getX() == 0){
                System.out.println(form.d.getX());
                Controller.MoveLeft(form);
                System.out.println(form.d.getX());
                assertEquals(form.d.getX(),0.0);
                break;
            }
        }
    }

    @Test
    void makeRect() {
        Form form;
        int[] count = new int[7];
        int round = 100000;
        int testRound = 1;

        for(int n = 0; n < testRound; n++){
            // EASY 모드 테스트
            Tetris.level = Tetris.Difficulty.Easy;
            for(int i = 0; i < round; i++){
                form = Controller.makeRect("o");
                switch (form.getName()){
                    case "j":
                        count[0]++;
                        break;
                    case "l":
                        count[1]++;
                        break;
                    case "o":
                        count[2]++;
                        break;
                    case "s":
                        count[3]++;
                        break;
                    case "t":
                        count[4]++;
                        break;
                    case "z":
                        count[5]++;
                        break;
                    case "i":
                        count[6]++;
                        break;
                    default:
                        throw new AssertionFailedError("add not equals");
                }
            }
            System.out.println("EASY MODE TEST");
            System.out.println("j = " + ((float)count[0]/(float)round)*100 + "%");
            System.out.println("l = " + ((float)count[1]/(float)round)*100 + "%");
            System.out.println("o = " + ((float)count[2]/(float)round)*100 + "%");
            System.out.println("s = " + ((float)count[3]/(float)round)*100 + "%");
            System.out.println("t = " + ((float)count[4]/(float)round)*100 + "%");
            System.out.println("z = " + ((float)count[5]/(float)round)*100 + "%");
            System.out.println("i = " + ((float)count[6]/(float)round)*100 + "%");
            for(int i = 0; i < 7; i++){
                double temp;
                if(i == 6){
                    // 0.1666666666666667 +- 5퍼센트
                    temp = 0.1666666666666667;
                    assertFalse(temp*0.95 > (float)count[i]/(float)round || temp*1.05 < (float)count[i]/(float)round );
                    break;
                }
                // 0.1388888888888889 +- 5퍼센트
                temp = 0.1388888888888889;
                assertFalse(temp*0.95 > (float)count[i]/(float)round || temp*1.05 < (float)count[i]/(float)round );
            }
            for(int i = 0; i < 7; i++){
                count[i] = 0;
            }

            // NORMAL 모드 테스트
            Tetris.level = Tetris.Difficulty.Normal;
            for(int i = 0; i < round; i++){
                form = Controller.makeRect("o");
                switch (form.getName()){
                    case "j":
                        count[0]++;
                        break;
                    case "l":
                        count[1]++;
                        break;
                    case "o":
                        count[2]++;
                        break;
                    case "s":
                        count[3]++;
                        break;
                    case "t":
                        count[4]++;
                        break;
                    case "z":
                        count[5]++;
                        break;
                    case "i":
                        count[6]++;
                        break;
                    default:
                        throw new AssertionFailedError("add not equals");
                }
            }
            System.out.println("Normal MODE TEST");
            System.out.println("j = " + ((float)count[0]/(float)round)*100 + "%");
            System.out.println("l = " + ((float)count[1]/(float)round)*100 + "%");
            System.out.println("o = " + ((float)count[2]/(float)round)*100 + "%");
            System.out.println("s = " + ((float)count[3]/(float)round)*100 + "%");
            System.out.println("t = " + ((float)count[4]/(float)round)*100 + "%");
            System.out.println("z = " + ((float)count[5]/(float)round)*100 + "%");
            System.out.println("i = " + ((float)count[6]/(float)round)*100 + "%");
            for(int i = 0; i < 7; i++){
                // 0.1428571428571429 +- 5퍼센트
                double temp = 0.1428571428571429;
                assertFalse(temp*0.95 > (float)count[i]/(float)round || temp*1.05 < (float)count[i]/(float)round );
            }
            for(int i = 0; i < 7; i++){
                count[i] = 0;
            }

            // HARD 모드 테스트
            Tetris.level = Tetris.Difficulty.Hard;
            for(int i = 0; i < round; i++){
                form = Controller.makeRect("o");
                switch (form.getName()){
                    case "j":
                        count[0]++;
                        break;
                    case "l":
                        count[1]++;
                        break;
                    case "o":
                        count[2]++;
                        break;
                    case "s":
                        count[3]++;
                        break;
                    case "t":
                        count[4]++;
                        break;
                    case "z":
                        count[5]++;
                        break;
                    case "i":
                        count[6]++;
                        break;
                    default:
                        throw new AssertionFailedError("add not equals");
                }
            }
            System.out.println("Hard MODE TEST");
            System.out.println("j = " + ((float)count[0]/(float)round)*100 + "%");
            System.out.println("l = " + ((float)count[1]/(float)round)*100 + "%");
            System.out.println("o = " + ((float)count[2]/(float)round)*100 + "%");
            System.out.println("s = " + ((float)count[3]/(float)round)*100 + "%");
            System.out.println("t = " + ((float)count[4]/(float)round)*100 + "%");
            System.out.println("z = " + ((float)count[5]/(float)round)*100 + "%");
            System.out.println("i = " + ((float)count[6]/(float)round)*100 + "%");
            for(int i = 0; i < 7; i++){
                double temp;
                if(i == 6){
                    // 0.1176470588235294 +- 5퍼센트
                    temp = 0.1176470588235294;
                    assertFalse(temp*0.95 > (float)count[i]/(float)round || temp*1.05 < (float)count[i]/(float)round );
                    break;
                }
                // 0.1470588235294118 +- 5퍼센트
                temp = 0.1470588235294118;
                assertFalse(temp*0.95 > (float)count[i]/(float)round || temp*1.05 < (float)count[i]/(float)round );
            }
            for(int i = 0; i < 7; i++){
                count[i] = 0;
            }
        }

    }

    @Test
    void makeItem(){
        Form form;
        int[] count = new int[5];
        int round = 100000;
        for(int i = 0; i < round; i++){
            Form.colorBlindMode = !Form.colorBlindMode;
            form = Controller.makeItem();
            switch (form.getName()){
                case "weight":
                    count[0]++;
                    break;
                default:
                    if(form.a.getText() == form.b.getText() && form.b.getText() == form.c.getText() && form.c.getText() == form.d.getText()){
                        throw new AssertionFailedError("add not equals");
                    }else{
                        String itemText;
                        if(form.a.getText() == form.b.getText() && form.b.getText() == form.c.getText()){
                            itemText = form.d.getText();
                        }else if(form.a.getText() == form.b.getText() && form.b.getText() == form.d.getText()){
                            itemText = form.c.getText();
                        }else if(form.a.getText() == form.c.getText() && form.c.getText() == form.d.getText()){
                            itemText = form.b.getText();
                        }else if(form.b.getText() == form.c.getText() && form.c.getText() == form.d.getText()){
                            itemText = form.a.getText();
                        }else{
                            itemText = null;
                        }

                        switch (itemText) {
                            case "B":
                                count[1]++;
                                break;
                            case "S":
                                count[2]++;
                                break;
                            case "F":
                                count[3]++;
                                break;
                            case "L":
                                count[4]++;
                                break;
                            default:
                                throw new AssertionFailedError("add not equals");
                        }
                    }
            }
        }

        System.out.println("ITEM BLOCK TEST");
        System.out.println("weight = " + ((float)count[0]/(float)round)*100 + "%");
        System.out.println("B = " + ((float)count[1]/(float)round)*100 + "%");
        System.out.println("S = " + ((float)count[2]/(float)round)*100 + "%");
        System.out.println("F = " + ((float)count[3]/(float)round)*100 + "%");
        System.out.println("L = " + ((float)count[4]/(float)round)*100 + "%");
        for(int i = 0; i < 5; i++){
            // 0.2 +- 5퍼센트
            double temp = 0.2;
            assertFalse(temp*0.95 > (float)count[i]/(float)round || temp*1.05 < (float)count[i]/(float)round );
        }
    }
}