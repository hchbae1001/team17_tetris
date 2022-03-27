package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

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
        Form form = Controller.makeRect("o");
        System.out.println(form.getName());
        assertNotEquals(form.getName(),null);
    }
}