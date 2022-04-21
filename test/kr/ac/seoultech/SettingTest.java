package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SettingTest {
    static private JFXPanel panel;
    Setting setting = new Setting();
    String filepath = new File("").getAbsolutePath();

    @BeforeAll
    static void testSet(){
        try{
            panel = new JFXPanel();
            Setting setting = new Setting();
            Setting.isCreate = false;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @DisplayName("Setting 저장")
    @Test
    public void saveSettingsTest(){
        ArrayList<String> settingTemp = new ArrayList<String>();
        settingTemp.add("Normal");
        settingTemp.add("TRUE");
        settingTemp.add("WASD");
        settingTemp.add("size35");
        Setting.saveSettings(settingTemp);
        try{
            File f = new File(filepath,"Setting");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String[] settings = br.readLine().split("-");
            String[] array = new String[settingTemp.size()];
            int size = 0;
            for(String temp : settingTemp){
                array[size++] = temp;
            }
            assertArrayEquals(settings,array);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @DisplayName("메뉴 색상 Refresh")
    @Test
    public void colorResetTest(){
        setting.colorReset();
        assertEquals("0x000000ff", Setting.diffcultyBool.getFill().toString());
        assertEquals("0x000000ff", Setting.colorBlindBool.getFill().toString());
        assertEquals("0x000000ff", Setting.keySettingBool.getFill().toString());
        assertEquals("0x000000ff", Setting.sizeSettingBool.getFill().toString());
        assertEquals("0x000000ff", Setting.resetScoreBoardBool.getFill().toString());
        assertEquals("0x000000ff", Setting.resetBool.getFill().toString());
        assertEquals(true, Setting.colorResetVal);
    }
    @DisplayName("메뉴 색상입히기")
    @Test
    public void menuColoringTest(){
        setting.menuColoring(5);
        assertEquals("0xff0000ff",Setting.diffcultyBool.getFill().toString());
        setting.menuColoring(4);
        assertEquals("0xff0000ff",Setting.colorBlindBool.getFill().toString());
        setting.menuColoring(3);
        assertEquals("0xff0000ff",Setting.keySettingBool.getFill().toString());
        setting.menuColoring(2);
        assertEquals("0xff0000ff",Setting.sizeSettingBool.getFill().toString());
        setting.menuColoring(1);
        assertEquals("0xff0000ff",Setting.resetScoreBoardBool.getFill().toString());
        setting.menuColoring(0);
        assertEquals("0xff0000ff",Setting.resetBool.getFill().toString());
    }

    @DisplayName("Setting 리셋")
    @Test
    void resetConfigTest() {
        setting.resetConfig();
        assertEquals("Easy", Setting.diffcultyBool.getText().toString());
        assertEquals("FALSE", Setting.colorBlindBool.getText().toString());
        assertEquals("Arrow Keys", Setting.keySettingBool.getText().toString());
        assertEquals("size25", Setting.sizeSettingBool.getText().toString());
        assertEquals("Arrow Keys", StartMenu.settings.get(3).toString());
        assertEquals(25,Tetris.SIZE);
        assertEquals(25,Tetris.MOVE);
        assertEquals(false, Form.colorBlindMode);
        assertEquals("Easy", Tetris.level.toString());
    }

    @Test
    public void settingSettingTest(){
        setting.settingSetting();
        String diff = Setting.difficulty.getText();
        assertEquals("Difficulty",diff);
        assertEquals("-fx-font: 20 arial", Setting.difficulty.getStyle());
        assertEquals("-fx-font: 20 arial", Setting.colorBlindText.getStyle());
        assertEquals("-fx-font: 20 arial", Setting.keySetting.getStyle());
        assertEquals("-fx-font: 20 arial", Setting.sizeSetting.getStyle());
        assertEquals("-fx-font: 20 arial", Setting.resetScoreBoard.getStyle());
        assertEquals("-fx-font: 20 arial", Setting.resetConfig.getStyle());

    }
    @DisplayName("Setting 불러오기")
    @Test
    public void loadSettingTest() {
        Setting.loadSetting();
        try{
            File f = new File(filepath,"Setting");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String[] settings = br.readLine().split("-");
            //StartMenu의 settings ArrayList를 Array로 변환
            String[] array = new String[StartMenu.settings.size()];
            int size = 0;
            for(String temp : StartMenu.settings){
                array[size++] = temp;
            }
            assertArrayEquals(settings,array);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @DisplayName("Setting 초기파일 만들기")
    @Test
    public void createSettingFileTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = setting.getClass().getDeclaredMethod("createSettingFile");
        method.setAccessible(true);
        try{
            method.invoke(setting);
            assertEquals(Setting.isCreate,true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
