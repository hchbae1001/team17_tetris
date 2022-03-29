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
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Setting extends Application {
    public static final int DEADLINEGAP = 4;
    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];

    final private static Pane group = new Pane();
    public static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);

    private static Form settingForm;
    //옵션 기록용 file
    final private static String filepath = new File("").getAbsolutePath();
    final private static String fileName = "Option";

    //색약모드 controll

    //TODO - return to default setting
    //TODO - ScoreBoard 기록 초기화
    //TODO - Key Setting

    public static Text colorBlindText = new Text("Color Blind Mode :");
    public static Text keySetting = new Text("Key Setting :");
    public static Text resetScoreBoard = new Text("Reset Score Board :");
    public static Text sizeSetting = new Text("Size Setting :");
    public static Text resetConfig = new Text("Reset Config :");


    public static Text colorBlindBool = new Text("FALSE");
    public static Text keySettingBool = new Text("WASD");
    public static Text sizeSettingBool = new Text("size1");
    public static Text resetScoreBoardBool =new Text("RESET");
    public static Text resetBool = new Text("RESET");

    private static String menuSelected = "";

    final private static ArrayList<String> select = new ArrayList<String>(Arrays.asList(
            "config","score", "size",  "key" ,"color"));
    final private static Integer menu_max = select.size();
    private static Integer count = menu_max - 1;
    Stage window;


    private void settingMenuSetting(){
        colorBlindText.setStyle("-fx-font: 20 arial");
        colorBlindText.setX(XMAX / 2 - 100);
        colorBlindText.setY(YMAX / 2);
        colorBlindText.setFill(Color.BLACK);

        keySetting.setStyle("-fx-font: 20 arial");
        keySetting.setX(XMAX / 2 - 100);
        keySetting.setY(YMAX / 2 + 50);
        keySetting.setFill(Color.BLACK);

        sizeSetting.setStyle("-fx-font: 20 arial");
        sizeSetting.setX(XMAX / 2 - 100);
        sizeSetting.setY(YMAX / 2 + 100);
        sizeSetting.setFill(Color.BLACK);

        resetScoreBoard.setStyle("-fx-font: 20 arial");
        resetScoreBoard.setX(XMAX / 2 - 100);
        resetScoreBoard.setY(YMAX / 2 + 150);
        resetScoreBoard.setFill(Color.BLACK);

        resetConfig.setStyle("-fx-font: 20 arial");
        resetConfig.setX(XMAX / 2 - 100);
        resetConfig.setY(YMAX / 2 + 200);
        resetConfig.setFill(Color.BLACK);
        //
        colorBlindBool.setStyle("-fx-font: 20 arial");
        colorBlindBool.setX(XMAX / 2 + 150);
        colorBlindBool.setY(YMAX / 2);
        colorBlindBool.setFill(Color.BLACK);

        keySettingBool.setStyle("-fx-font: 20 arial");
        keySettingBool.setX(XMAX / 2 + 150);
        keySettingBool.setY(YMAX / 2 + 50);
        keySettingBool.setFill(Color.BLACK);

        sizeSettingBool.setStyle("-fx-font: 20 arial");
        sizeSettingBool.setX(XMAX / 2 + 150);
        sizeSettingBool.setY(YMAX / 2 + 100);
        sizeSettingBool.setFill(Color.BLACK);

        resetScoreBoardBool.setStyle("-fx-font: 20 arial");
        resetScoreBoardBool.setX(XMAX / 2 + 150);
        resetScoreBoardBool.setY(YMAX / 2 + 150);
        resetScoreBoardBool.setFill(Color.BLACK);

        resetBool.setStyle("-fx-font: 20 arial");
        resetBool.setX(XMAX / 2 + 150);
        resetBool.setY(YMAX / 2 + 200);
        resetBool.setFill(Color.BLACK);

        group.getChildren().addAll(
          colorBlindText, keySetting, resetConfig, resetScoreBoard, sizeSetting,
                colorBlindBool,keySettingBool,sizeSettingBool,resetBool,resetScoreBoardBool
        );
    }
    @Override
    public void start(Stage settingStage){
        window = settingStage;
        settingMenuSetting();
        settingPress(settingForm);

        settingStage.setScene(scene);
        settingStage.setTitle("T E T R I S");
        settingStage.show();

    }

    private void colorReset(){
        colorBlindBool.setFill(Color.BLACK);
        keySettingBool.setFill(Color.BLACK);
        sizeSettingBool.setFill(Color.BLACK);
        resetScoreBoardBool.setFill(Color.BLACK);
        resetBool.setFill(Color.BLACK);
    }
    private void menuColoring(){
        switch (count){
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
                        menuColoring();
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
                        menuColoring();
                        break;

                    case SPACE:
                        System.out.println("space");
                            switch (menuSelected){
                                case "color":

                                    if(colorBlindBool.getText().equals("FALSE")){
                                        colorBlindBool.setText("TRUE");
                                        Form.colorBlindMode = true;
                                    }else{
                                        colorBlindBool.setText("FALSE");
                                        Form.colorBlindMode = false;
                                    }

                                    break;
                                case "key":
                                    if(keySettingBool.getText().equals("WASD")){
                                        keySettingBool.setText("방향키");
                                    }else{
                                        keySettingBool.setText("WASD");
                                    }
                                    break;
                                case "size":
                                    if(sizeSettingBool.getText().equals("size1")){
                                        sizeSettingBool.setText("size2");
                                    }else if(sizeSettingBool.getText().equals("size2")){
                                        sizeSettingBool.setText("size3");
                                    }else{
                                        sizeSettingBool.setText("size1");
                                    }
                                    break;
                                case "score":
                                    System.out.println("ScoreBoardReset");
                                    break;
                                case "config":
                                    System.out.println("Reset Configuration");
                                    break;
                            }
                        break;

                    case BACK_SPACE:
                        try{
                            window.setScene(StartMenu.scene);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }));
    }
}
