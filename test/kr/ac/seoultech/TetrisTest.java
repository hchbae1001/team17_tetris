package kr.ac.seoultech;


import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;





import static org.junit.jupiter.api.Assertions.*;

class TetrisTest {
    private JFXPanel panel = new JFXPanel();

    @Test
    void speedUp(){
        Tetris tetris = new Tetris();
        try {
            while(true){
                tetris.speedUp();

                Field field1 = tetris.getClass().getDeclaredField("bonusScore");
                field1.setAccessible(true);
                int bonusScore = (int)field1.get(tetris);

                Field field2 = tetris.getClass().getDeclaredField("dropPeriod");
                field2.setAccessible(true);
                int dropPeriod = (int)field2.get(tetris);

                Field field3 = tetris.getClass().getDeclaredField("limitDropPeriod");
                field3.setAccessible(true);
                int limitDropPeriod = (int)field3.get(tetris);

                System.out.println(dropPeriod);

                if(dropPeriod == limitDropPeriod){
                    System.out.println("bonusScore :");
                    System.out.println(bonusScore);
                    System.out.println("dropPeriod :");
                    System.out.println(dropPeriod);
                    System.out.println("limitDropPeriod :");
                    System.out.println(limitDropPeriod);
                    break;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}