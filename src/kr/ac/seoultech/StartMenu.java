package kr.ac.seoultech;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kr.ac.seoultech.*;

public class StartMenu extends Application {

    public static final int DEADLINEGAP = 4;
    public static final int SIZE = Setting.SIZE;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);

    final private static Pane group = new Pane();
    public static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);
    private static Form menu;
    //페이지 내 표시할 것들
    final private static Text menu3_start = new Text("START");
    final private static Text menu2_setting = new Text("Setting");
    final private static Text menu1_scoreBoard = new Text("Score Board");
    final private static Text menu0_Exit = new Text("Exit");
    private static boolean isSettingOn = false;
    //count = 3 -> start 에 커서
    private static Integer count = 3;
    private static String menuSelected = "";

    final private static ArrayList<String> select = new ArrayList<String>(Arrays.asList(
            "exit", "scoreBoard", "setting",  "start"));
    final private static Integer menu_max = select.size();

    public static Stage window;

    private void startMenuSetting(){
        menu3_start.setStyle("-fx-font: 20 arial");
        menu3_start.setX(XMAX / 2);
        menu3_start.setY(YMAX / 2 + 50);
        menu3_start.setFill(Color.RED);

        menu2_setting.setStyle("-fx-font: 20 arial");
        menu2_setting.setX(XMAX / 2);
        menu2_setting.setY(YMAX / 2 + 100);
        menu2_setting.setFill(Color.BLACK);

        menu1_scoreBoard.setStyle("-fx-font: 20 arial");
        menu1_scoreBoard.setX(XMAX / 2);
        menu1_scoreBoard.setY(YMAX / 2 + 150);
        menu1_scoreBoard.setFill(Color.BLACK);

        menu0_Exit.setStyle("-fx-font: 20 arial");
        menu0_Exit.setX(XMAX / 2);
        menu0_Exit.setY(YMAX / 2 + 200);
        menu0_Exit.setFill(Color.BLACK);

        group.getChildren().addAll(
                menu3_start,menu2_setting,menu1_scoreBoard,menu0_Exit
        );

        //초기 시작 시, Start 에 커서
        menuSelected = select.get(count);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        startMenuSetting();
        menuPress(menu);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("T E T R I S");
        primaryStage.show();
    }

    private void colorReset(){
        menu3_start.setFill(Color.BLACK);
        menu2_setting.setFill(Color.BLACK);
        menu1_scoreBoard.setFill(Color.BLACK);
        menu0_Exit.setFill(Color.BLACK);
    }

    private void menuColoring(){
        switch (count){
            case 3:
                colorReset();
                menu3_start.setFill(Color.RED);
                break;
            case 2:
                colorReset();
                menu2_setting.setFill(Color.RED);
                break;
            case 1:
                colorReset();
                menu1_scoreBoard.setFill(Color.RED);
                break;
            case 0:
                colorReset();
                menu0_Exit.setFill(Color.RED);
                break;
        }
    }
    private void menuPress(Form form){
        Setting settingMenu = new Setting();
        scene.setOnKeyPressed((new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        //
                        if(count >= 0 && count < menu_max -1){
                            ++count;
                            menuSelected = select.get(count);
                            System.out.println(count);
                        }else{
                            count = 0;
                            menuSelected = select.get(count);
                            System.out.println(count);
                        }
                        menuColoring();
                        break;

                    case DOWN:
                        if(count >= 1 && count < menu_max){
                            --count;
                            menuSelected = select.get(count);
                            System.out.println(count);
                        }else{
                            count = menu_max - 1;
                            menuSelected = select.get(count);
                            System.out.println(count);
                        }
                        menuColoring();
                        break;

                    case SPACE:
                        switch (menuSelected){
                            case "start":
                                Tetris tetris = new Tetris();
                                try{
                                    tetris.start(window);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                break;
                            case "setting":
                                if(!isSettingOn){
                                    try{
                                        settingMenu.start(window);
                                        isSettingOn = true;
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }else{
                                    try{
                                        window.setScene(Setting.scene);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                                System.out.println("setting");
                                break;
                            case "scoreBoard":
                                //Todo scoreboard 연동
                                System.out.println("scoreBoard");
                                break;
                            case "exit":
                                System.out.println("exit");
                                window.close();
                                break;
                        }
                        break;
                }
            }
        }));
    }
}