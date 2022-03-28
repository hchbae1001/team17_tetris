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
    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    private static Pane group = new Pane();
    private static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);
    private static Form menu;
    //옵션 기록용 file
    private static String filepath = new File("").getAbsolutePath();
    private static String fileName = "Option";
    //페이지 내 표시할 것들
    private static Text menu3_start = new Text("START");
    private static Text menu2_setting = new Text("Setting");
    private static Text menu1_scoreBoard = new Text("Score Board");
    private static Text menu0_Exit = new Text("Exit");

    //count = 3 -> start 에 커서
    private static Integer count = 3;
    private static String menuSelected = "";
    private static Integer result = 0;

    private static ArrayList<String> select = new ArrayList<String>(Arrays.asList(
            "exit", "scoreBoard", "setting",  "start"));
    private static Integer menu_max = select.size();
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
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



        menuPress(menu);

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
                                //Todo Tetris와 연결
                                System.out.println("start");
                                break;
                            case "setting":
                                //Todo 설정창 구현 후 연동 필요
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
