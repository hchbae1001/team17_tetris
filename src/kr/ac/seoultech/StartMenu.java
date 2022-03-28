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
    private static Form object;
    private static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);

    //옵션 기록용 file
    private static String filepath = new File("").getAbsolutePath();
    private static String fileName = "Option";
    //페이지 내 표시할 것들
    private static Text menu1_start = new Text("START");
    private static Text menu2_setting = new Text("Setting");
    private static Text menu3_scoreBoard = new Text("Score Board");
    private static Text menu4_Exit = new Text("Exit");

    private static String menuSelected = "";


    @Override
    public void start(Stage primaryStage) throws Exception {
        menu1_start.setStyle("-fx-font: 20 arial");
        menu1_start.setX(XMAX / 2);
        menu1_start.setY(300);
        menu1_start.setFill(Color.BLACK);

        menu2_setting.setStyle("-fx-font: 20 arial");
        menu2_setting.setX(XMAX / 2);
        menu2_setting.setY(350);
        menu2_setting.setFill(Color.BLACK);

        menu3_scoreBoard.setStyle("-fx-font: 20 arial");
        menu3_scoreBoard.setX(XMAX / 2);
        menu3_scoreBoard.setY(400);
        menu3_scoreBoard.setFill(Color.BLACK);

        menu4_Exit.setStyle("-fx-font: 20 arial");
        menu4_Exit.setX(XMAX / 2);
        menu4_Exit.setY(450);
        menu4_Exit.setFill(Color.BLACK);


        group.getChildren().addAll(menu1_start,menu2_setting,menu3_scoreBoard,menu4_Exit);

        ArrayList<String> select = new ArrayList<String>(Arrays.asList(
                "start", "setting", "scoreBoard", "exit"));



        menuSelected = select.get(0);
//        private void menuPress(Form form){
//            scene.setOnKeyPressed((new EventHandler<KeyEvent>() {
//                @Override
//                public void handle(KeyEvent event) {
//                    switch (event.getCode()){
//                        case UP:
//
//                            break;
//
//                        case DOWN:
//
//                            break;
//
//                        case SPACE:
//                            if(){
//
//                            }
//                            break;
//                    }
//                }
//            }));
//        }

        primaryStage.setScene(scene);
        primaryStage.setTitle("T E T R I S");
        primaryStage.show();
    }
}
