package kr.ac.seoultech;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class LeaderBoard_Select extends Application {
    public static final int DEADLINEGAP = 4;

    public static int MOVE = 25;
    public static int SIZE = 25;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    final private static Pane group = new Pane();
    public static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);
    Tetris tetris = new Tetris();
    StartMenu startMenu = new StartMenu();

    public static Form boardSelectForm;

    public static Text modeSelect = new Text("Mode");
    public static Text standardMode = new Text("STANDARD");
    public static Text itemMode = new Text("ITEM");

    public static Text difficulty = new Text("Difficulty");
    public static Text easeMode = new Text("EASY");
    public static Text normalMode = new Text("NORMAL");
    public static Text hardMode = new Text("HARD");

    //0-item 1-standard
    final private static ArrayList<String> selectOne = new ArrayList<String>(Arrays.asList(
            "ITEM","STANDARD"));
    final private static Integer menu1_max = selectOne.size();
    public static String menuSelected1 = selectOne.get(menu1_max - 1);
    public static Integer count1 = menu1_max - 1;

    //0-hard 1-normal 2-easy
    final private static ArrayList<String> selectTwo = new ArrayList<String>(Arrays.asList(
            "HARD", "NORMAL", "EASY"));
    final private static Integer menu2_max = selectTwo.size();
    public static String menuSelected2;
    public static Integer count2 = null;

    Stage window;
    public static Boolean modeSelected = false;
    public void boardSelectSetting(){
        modeSelect.setStyle("-fx-font: 40 arial");
        modeSelect.setX(XMAX / 2 - 50);
        modeSelect.setY(YMAX / 2 - 150);
        modeSelect.setFill(Color.BLACK);

        standardMode.setStyle("-fx-font: 20 arial");
        standardMode.setX(XMAX / 2 - 50);
        standardMode.setY(YMAX / 2 - 50);
        standardMode.setFill(Color.RED);

        itemMode.setStyle("-fx-font: 20 arial");
        itemMode.setX(XMAX / 2 - 50);
        itemMode.setY(YMAX / 2);
        itemMode.setFill(Color.BLACK);

        difficulty.setStyle("-fx-font: 40 arial");
        difficulty.setX(XMAX / 2 + 100);
        difficulty.setY(YMAX / 2 - 150);
        difficulty.setFill(Color.BLACK);

        easeMode.setStyle("-fx-font: 20 arial");
        easeMode.setX(XMAX / 2 + 100);
        easeMode.setY(YMAX / 2 - 50);
        easeMode.setFill(Color.BLACK);

        normalMode.setStyle("-fx-font: 20 arial");
        normalMode.setX(XMAX / 2 + 100);
        normalMode.setY(YMAX / 2);
        normalMode.setFill(Color.BLACK);

        hardMode.setStyle("-fx-font: 20 arial");
        hardMode.setX(XMAX / 2 + 100);
        hardMode.setY(YMAX / 2 + 50);
        hardMode.setFill(Color.BLACK);

        Line line = new Line(0, YMAX / 2 -100 , XMAX*2, YMAX / 2 -100 );
        line.setStroke(Color.BLACK);

        group.getChildren().addAll(
                modeSelect, standardMode, itemMode,
                difficulty, easeMode, normalMode, hardMode,
                line
        );
    }
    @Override
    public void start(Stage boardSelect) throws Exception {
        System.out.println(menuSelected1);
        window = boardSelect;
        boardSelectSetting();
        boardSelectPress(boardSelectForm);

        boardSelect.setScene(scene);
        boardSelect.setTitle("T E T R I S");
        boardSelect.setResizable(false);
        boardSelect.show();
    }
    public void colorReset(){
        if(!modeSelected){
            standardMode.setFill(Color.BLACK);
            itemMode.setFill(Color.BLACK);
        }
        easeMode.setFill(Color.BLACK);
        normalMode.setFill(Color.BLACK);
        hardMode.setFill(Color.BLACK);
    }
    public void menuColoring(){
        colorReset();
        //모드 선택시 색상고정
        if(!modeSelected){
            switch (count1){
                case 1:
                    standardMode.setFill(Color.RED);
                    break;
                case 0:
                    itemMode.setFill(Color.RED);
                    break;
            }
        }else{
            switch (count1){
                case 1:
                    standardMode.setFill(Color.BLUE);
                    break;
                case 0:
                    itemMode.setFill(Color.BLUE);
                    break;
            }
            switch (count2){
                case 2:
                    easeMode.setFill(Color.RED);
                    break;
                case 1:
                    normalMode.setFill(Color.RED);
                    break;
                case 0:
                    hardMode.setFill(Color.RED);
                    break;
            }
        }
    }

    public void boardSelectPress(Form form) {
        scene.setOnKeyPressed((new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                selectBoard(event.getCode());
            }
        }));
    }

    public void selectBoard(KeyCode k)
    {
        switch (k) {
            case UP:
                if(!modeSelected){
                    if(count1 >= 0 && count1 < menu1_max -1){
                        ++count1;
                        menuSelected1 = selectOne.get(count1);
                        System.out.println(menuSelected1);
                    }
                    else{
                        count1 = 0;
                        menuSelected1 = selectOne.get(count1);
                        System.out.println(menuSelected1);
                    }

                }
                else{
                    if(count2 >= 0 && count2 < menu2_max -1){
                        ++count2;
                        menuSelected2 = selectTwo.get(count2);
                        System.out.println(menuSelected2);
                    }else{
                        count2 = 0;
                        menuSelected2 = selectTwo.get(count2);
                        System.out.println(menuSelected2);
                    }
                }
                menuColoring();
                break;

            case DOWN:
                if(!modeSelected){
                    if(count1 >= 1 && count1 < menu1_max){
                        --count1;
                        menuSelected1 = selectOne.get(count1);
                        System.out.println(menuSelected1);
                    }else{
                        count1 = menu1_max - 1;
                        menuSelected1 = selectOne.get(count1);
                        System.out.println(menuSelected1);
                    }
                }else{
                    if(count2 >= 1 && count2 < menu2_max){
                        --count2;
                        menuSelected2 = selectTwo.get(count2);
                        System.out.println(menuSelected2);
                    }else{
                        count2 = menu2_max - 1;
                        menuSelected2 = selectTwo.get(count2);
                        System.out.println(menuSelected2);
                    }
                }
                menuColoring();
                break;
            case SPACE:
                if(modeSelected){
                    LeaderBoard_menu.mode = menuSelected1;
                    switch (menuSelected2){
                        case "EASY":
                            LeaderBoard_menu.difficulty = "EASY";
                            break;
                        case "NORMAL":
                            LeaderBoard_menu.difficulty = "NORMAL";
                            break;
                        case "HARD":
                            LeaderBoard_menu.difficulty = "HARD";
                            break;
                    }
                    if(StartMenu.isLeaderboardOn){
                        System.out.println("check setscene");
                        LeaderBoard_menu.LeaderBoard();
                        window.setScene(LeaderBoard_menu.scene);
                    }else{
                        System.out.println("check start");
                        try{
                            LeaderBoard_menu leaderBoard_menu = new LeaderBoard_menu();
                            if(window==null)
                            {
                                System.out.println("!window");
                            }
                            else
                                leaderBoard_menu.start(window);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }else{
                    count2 = menu2_max - 1;
                    menuSelected2 = selectTwo.get(count2);
                    System.out.println(menuSelected2);
                    easeMode.setFill(Color.RED);
                    modeSelected = true;
                    menuColoring();

                }
                break;
            case BACK_SPACE:
                System.out.println("modeSelected:"+modeSelected);
                if(modeSelected){
                    modeSelected = false;
                    count2 = null;
                    menuColoring();
                }else{
                    window.setScene(StartMenu.scene);
                }
                break;
        }
    }
}
