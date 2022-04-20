package kr.ac.seoultech;

import com.sun.javafx.scene.control.skin.ColorPalette;
import javafx.embed.swing.JFXPanel;
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
    void boardSelectPress()
    {
        leaderBoard_select.boardSelectPress(leaderBoard_select.boardSelectForm);
    }
}