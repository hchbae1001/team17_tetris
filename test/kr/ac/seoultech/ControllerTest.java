package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.opentest4j.AssertionFailedError;


import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private JFXPanel panel = new JFXPanel();

    @Test
    void moveRight() {
        Form form = Controller.makeRect("o");
        int tempMax = Controller.XMAX - Controller.SIZE;
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
    }

    @Test
    void makeRect() {

        System.out.println((float)7/100);
        Form form = Controller.makeRect("o");
        assertNotEquals(form.getName(),null);
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
}