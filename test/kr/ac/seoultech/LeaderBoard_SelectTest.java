package kr.ac.seoultech;

import com.sun.javafx.scene.control.skin.ColorPalette;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaderBoard_SelectTest {
    private JFXPanel panel = new JFXPanel();
    LeaderBoard_Select leaderBoard_select = new LeaderBoard_Select();

    @Test
    void boardSelectSetting()
    {
        leaderBoard_select.boardSelectSetting();
    }

    @Test
    void colorReset()
    {
        LeaderBoard_Select.modeSelected=false;
        leaderBoard_select.colorReset();
        assertEquals("0x000000ff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.hardMode.getFill().toString());
    }

    @Test
    void menuColoring()
    {
        //!modeselected, count1=1
        leaderBoard_select.menuColoring();
        assertEquals("0xff0000ff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.itemMode.getFill().toString());

        //!modeselected, count1=0
        LeaderBoard_Select.count1=0;
        leaderBoard_select.menuColoring();
        assertEquals("0x000000ff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.itemMode.getFill().toString());

        //modeselected, count1=1, count2=0;
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count1=1;
        LeaderBoard_Select.count2=0;
        leaderBoard_select.menuColoring();
        assertEquals("0x0000ffff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.hardMode.getFill().toString());

        //modeselected, count1=1, count2=1;
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count1=1;
        LeaderBoard_Select.count2=1;
        leaderBoard_select.menuColoring();
        assertEquals("0x0000ffff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.hardMode.getFill().toString());

        //modeselected, count1=1, count2=2;
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count1=1;
        LeaderBoard_Select.count2=2;
        leaderBoard_select.menuColoring();
        assertEquals("0x0000ffff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.hardMode.getFill().toString());

        //modeselected, count1=0, count2=0;
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count1=0;
        LeaderBoard_Select.count2=0;
        leaderBoard_select.menuColoring();
        assertEquals("0x0000ffff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0x0000ffff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.hardMode.getFill().toString());

        //modeselected, count1=0, count2=1;
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count1=0;
        LeaderBoard_Select.count2=1;
        leaderBoard_select.menuColoring();
        assertEquals("0x0000ffff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0x0000ffff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.hardMode.getFill().toString());

        //modeselected, count1=0, count2=2;
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count1=0;
        LeaderBoard_Select.count2=2;
        leaderBoard_select.menuColoring();
        assertEquals("0x0000ffff",LeaderBoard_Select.standardMode.getFill().toString());
        assertEquals("0x0000ffff",LeaderBoard_Select.itemMode.getFill().toString());
        assertEquals("0xff0000ff",LeaderBoard_Select.easeMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.normalMode.getFill().toString());
        assertEquals("0x000000ff",LeaderBoard_Select.hardMode.getFill().toString());
    }

    @Test
    void selectBoard()
    {
        //CASE UP , MODESELECTED = FALSE, COUNT1=1
        LeaderBoard_Select.modeSelected=false;
        LeaderBoard_Select.count1=1;
        leaderBoard_select.selectBoard(KeyCode.UP);
        assertEquals("ITEM",LeaderBoard_Select.menuSelected1);
        //CASE UP , MODESELECTED = FALSE, COUNT1=0
        LeaderBoard_Select.modeSelected=false;
        LeaderBoard_Select.count1=0;
        leaderBoard_select.selectBoard(KeyCode.UP);
        assertEquals("STANDARD",LeaderBoard_Select.menuSelected1);
        //CASE UP , MODESELECTED = TRUE, COUNT2=0
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count2=0;
        leaderBoard_select.selectBoard(KeyCode.UP);
        assertEquals("NORMAL",LeaderBoard_Select.menuSelected2);
        //CASE UP , MODESELECTED = TRUE, COUNT2=1
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count2=1;
        leaderBoard_select.selectBoard(KeyCode.UP);
        assertEquals("EASY",LeaderBoard_Select.menuSelected2);
        //CASE DOWN, MODESELECTED = FALSE, COUNT1=1
        LeaderBoard_Select.modeSelected=false;
        LeaderBoard_Select.count1=1;
        leaderBoard_select.selectBoard(KeyCode.DOWN);
        assertEquals("ITEM",LeaderBoard_Select.menuSelected1);
        //CASE DOWN, MODESELECTED = FALSE, COUNT1=0
        LeaderBoard_Select.modeSelected=false;
        LeaderBoard_Select.count1=0;
        leaderBoard_select.selectBoard(KeyCode.DOWN);
        assertEquals("STANDARD",LeaderBoard_Select.menuSelected1);
        //CASE DOWN, MODESELECTED = TRUE, COUNT2=1
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count2=1;
        leaderBoard_select.selectBoard(KeyCode.DOWN);
        assertEquals("HARD",LeaderBoard_Select.menuSelected2);
        //CASE DOWN, MODESELECTED = TRUE, COUNT2=0
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.count2=0;
        leaderBoard_select.selectBoard(KeyCode.DOWN);
        assertEquals("EASY",LeaderBoard_Select.menuSelected2);
        //CASE SPACE, MODESELECTED=TRUE, MENUSELECTED2=EASY
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.menuSelected2="EASY";
        leaderBoard_select.selectBoard(KeyCode.SPACE);
        assertEquals("EASY",LeaderBoard_menu.difficulty);
        //CASE SPACE, MODESELECTED=TRUE, MENUSELECTED2=NORMAL
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.menuSelected2="NORMAL";
        leaderBoard_select.selectBoard(KeyCode.SPACE);
        assertEquals("NORMAL",LeaderBoard_menu.difficulty);
        //CASE SPACE, MODESELECTED=TRUE, MENUSELECTED2=HARD
        LeaderBoard_Select.modeSelected=true;
        LeaderBoard_Select.menuSelected2="HARD";
        leaderBoard_select.selectBoard(KeyCode.SPACE);
        assertEquals("HARD",LeaderBoard_menu.difficulty);
        //CASE SPACE, MODESELECTED=FALSE
        LeaderBoard_Select.modeSelected=false;
        leaderBoard_select.selectBoard(KeyCode.SPACE);
        assertEquals("EASY",LeaderBoard_Select.menuSelected2);
        leaderBoard_select.selectBoard(KeyCode.BACK_SPACE);
    }
}