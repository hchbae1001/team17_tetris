package kr.ac.seoultech;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompeteMenuTest {
    private JFXPanel panel = new JFXPanel();
    CompeteMenu competeMenu = new CompeteMenu(new Tetris());
    @DisplayName("메뉴 초기 세팅")
    @Test
    void competeMenuSetting() {
        competeMenu.competeMenuSetting();
        assertEquals("0xff0000ff", CompeteMenu.standardMode.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.itemMode.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.timeoutMode.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.gameTitle.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.help.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.oneMin.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.twoMin.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.threeMin.getFill().toString());
    }
    @DisplayName("색상 초기화")
    @Test
    void colorReset() {
        competeMenu.colorReset();
        if(competeMenu.timeOutSelected = false){
            assertEquals("0x000000ff", CompeteMenu.standardMode.getFill().toString());
            assertEquals("0x000000ff", CompeteMenu.itemMode.getFill().toString());
            assertEquals("0x000000ff", CompeteMenu.timeoutMode.getFill().toString());
        }
        assertEquals("0x000000ff", CompeteMenu.oneMin.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.twoMin.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.threeMin.getFill().toString());


    }
    @DisplayName("선택된 메뉴 빨강색 표시")
    @Test
    void menuColoring() {

     competeMenu.menuColoring(0);
        assertEquals("0xff0000ff",CompeteMenu.timeoutMode.getFill().toString());
     competeMenu.menuColoring(1);
        assertEquals("0xff0000ff",CompeteMenu.itemMode.getFill().toString());
     competeMenu.menuColoring(2);
        assertEquals("0xff0000ff",CompeteMenu.standardMode.getFill().toString());
    }
    @Test
    void competeSelect(){
        // up
        competeMenu.timeOutSelected = false;
        competeMenu.count = 0;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("ITEM", competeMenu.menuSelected);

        competeMenu.timeOutSelected = false;
        competeMenu.count = 1;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("STANDARD", competeMenu.menuSelected);

        competeMenu.timeOutSelected = false;
        competeMenu.count = 2;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("TIMEOUT", competeMenu.menuSelected);

        //down
        competeMenu.timeOutSelected = false;
        competeMenu.count = 2;
        competeMenu.competeSelect(KeyCode.DOWN);
        assertEquals("ITEM", competeMenu.menuSelected);

        competeMenu.timeOutSelected = false;
        competeMenu.count = 1;
        competeMenu.competeSelect(KeyCode.DOWN);
        assertEquals("TIMEOUT", competeMenu.menuSelected);

        competeMenu.timeOutSelected = false;
        competeMenu.count = 0;
        competeMenu.competeSelect(KeyCode.DOWN);
        assertEquals("STANDARD", competeMenu.menuSelected);

        // UP
        competeMenu.timeOutSelected = true;
        competeMenu.count2 = 0;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("2min", competeMenu.timeSelected);

        competeMenu.timeOutSelected = true;
        competeMenu.count2 = 1;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("1min", competeMenu.timeSelected);

        competeMenu.timeOutSelected = true;
        competeMenu.count2 = 2;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("3min", competeMenu.timeSelected);

        // DOWN
        competeMenu.timeOutSelected = true;
        competeMenu.count2 = 0;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("2min", competeMenu.timeSelected);

        competeMenu.timeOutSelected = true;
        competeMenu.count2 = 1;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("1min", competeMenu.timeSelected);

        competeMenu.timeOutSelected = true;
        competeMenu.count2 = 2;
        competeMenu.competeSelect(KeyCode.UP);
        assertEquals("3min", competeMenu.timeSelected);

        competeMenu.menuSelected = "TIMEOUT";
        competeMenu.timeOutSelected = false;
        competeMenu.competeSelect(KeyCode.SPACE);
        assertEquals(true, competeMenu.timeOutSelected);
        assertEquals(2, competeMenu.count2);


    }
}