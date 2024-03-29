package kr.ac.seoultech;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Setting extends Application {
    public static final int DEADLINEGAP = 4;

    public static int MOVE = 25;
    public static int SIZE = 25;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    final private static Pane group = new Pane();
    public static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);
    Tetris tetris = new Tetris();
    StartMenu startMenu = new StartMenu();
    public static Boolean isCreate = false;
    private static Form settingForm;
    //옵션 기록용 file
    final private static String filePath = new File("").getAbsolutePath();
    final private static String fileName = "Setting";

    public static Text colorBlindText = new Text("Color Blind Mode :");
    public static Text keySetting = new Text("Key Setting :");
    public static Text resetScoreBoard = new Text("Reset Score Board :");
    public static Text sizeSetting = new Text("Tetris Size Setting :");
    public static Text resetConfig = new Text("Reset Config :");
    public static Text difficulty = new Text("Difficulty");

    public static Text diffcultyBool = new Text("Easy");
    public static Text colorBlindBool = new Text("FALSE");
    public static Text keySettingBool = new Text("Arrow Keys");
    public static Text sizeSettingBool = new Text("size25");
    public static Text resetScoreBoardBool =new Text("RESET");
    public static Text resetBool = new Text("RESET");


    public static ArrayList<String> savedSetting = new ArrayList<String>();
    final private static ArrayList<String> select = new ArrayList<String>(Arrays.asList(
            "config","score", "size",  "key" ,"color","difficulty"));
    final private static Integer menu_max = select.size();
    private static String menuSelected = select.get(menu_max - 1);
    private static Integer count = menu_max - 1;
    Stage window;
    private static Boolean resetConfigCheck = false;
    public static String title ="";
    public void settingSetting(){
        difficulty.setStyle("-fx-font: 20 arial");
        difficulty.setX(XMAX / 2 - 100);
        difficulty.setY(YMAX / 2 - 100);
        difficulty.setFill(Color.BLACK);

        colorBlindText.setStyle("-fx-font: 20 arial");
        colorBlindText.setX(XMAX / 2 - 100);
        colorBlindText.setY(YMAX / 2 - 50);
        colorBlindText.setFill(Color.BLACK);

        keySetting.setStyle("-fx-font: 20 arial");
        keySetting.setX(XMAX / 2 - 100);
        keySetting.setY(YMAX / 2);
        keySetting.setFill(Color.BLACK);

        sizeSetting.setStyle("-fx-font: 20 arial");
        sizeSetting.setX(XMAX / 2 - 100);
        sizeSetting.setY(YMAX / 2 + 50);
        sizeSetting.setFill(Color.BLACK);

        resetScoreBoard.setStyle("-fx-font: 20 arial");
        resetScoreBoard.setX(XMAX / 2 - 100);
        resetScoreBoard.setY(YMAX / 2 + 100);
        resetScoreBoard.setFill(Color.BLACK);

        resetConfig.setStyle("-fx-font: 20 arial");
        resetConfig.setX(XMAX / 2 - 100);
        resetConfig.setY(YMAX / 2 + 150);
        resetConfig.setFill(Color.BLACK);


        //
        diffcultyBool.setStyle("-fx-font: 20 arial");
        diffcultyBool.setX(XMAX / 2 + 150);
        diffcultyBool.setY(YMAX / 2 - 100);
        diffcultyBool.setFill(Color.RED);

        colorBlindBool.setStyle("-fx-font: 20 arial");
        colorBlindBool.setX(XMAX / 2 + 150);
        colorBlindBool.setY(YMAX / 2 - 50);
        colorBlindBool.setFill(Color.BLACK);

        keySettingBool.setStyle("-fx-font: 20 arial");
        keySettingBool.setX(XMAX / 2 + 150);
        keySettingBool.setY(YMAX / 2);
        keySettingBool.setFill(Color.BLACK);

        sizeSettingBool.setStyle("-fx-font: 20 arial");
        sizeSettingBool.setX(XMAX / 2 + 150);
        sizeSettingBool.setY(YMAX / 2 + 50);
        sizeSettingBool.setFill(Color.BLACK);

        resetScoreBoardBool.setStyle("-fx-font: 20 arial");
        resetScoreBoardBool.setX(XMAX / 2 + 150);
        resetScoreBoardBool.setY(YMAX / 2 + 100);
        resetScoreBoardBool.setFill(Color.BLACK);

        resetBool.setStyle("-fx-font: 20 arial");
        resetBool.setX(XMAX / 2 + 150);
        resetBool.setY(YMAX / 2 + 150);
        resetBool.setFill(Color.BLACK);

        group.getChildren().addAll(
                difficulty,colorBlindText, keySetting, resetConfig, resetScoreBoard, sizeSetting,
                diffcultyBool,colorBlindBool,keySettingBool,sizeSettingBool,resetBool,resetScoreBoardBool
        );
    }
    @Override
    public void start(Stage settingStage){
        System.out.println(menuSelected);
        window = settingStage;
        settingSetting();
        settingPress(settingForm);
        settingStage.setScene(scene);
        settingStage.setTitle("T E T R I S");
        title = settingStage.getTitle();
        settingStage.setResizable(false);
        settingStage.show();
    }
    public static void loadSetting(){
        try{
            File f = new File(filePath,fileName);
            if(!f.isFile()){
                createSettingFile();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String[] settings = br.readLine().split("-");
            for(int i = 0; i < settings.length ; i++){
                StartMenu.settings.add(settings[i]);
            }
            Setting.diffcultyBool.setText(StartMenu.settings.get(0));
            Setting.colorBlindBool.setText(StartMenu.settings.get(1));
            Setting.keySettingBool.setText(StartMenu.settings.get(2));
            Setting.sizeSettingBool.setText(StartMenu.settings.get(3));
            for(int i = 0; i < 4; i++){
                savedSetting.add(StartMenu.settings.get(i));
            }
            switch (StartMenu.settings.get(3)){
                case "size25":
                    Tetris.SIZE = 25;
                    Tetris.MOVE = 25;
                    break;
                case "size30":
                    Tetris.SIZE = 30;
                    Tetris.MOVE = 30;
                    break;
                case "size35":
                    Tetris.SIZE = 35;
                    Tetris.MOVE = 35;
                    break;
            }
            switch (StartMenu.settings.get(0)){
                case "Easy":
                    Tetris.level = Tetris.Difficulty.Easy;
                    break;
                case "Normal":
                    Tetris.level = Tetris.Difficulty.Normal;
                    break;
                case "Hard":
                    Tetris.level = Tetris.Difficulty.Hard;
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void createSettingFile(){
        FileWriter output = null;
        try{
            File f = new File(filePath,fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(
                    "Easy" + "-"+ //diffuculty
                            "FALSE" + "-"+ //color blind mode
                            "Arrow Keys" + "-"+ //key setting
                            "size25" //tetris size setting
            );
            writer.close();
            isCreate = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveSettings(ArrayList savedSetting){
        FileWriter output = null;
        try{
            File f = new File(filePath,fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(
                    savedSetting.get(0) +"-"+
                            savedSetting.get(1) +"-"+
                            savedSetting.get(2) +"-"+
                            savedSetting.get(3)
            );
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Boolean colorResetVal = false;

    public void colorReset(){
        diffcultyBool.setFill(Color.BLACK);
        colorBlindBool.setFill(Color.BLACK);
        keySettingBool.setFill(Color.BLACK);
        sizeSettingBool.setFill(Color.BLACK);
        resetScoreBoardBool.setFill(Color.BLACK);
        resetBool.setFill(Color.BLACK);
        colorResetVal = true;
    }

    public void menuColoring(int val){
        switch (val){
            case 5:
                colorReset();
                diffcultyBool.setFill(Color.RED);
                break;
            case 4:
                colorReset();
                colorBlindBool.setFill(Color.RED);
                break;
            case 3:
                colorReset();
                keySettingBool.setFill(Color.RED);
                break;
            case 2:
                colorReset();
                sizeSettingBool.setFill(Color.RED);
                break;
            case 1:
                colorReset();
                resetScoreBoardBool.setFill(Color.RED);
                break;
            case 0:
                colorReset();
                resetBool.setFill(Color.RED);
                break;
        }
    }
    private void settingPress(Form form) {
        scene.setOnKeyPressed((new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        //
                        if(count >= 0 && count < menu_max -1){
                            ++count;
                            menuSelected = select.get(count);
                            System.out.println(menuSelected);
                        }else{
                            count = 0;
                            menuSelected = select.get(count);
                            System.out.println(menuSelected);
                        }
                        menuColoring(count);
                        break;

                    case DOWN:
                        if(count >= 1 && count < menu_max){
                            --count;
                            menuSelected = select.get(count);
                            System.out.println(menuSelected);
                        }else{
                            count = menu_max - 1;
                            menuSelected = select.get(count);
                            System.out.println(menuSelected);
                        }
                        menuColoring(count);
                        break;

                    case SPACE:
                        switch (menuSelected){
                            case "difficulty":
                                if(diffcultyBool.getText().equals("Hard")){
                                    //쉬움
                                    Tetris.level = Tetris.Difficulty.Easy;
                                    diffcultyBool.setText("Easy");
                                    savedSetting.set(0,"Easy");
                                }else if(diffcultyBool.getText().equals("Easy")){
                                    //중간
                                    Tetris.level = Tetris.Difficulty.Normal;
                                    diffcultyBool.setText("Normal");
                                    savedSetting.set(0,"Normal");
                                }else{
                                    //어려움
                                    Tetris.level = Tetris.Difficulty.Hard;
                                    diffcultyBool.setText("Hard");
                                    savedSetting.set(0,"Hard");
                                }
                                break;
                            case "color":
                                if(colorBlindBool.getText().equals("FALSE")){
                                    colorBlindBool.setText("TRUE");
                                    Form.colorBlindMode = true;
                                    savedSetting.set(1,"TRUE");
                                }else{
                                    colorBlindBool.setText("FALSE");
                                    Form.colorBlindMode = false;
                                    savedSetting.set(1,"FALSE");
                                }

                                break;
                            case "key":
                                if(keySettingBool.getText().equals("WASD")){
                                    keySettingBool.setText("Arrow Keys");
                                    savedSetting.set(2,"Arrow Keys");
                                }else{
                                    keySettingBool.setText("WASD");
                                    savedSetting.set(2,"WASD");
                                }
                                break;
                            case "size":
                                if(sizeSettingBool.getText().equals("size35")){
                                    Tetris.SIZE = 25;
                                    Tetris.MOVE = 25;
                                    sizeSettingBool.setText("size25");
                                    savedSetting.set(3,"size25");
                                }else if(sizeSettingBool.getText().equals("size25")){
                                    Tetris.SIZE = 30;
                                    Tetris.MOVE = 30;
                                    sizeSettingBool.setText("size30");
                                    savedSetting.set(3,"size30");
                                }else{
                                    Tetris.SIZE = 35;
                                    Tetris.MOVE = 35;
                                    sizeSettingBool.setText("size35");
                                    savedSetting.set(3,"size35");
                                }
                                break;
                            case "score":
                                Leaderboard.createSaveData(Leaderboard.fileName);
                                if(StartMenu.isLeaderboardOn){
                                    for(int i = 0 ; i < 10 ; i++){
                                        LeaderBoard_menu.Ranking_score[i].setText("0");
                                        LeaderBoard_menu.Ranking_user[i].setText("___");
                                        LeaderBoard_menu.Ranking_score[i].setFill(Color.BLACK);
                                        LeaderBoard_menu.Ranking_user[i].setFill(Color.BLACK);
                                    }
                                }
                                System.out.println("ScoreBoardReset");

                                break;
                            case "config":
                                resetConfig();
                                resetConfigCheck = true;
                                System.out.println("Reset Configuration");
                                break;
                        }
                        break;

                    case BACK_SPACE:
                        try{
                            if(resetConfigCheck){
                                resetConfigCheck = false;
                            }else{
                                saveSettings(savedSetting);
                            }
                            window.setScene(StartMenu.scene);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }));
    }
    public void resetConfig(){
        diffcultyBool.setText("Easy");
        colorBlindBool.setText("FALSE");
        keySettingBool.setText("Arrow Keys");
        sizeSettingBool.setText("size25");
        createSettingFile();
        Tetris.level = Tetris.Difficulty.Easy;
        Form.colorBlindMode = false;
        StartMenu.settings.set(3,"Arrow Keys");
        Tetris.SIZE = 25;
        Tetris.MOVE = 25;
    }
}