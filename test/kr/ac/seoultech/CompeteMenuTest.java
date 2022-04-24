package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
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
    }
    @DisplayName("색상 초기화")
    @Test
    void colorReset() {
        competeMenu.colorReset();
        assertEquals("0x000000ff", CompeteMenu.standardMode.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.itemMode.getFill().toString());
        assertEquals("0x000000ff", CompeteMenu.timeoutMode.getFill().toString());
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
}