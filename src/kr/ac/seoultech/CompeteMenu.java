package kr.ac.seoultech;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class CompeteMenu extends Application {

    public static final int DEADLINEGAP = 4;
    public static final int SIZE = Setting.SIZE;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    final private static Pane group = new Pane();
    public static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);
    public static Form menu;
    //페이지 내 표시할 것들
    final public static Text gameTitle = new Text("Team17 TETRIS");
    final public static Text help = new Text("Space = Select Menu");
    final public static Text standardMode = new Text("STANDARD");
    final public static Text itemMode = new Text("ITEM");
    final public static Text timeoutMode = new Text("TIMEOUT");
    public static String menuSelected = "";
    public static ArrayList<String> select = new ArrayList<String>(Arrays.asList("TIMEOUT","ITEM","STANDARD"
            ));
    public static Integer menu_max = select.size();
    public static Integer count = select.size() - 1;
    public static Stage window;
    private Tetris tetris;
    final private static ArrayList<String> timeSelect = new ArrayList<String>(Arrays.asList(
       "3min", "2min","1min"
    ));
    public static Boolean timeOutSelected = false;
    public final static Integer time_Arr_max = timeSelect.size();
    public static String timeSelected;
    public static Integer count2 = null;
    public static Text oneMin = new Text("1Min");
    public static Text twoMin = new Text("2Min");
    public static Text threeMin = new Text("3Min");
    public CompeteMenu(Tetris _tetris){
        tetris = _tetris;
    }

    public Boolean tm = false;
    public Boolean cp = false;
    public void competeMenuSetting(){

        gameTitle.setStyle("-fx-font-size: 20px");
        gameTitle.setFont(Font.font(null, FontWeight.BOLD,20));
        gameTitle.setX(XMAX / 2);
        gameTitle.setY(YMAX / 2 - 150);
        gameTitle.setFill(Color.BLACK);

        help.setStyle("-fx-font: 20 arial");
        help.setX(XMAX / 2 - 10);
        help.setY(YMAX / 2 - 100);
        help.setFill(Color.BLACK);

        standardMode.setStyle("-fx-font-size: 20px");
        standardMode.setX(XMAX / 2);
        standardMode.setY(YMAX / 2);
        standardMode.setFill(Color.RED);

        itemMode.setStyle("-fx-font-size: 20px");
        itemMode.setX(XMAX / 2);
        itemMode.setY(YMAX / 2 + 50);
        itemMode.setFill(Color.BLACK);

        timeoutMode.setStyle("-fx-font-size: 20px");
        timeoutMode.setX(XMAX / 2);
        timeoutMode.setY(YMAX / 2 + 100);
        timeoutMode.setFill(Color.BLACK);

        oneMin.setStyle("-fx-font: 20 arial");
        oneMin.setX(XMAX / 2 + 130);
        oneMin.setY(YMAX / 2 + 110);
        oneMin.setFill(Color.BLACK);

        twoMin.setStyle("-fx-font: 20 arial");
        twoMin.setX(XMAX / 2 + 130);
        twoMin.setY(YMAX / 2 + 160);
        twoMin.setFill(Color.BLACK);

        threeMin.setStyle("-fx-font: 20 arial");
        threeMin.setX(XMAX / 2 + 130);
        threeMin.setY(YMAX / 2 + 210);
        threeMin.setFill(Color.BLACK);

        group.getChildren().addAll(
          standardMode,itemMode,timeoutMode,gameTitle,help, oneMin,twoMin,threeMin
        );
        menuSelected = select.get(count);
        System.out.println(menuSelected);
    }
    @Override
    public void start(Stage competeMenuStage) throws Exception {
        competeMenuSetting();
        window = competeMenuStage;
        menuPress(menu);
        competeMenuStage.setResizable(false);
        competeMenuStage.setScene(scene);
        competeMenuStage.setTitle("T E T R I S");
        competeMenuStage.show();
    }
    public void colorReset(){
        if(!timeOutSelected){
            standardMode.setFill(Color.BLACK);
            itemMode.setFill(Color.BLACK);
            timeoutMode.setFill(Color.BLACK);
        }
        oneMin.setFill(Color.BLACK);
        twoMin.setFill(Color.BLACK);
        threeMin.setFill(Color.BLACK);
    }
    public void menuColoring(Integer val){
        colorReset();
        if(!timeOutSelected){
            switch (val){
                case 2:
                    standardMode.setFill(Color.RED);
                    break;
                case 1:
                    itemMode.setFill(Color.RED);
                    break;
                case 0:
                    timeoutMode.setFill(Color.RED);
                    break;
            }
        }else{
            //시간제한모드 선택시
            standardMode.setFill(Color.BLACK);
            itemMode.setFill(Color.BLACK);
            timeoutMode.setFill(Color.BLUE);
            switch (count2){
                case 2:
                    oneMin.setFill(Color.RED);
                    break;
                case 1:
                    twoMin.setFill(Color.RED);
                    break;
                case 0:
                    threeMin.setFill(Color.RED);
                    break;

            }
        }

    }
    public void competeSelect(KeyCode k){
        switch (k){
            case UP:
                System.out.println(timeOutSelected);
                if(!timeOutSelected){
                    if(count >= 0 && count < menu_max -1){
                        ++count;
                        menuSelected = select.get(count);
                        System.out.println(menuSelected);
                    }else{
                        count = 0;
                        menuSelected = select.get(count);
                        System.out.println(menuSelected);
                    }
                }else{
                    if(count2 >= 0 && count2 < time_Arr_max -1){
                        ++count2;
                        timeSelected = timeSelect.get(count2);
                        System.out.println(timeSelected);
                    }else{
                        count2 = 0;
                        timeSelected = timeSelect.get(count2);
                        System.out.println(timeSelected);
                    }
                }
                menuColoring(count);
                break;

            case DOWN:
                System.out.println(timeOutSelected);
                if(!timeOutSelected){
                    if(count >= 1 && count < menu_max){
                        --count;
                        menuSelected = select.get(count);
                        System.out.println(menuSelected);
                    }else{
                        count = menu_max - 1;
                        menuSelected = select.get(count);
                        System.out.println(menuSelected);
                    }
                }else{
                    if(count2 >= 1 && count2 < time_Arr_max){
                        --count2;
                        timeSelected = timeSelect.get(count2);
                        System.out.println(timeSelected);
                    }else{
                        count2 = time_Arr_max - 1;
                        timeSelected = timeSelect.get(count2);
                        System.out.println(timeSelected);
                    }
                }
                menuColoring(count);
                break;

            case BACK_SPACE:
                if(timeOutSelected){
                    timeOutSelected = false;
                    count2 = null;
                    menuColoring(count);
                }else{
                    try{
                        window.setScene(StartMenu.scene);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;

            case SPACE:
                switch (menuSelected){
                    case "STANDARD":
                        tm = false;
                        Tetris.tm = false;
                        System.out.println(menuSelected);
                        //TODO 대전모드 ItemMode boolean false;
                        if(!StartMenu.isGameOn){
                            try{
                                StartMenu.isGameOn = true;
                                Tetris.itemModeBool = false;
                                Tetris.cp = true;
                                tetris.createTetrisThread();
                                tetris.createInputThread();
                                tetris.start(window);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            Tetris.itemModeBool = false;
                            Tetris.cp = true;
                            cp = true;
                            if(tetris.player2 == null){
                                tetris.createTetrisThread();
                                tetris.player2.setPid(2);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run(){
                                        tetris.player2.deleteOldGame();
                                        tetris.player2.continueGame("Restart");
                                    }
                                });
                            }else {
                                tetris.player2.continueGame("Restart");
                                tetris.player2.window.show();
                            }
                            tetris.continueGame("Restart");
                            window.setScene(tetris.scene);
                        }
                        break;

                    case "ITEM":
                        Tetris.tm = false;
                        System.out.println(menuSelected);
                        if(!StartMenu.isGameOn){
                            try{
                                StartMenu.isGameOn = true;
                                Tetris.itemModeBool = true;
                                Tetris.cp = true;
                                tetris.createTetrisThread();
                                tetris.createInputThread();
                                tetris.start(window);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            Tetris.itemModeBool = true;
                            Tetris.cp = true;
                            if(tetris.player2 == null){
                                tetris.createTetrisThread();
                                tetris.player2.setPid(2);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run(){
                                        tetris.player2.deleteOldGame();
                                        tetris.player2.continueGame("Restart");
                                    }
                                });
                            }else {
                                tetris.player2.continueGame("Restart");
                                tetris.player2.window.show();
                            }
                            tetris.continueGame("Restart");
                            window.setScene(tetris.scene);
                        }
                        break;

                    case "TIMEOUT":
                        if(timeOutSelected){
                            switch (timeSelected){
                                case "1min":
                                    Tetris.cpTime = 60000;
                                    break;
                                case "2min":
                                    Tetris.cpTime = 120000;
                                    break;
                                case "3min":
                                    Tetris.cpTime = 180000;
                                    break;
                            }
                            System.out.println(menuSelected);
                            if(!StartMenu.isGameOn){
                                Tetris.itemModeBool = false;
                                Tetris.cp = true;
                                Tetris.tm = true;
                                try{
                                    StartMenu.isGameOn = true;
                                    tetris.createTetrisThread();
                                    tetris.createInputThread();
                                    tetris.start(window);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else {
                                Tetris.itemModeBool = false;
                                Tetris.cp = true;
                                Tetris.tm = true;
                                if(tetris.player2 == null){
                                    tetris.createTetrisThread();
                                    tetris.player2.setPid(2);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run(){
                                            tetris.player2.deleteOldGame();
                                            tetris.player2.continueGame("Restart");
                                        }
                                    });
                                }else {
                                    tetris.player2.continueGame("Restart");
                                    tetris.player2.window.show();
                                }
                                tetris.continueGame("Restart");
                                window.setScene(tetris.scene);
                            }
                        }else{
                            timeOutSelected = true;
                            System.out.println(timeOutSelected);
                            count2 = time_Arr_max -1;
                            timeSelected = timeSelect.get(count2);
                            System.out.println(timeSelected);
                            menuColoring(count);
                        }


                        break;
                }
                break;

        }
    }
    public void menuPress(Form form){
        scene.setOnKeyPressed((new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                competeSelect(event.getCode());
            }
        }));
    }
}
