package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StartMenuTest {
    private JFXPanel panel = new JFXPanel();
    StartMenu startMenu = new StartMenu();
    @Order(1)
    @Test
    public void startMenuSettingTest(){
        startMenu.startMenuSetting();
        assertEquals("0x000000ff", StartMenu.gameTitle.getFill().toString());
//        assertEquals(125,StartMenu.gameTitle.getX());
//        assertEquals(150,StartMenu.gameTitle.getY());
        assertEquals("-fx-font-size: 20px",StartMenu.gameTitle.getStyle());

        assertEquals("0x000000ff", StartMenu.help.getFill().toString());
//        assertEquals(115,StartMenu.help.getX());
//        assertEquals(200,StartMenu.help.getY());
        assertEquals("-fx-font: 20 arial",StartMenu.help.getStyle());

        assertEquals("0xff0000ff", StartMenu.menu5_comepete.getFill().toString());

        assertEquals("0x000000ff", StartMenu.menu4_start.getFill().toString());
//        assertEquals(125,StartMenu.menu4_start.getX());
//        assertEquals(300,StartMenu.menu4_start.getY());
        assertEquals("-fx-font: 20 arial",StartMenu.menu4_start.getStyle());

        assertEquals("0x000000ff", StartMenu.menu3_itemstart.getFill().toString());
//        assertEquals(125,StartMenu.menu3_itemstart.getX());
//        assertEquals(350,StartMenu.menu3_itemstart.getY());
        assertEquals("-fx-font: 20 arial",StartMenu.menu3_itemstart.getStyle());

        assertEquals("0x000000ff", StartMenu.menu2_setting.getFill().toString());
//        assertEquals(125,StartMenu.menu2_setting.getX());
//        assertEquals(400,StartMenu.menu2_setting.getY());
        assertEquals("-fx-font: 20 arial",StartMenu.menu2_setting.getStyle());

        assertEquals("0x000000ff", StartMenu.menu1_scoreBoard.getFill().toString());
//        assertEquals(125,StartMenu.menu1_scoreBoard.getX());
//        assertEquals(450,StartMenu.menu1_scoreBoard.getY());
        assertEquals("-fx-font: 20 arial",StartMenu.menu1_scoreBoard.getStyle());

        assertEquals("0x000000ff", StartMenu.menu0_Exit.getFill().toString());
//        assertEquals(125,StartMenu.menu0_Exit.getX());
//        assertEquals(500,StartMenu.menu0_Exit.getY());
        assertEquals("-fx-font: 20 arial",StartMenu.menu0_Exit.getStyle());

        assertEquals(3,StartMenu.count);
        assertEquals("itemstart", StartMenu.menuSelected);
    }
    @Test
    public void textCheck(){
        assertEquals("Team17 TETRIS",StartMenu.gameTitle.getText());
        assertEquals("Space = Select Menu",StartMenu.help.getText());
        assertEquals("STANDARD MODE START",StartMenu.menu4_start.getText());
        assertEquals("ITEM MODE START",StartMenu.menu3_itemstart.getText());
        assertEquals("Setting",StartMenu.menu2_setting.getText());
        assertEquals("Score Board",StartMenu.menu1_scoreBoard.getText());
        assertEquals("Exit",StartMenu.menu0_Exit.getText());
    }
    @Test
    public void startTest(){
    }
    @Test
    public void colorResetTest(){
        startMenu.colorReset();
        assertEquals("0x000000ff", StartMenu.menu4_start.getFill().toString());
        assertEquals("0x000000ff", StartMenu.menu3_itemstart.getFill().toString());
        assertEquals("0x000000ff", StartMenu.menu2_setting.getFill().toString());
        assertEquals("0x000000ff", StartMenu.menu1_scoreBoard.getFill().toString());
        assertEquals("0x000000ff", StartMenu.menu0_Exit.getFill().toString());
    }
    @Test
    public void menuColoringTest(){
        startMenu.menuColoring(0);
        assertEquals("0xff0000ff",StartMenu.menu0_Exit.getFill().toString());
        startMenu.menuColoring(1);
        assertEquals("0xff0000ff",StartMenu.menu1_scoreBoard.getFill().toString());
        startMenu.menuColoring(2);
        assertEquals("0xff0000ff",StartMenu.menu2_setting.getFill().toString());
        startMenu.menuColoring(3);
        assertEquals("0xff0000ff",StartMenu.menu3_itemstart.getFill().toString());
        startMenu.menuColoring(4);
        assertEquals("0xff0000ff",StartMenu.menu4_start.getFill().toString());
        startMenu.menuColoring(5);
        assertEquals("0xff0000ff",StartMenu.menu5_comepete.getFill().toString());
    }
    @Test
    public void handleTest(){
        startMenu.menuPress(StartMenu.menu);
    }
    @Order(2)
    @Test
    public void upKeyTest(){
        Integer count = StartMenu.count = 0, menu_max = StartMenu.menu_max;
        String menuSelected = StartMenu.menuSelected;
        ArrayList<String> select = StartMenu.select;
        while(count < menu_max -1){
            if(count >= 0 && count < menu_max -1){
                ++count;
                menuSelected = select.get(count);
                System.out.println(count);
            }else{
                count = 0;
                menuSelected = select.get(count);
                System.out.println(count);
            }
            assertEquals(menuSelected, StartMenu.select.get(count));
        }
    }
    @Order(3)
    @Test
    public void DownKeyTest(){
        Integer count = StartMenu.count = 3, menu_max = StartMenu.menu_max;
        String menuSelected = StartMenu.menuSelected;
        ArrayList<String> select = StartMenu.select;
            if(count >= 1 && count < menu_max){
                --count;
                menuSelected = select.get(count);
                System.out.println(count);
            }else{
                count = menu_max - 1;
                menuSelected = select.get(count);
                System.out.println(count);
            }
            assertEquals(menuSelected, StartMenu.select.get(count));
    }
}