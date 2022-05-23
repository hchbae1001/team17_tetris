package kr.ac.seoultech;

import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class Tetris extends Application implements Runnable{
    public static boolean isTest = false;
    // The variables
    // 데드라인은 최소한 2 이상 ,하지만 일자 블럭 생성 직후부터 회전이 가능하려면 4 이상을 사용해야 함, 그냥 크게 설정하면 편함.
    public static final int DEADLINEGAP = 6;
    public static int MOVE = Setting.MOVE;
    public static int SIZE = Setting.SIZE;
    public static int XMAX = SIZE * 10;
    public static int YMAX = SIZE * (20 + DEADLINEGAP);
    public int[][] MESH;    // = new int[XMAX / SIZE][YMAX / SIZE];
    public int[][] P1_previous_MESH;
    public int[][] P2_previous_MESH;
    public static int[][] P1_MESH;
    public static int[][] P2_MESH;
    public static final int BonusRate = 10;
    public static final int SpeedUpRate = 50;

    public enum Difficulty {Easy, Normal, Hard}

    public static Difficulty level = Difficulty.Easy;
    //Timer fall = new Timer();
    private Pane animPane = new Pane();
    private Pane group = new Pane();
    private Form object;
    //private static Form P1_object;
    //private static Form P2_object;

    private Timer timer = new Timer();
    public Timer cpTimer = new Timer();
    // 일시 정지 UI
    private Pane pausePane = new Pane();
    final private static ArrayList<String> pauseSelect = new ArrayList<String>(Arrays.asList(
            "Quit game", "Go To Menu", "Restart", "Continue"));
    private String pauseSelected = "";
    final private Integer pause_max = pauseSelect.size();
    private Integer pauseCount = pauseSelect.size() - 1;
    final private Rectangle pauseBox = new Rectangle();
    final private Text pauseText = new Text("Pause");
    final private Text pause3_continue = new Text("Continue");
    final private Text pause2_restart = new Text("Restart");
    final private Text pause1_menu = new Text("Go To Menu");
    final private Text pause0_quit = new Text("Quit game");
    public static Text timerText = new Text("");
    private static boolean timeStop = false;
    //
    public int score = 0;
    private static boolean top = false;
    static boolean game = true;
    private Form nextObj;
    private Pane nextObjPane = new Pane();
    private Pane showQueuePane = new Pane();
    private int linesNo = 0;
    private Text scoretext = new Text("Score: ");
    private Text linetesxt = new Text("Lines: ");

    private int itemCount = 0;
    private int dropPeriod = 1000;
    private int bonusScore = 10;
    private static int limitDropPeriod = 300;
    private boolean isPaused = false;
    private boolean directKeyPressed = false;
    private boolean isWeightBlock = false;
    private boolean weightIsLocked = false;
    private boolean itemAnim = false;
    private boolean rowRemoved = false;
    private Text continueCounter = new Text("4");
    LeaderBoard_menu leaderBoard_menu = new LeaderBoard_menu();

    private boolean isFocused = false;

    public static void main(String[] args) {
        launch(args);
    }

    public Scene scene;
    public Stage window;
    public static String name;
    public static Boolean itemModeBool;
    public static int itemModeInt;

    // 대전 모드 관련 변수들
    public static Boolean cp = true;
    private int pid = 1;
    private boolean isArroyKey;
    private Thread inputThread;
    private Thread tetrisThread;
    public Tetris player2 = null;
    private static Text winnerText1;
    private static Text winnerText2;
    private static Queue<KeyCode> inputQueue = new LinkedList<>();
    public static Queue<int[]> P1_attackQueue;
    public static Queue<int[]> P2_attackQueue;
    static int P1_attackArray[][];
    static int P2_attackArray[][];
    static boolean P1_showIsChanged;
    static boolean P2_showIsChanged;
    public static int cpTime = 60000;
    private Rectangle nextObjectEdge = new Rectangle();
    private Rectangle attackShowEdge = new Rectangle();

    private static int cpMaxCounter = cpTime / 1000;
    private static int cpCounter = cpMaxCounter;
    public static boolean tm = false;

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println(level);
        System.out.println(itemModeBool);

        window = stage;
        XMAX = SIZE * 10;
        YMAX = SIZE * (20 + DEADLINEGAP);
        scene = new Scene(group, XMAX + 150, YMAX - SIZE);

        setNewGame();

        window.setScene(scene);
        //stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.setResizable(false);
        stage.show();
        timer = startTimer(dropPeriod);
        if(isTest)
            window.close();
    }

    @Override
    public void run() {
        System.out.println("player2 thread start");

        Platform.runLater(new Runnable() {

            @Override

            public void run(){
                pid = 2;

                isFocused =  true;
                // UI 생성 및 변경 코드
                window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setX(Screen.getPrimary().getBounds().getWidth()/2);
                window.setY(0);
                scene = new Scene(group, XMAX + 150, YMAX - SIZE);
                //window2.showAndWait();

                setNewGame();

                window.setScene(scene);
                window.setTitle("T E T R I S 2");
                window.setResizable(false);
                window.show();
                timer = startTimer(dropPeriod);

                //window.setScene(scene2);
                //stage.setScene(scene);
                //window2.setTitle("T E T R I S 2");
                //window2.setResizable(false);
                //window2.show();
                //timer = startTimer(dropPeriod);

                if(isTest)
                    window.close();

            }

        });


        //window2.initModality(Modality.APPLICATION_MODAL);
        //window2.setTitle("T E T R I S 2");
        //window2.setMinWidth(300);



        //Pane pane = new Pane();
        //Scene scene = new Scene(pane, 300, 500);
        //window2.setScene(scene);
        //window2.showAndWait();
        System.out.println("player2 thread Run method end");

        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                //window.close();
            }
        });

    }

    // 키입력
    private void moveOnKeyPressArrow(Form form, Scene controlScene) {
        controlScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                cpQueueAdd(event.getCode());
                if(!timeStop && !itemAnim) {
                    arrowKeyCodeFunc(event.getCode(), form);
                }
            }
        });
    }

    public void arrowKeyCodeFunc(KeyCode keyCode, Form form){
        if (isPaused) {
            if(pid == 2)
                return;
            switch (keyCode) {
                case ESCAPE:
                    continueGame("Continue");
                    group.getChildren().remove(pausePane);
                    break;
                case SPACE:
                    switch (pauseSelected) {
                        case "Continue":
                            continueGame("Continue");
                            group.getChildren().remove(pausePane);
                            break;
                        case "Restart":
                            // 초기화 메서드 호출
                            deleteOldGame();
                            continueGame("Restart");
                            break;
                        case "Go To Menu":
                            try {
                                deleteOldGame();
                                window.setWidth(440);
                                window.setHeight(613);
                                window.setScene(StartMenu.scene);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "Quit game":
                            System.out.println("exit");
                            window.close();
                            System.exit(0);
                            break;
                    }
                    break;
                case DOWN:
                    if (pauseCount >= 1 && pauseCount < pause_max) {
                        --pauseCount;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    } else {
                        pauseCount = pause_max - 1;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    }
                    pauseColoring();
                    break;
                case UP:
                    if (pauseCount >= 0 && pauseCount < pause_max - 1) {
                        ++pauseCount;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    } else {
                        pauseCount = 0;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    }
                    pauseColoring();
                    break;
            }
        } else {
            switch (keyCode) {
                case ESCAPE:
                    if(pid==2)
                        return;

                    group.getChildren().add(pausePane);
                    isPaused = true;
                    break;
                case SPACE:
                    directKeyPressed = true;
                    BlockDown(form);
                    break;
                case RIGHT:
                    if (weightIsLocked)
                        break;
                    Controller.MoveRight(form, MESH);
                    break;
                case DOWN:
                    BlockDown(form);//
                    break;
                case LEFT:
                    if (weightIsLocked)
                        break;
                    Controller.MoveLeft(form, MESH);
                    break;
                case UP:
                    MoveTurn(form);
                    break;
            }
        }
    }

    public void cpQueueAdd(KeyCode keyCode){
        if(cp) {
            inputQueue.add(keyCode);
        }
    }

    // 키입력
    private void moveOnKeyPressWASD(Form form, Scene controlScene) {
        controlScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //if(cp)
                //    inputQueue.add(event.getCode());
                if(!timeStop && !itemAnim) {
                    WASDKeyCodeFunc(event.getCode(), form);
                }
            }
        });
    }

    public void WASDKeyCodeFunc(KeyCode keyCode, Form form){
        if (isPaused) {
            switch (keyCode) {
                case ESCAPE:
                    if(cp)
                        player2.continueGame("Continue");
                    continueGame("Continue");
                    group.getChildren().remove(pausePane);
                    break;
                case Z:
                    switch (pauseSelected) {
                        case "Continue":
                            if(cp)
                                player2.continueGame("Continue");
                            continueGame("Continue");
                            group.getChildren().remove(pausePane);
                            break;
                        case "Restart":
                            // 초기화 메서드 호출
                            //???????????????????
                            if(cp) {
                                player2.deleteOldGame();
                                player2.continueGame("Restart");
                            }
                            deleteOldGame();
                            continueGame("Restart");
                            break;
                        case "Go To Menu":
                            goToMenu();
                            break;
                        case "Quit game":
                            System.out.println("exit");
                            window.close();
                            System.exit(0);
                            break;
                    }
                    break;
                case S:
                    if (pauseCount >= 1 && pauseCount < pause_max) {
                        --pauseCount;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    } else {
                        pauseCount = pause_max - 1;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    }
                    pauseColoring();
                    break;
                case W:
                    if (pauseCount >= 0 && pauseCount < pause_max - 1) {
                        ++pauseCount;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    } else {
                        pauseCount = 0;
                        pauseSelected = pauseSelect.get(pauseCount);
                        System.out.println(pauseCount);
                    }
                    pauseColoring();
                    break;
            }
        } else {
            switch (keyCode) {
                case ESCAPE:
                    if(cp){
                        if(!itemAnim && !player2.getItemAnim()){
                            group.getChildren().add(pausePane);
                            isPaused = true;
                            player2.isPaused = true;
                            break;
                        }else {
                            break;
                        }
                    }else {
                        group.getChildren().add(pausePane);
                        isPaused = true;
                        break;
                    }
                case Z:
                    directKeyPressed = true;
                    BlockDown(form);
                    break;
                case D:
                    if (weightIsLocked)
                        break;
                    Controller.MoveRight(form, MESH);
                    break;
                case S:
                    BlockDown(form);//
                    break;
                case A:
                    if (weightIsLocked)
                        break;
                    Controller.MoveLeft(form, MESH);
                    break;
                case W:
                    MoveTurn(form);
                    break;
            }
        }
    }

    public void goToMenu(){
        try {
            if(cp) {
                inputThread.interrupt();
                player2.window.close();
                player2.deleteOldGame();
            }
            deleteOldGame();
            window.setWidth(440);
            window.setHeight(613);
            window.setScene(StartMenu.scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MoveTurn(Form form) {
        int f = form.form;
        NewShape a = form.a;
        NewShape b = form.b;
        NewShape c = form.c;
        NewShape d = form.d;
        switch (form.getName()) {
            case "j":
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "l":
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "o":
                if (f == 1) {
                    MoveRight(form.a);
                    MoveDown(form.b);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2) {
                    MoveDown(form.a);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3) {
                    MoveLeft(form.a);
                    MoveUp(form.b);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4) {
                    MoveUp(form.a);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
            case "s":
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveLeft(form.a);
                    MoveDown(form.a);
                    MoveUp(form.c);
                    MoveLeft(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 0)) {
                    MoveUp(form.a);
                    MoveLeft(form.a);
                    MoveRight(form.c);
                    MoveUp(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveRight(form.a);
                    MoveUp(form.a);
                    MoveDown(form.c);
                    MoveRight(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, 0)) {
                    MoveDown(form.a);
                    MoveRight(form.a);
                    MoveLeft(form.c);
                    MoveDown(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "t":
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "z":
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(b, 1, -1) && cB(c, 1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.b);
                    MoveRight(form.b);
                    MoveRight(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveLeft(form.b);
                    MoveDown(form.b);
                    MoveDown(form.c);
                    MoveRight(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, 1) && cB(c, -1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.b);
                    MoveLeft(form.b);
                    MoveLeft(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "i":
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 2, -2) && cB(b, 1, -1) && cB(d, -1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveDown(form.b);
                    MoveRight(form.b);
                    MoveUp(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.b);
                    MoveDown(form.b);
                    MoveRight(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, 2) && cB(b, -1, 1) && cB(d, 1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveUp(form.b);
                    MoveLeft(form.b);
                    MoveDown(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
        }
    }

    public void RemoveRows(Pane pane, int lineCount) {
        if (itemModeBool.equals(true)) {
            lineClearItem(pane);
            bombItem(pane);
            fillItem(pane);
            if(rowRemoved){

            }
            else if(itemAnim)
                return;
        }
        ArrayList<Node> rects = new ArrayList<Node>();
        int lines = -1;
        ArrayList<Node> newrects = new ArrayList<Node>();
        int full = 0;
        for (int i = 0; i < MESH[0].length; i++) {
            for (int j = MESH.length - 1; j >= 0; j--) {
                if (MESH[j][i] == 1)
                    full++;
            }
            if (full == MESH.length) {
                if (itemModeBool.equals(true)) {
                    scoreItem(pane, i * SIZE, 0, XMAX - SIZE);
                }
                lines = i;
                System.out.println("Remove line : " + lines);
                break;
            }
            full = 0;
        }
        if (lines != -1) {
            rowRemoved = true;
            //TEST
            //inputDeleteQueue(lines,2);
            inputDeleteQueue(lines, pid);

            for (Node node : pane.getChildren()) {
                if (node instanceof NewShape)
                    rects.add(node);
            }
            System.out.println("Score + " + (bonusScore * 100 * (lineCount + 1)));
            score += (bonusScore * 100 * (lineCount + 1));
            linesNo++;

            for (Node node : rects) {
                NewShape a = (NewShape) node;
                if (a.getY() == lines * SIZE) {
                    MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                    pane.getChildren().remove(node);
                    animPane.getChildren().add(node);
                    deleteAnim1(node, animPane, 0);
                } else
                    newrects.add(node);
            }
            itemAnim = true;
            timer.cancel();

            Timer fall = new Timer();
            int finalLines = lines;
            TimerTask task = new TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            //
                            for (Node node : newrects) {
                                NewShape a = (NewShape) node;
                                if (a.getY() < finalLines * SIZE) {
                                    MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                                    a.setY(a.getY() + SIZE);
                                }
                            }

                            rects.clear();
                            newrects.clear();
                            for (Node node : pane.getChildren()) {
                                if (node instanceof NewShape)
                                    rects.add(node);
                            }
                            for (Node node : rects) {
                                NewShape a = (NewShape) node;
                                try {
                                    MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                                } catch (ArrayIndexOutOfBoundsException e) {
                                }
                            }
                            rects.clear();

                            //itemAnim = false;
                            //MakeObject();
                            RemoveRows(group, lineCount + 1);
                            //timer = startTimer();
                        }
                    });
                }
            };

            itemAnim = true;
            //waitForTimer();
            fall.schedule(task, 1000);
        }else {
            if(rowRemoved){
                Timer fall1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    public void run() {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                //
                                rowRemoved = false;
                                itemAnim = false;
                                GenerateLine();
                                MakeObject();
                                timer = startTimer(dropPeriod);

                            }
                        });
                    }
                };
                fall1.schedule(task1, 1000);
            }else {
                GenerateLine();
                MakeObject();
                timer = startTimer(dropPeriod);
            }
        }
        updateScoretext();
    }

    public void MoveDown(NewShape rect) {
        if (rect.getY() + MOVE < YMAX)
            rect.setY(rect.getY() + MOVE);

    }

    public void MoveRight(NewShape rect) {
        if (rect.getX() + MOVE <= XMAX - SIZE)
            rect.setX(rect.getX() + MOVE);
    }

    public void MoveLeft(NewShape rect) {
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getX() - MOVE);
    }

    public void MoveUp(NewShape rect) {
        if (rect.getY() - MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    public void BlockDown(Form form) {
        do {
            score = score + bonusScore;
            updateScoretext();
            if (isWeightBlock)
                weightDown(form, group);
            if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                    || form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {

                // 수정 필요성 있음
                MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
                MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
                MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
                MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
                if (form instanceof FormSix) {
                    MESH[(int) ((FormSix) form).e.getX() / SIZE][(int) ((FormSix) form).e.getY() / SIZE] = 1;
                    MESH[(int) ((FormSix) form).f.getX() / SIZE][(int) ((FormSix) form).f.getY() / SIZE] = 1;
                    weightDelete(group);
                }
                timer.cancel();
                RemoveRows(group, 0);
                if(checkGameover())
                    showGameover();
                if (top)
                    return;
                break;
            }

            if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
                    && form.d.getY() + MOVE < YMAX) {
                int movea = MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1];
                int moveb = MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1];
                int movec = MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1];
                int moved = MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1];
                if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                    form.a.setY(form.a.getY() + MOVE);
                    form.b.setY(form.b.getY() + MOVE);
                    form.c.setY(form.c.getY() + MOVE);
                    form.d.setY(form.d.getY() + MOVE);
                    if (form instanceof FormSix) {
                        ((FormSix) form).e.setY(((FormSix) form).e.getY() + MOVE);
                        ((FormSix) form).f.setY(((FormSix) form).f.getY() + MOVE);
                    }
                }
            }
        } while (directKeyPressed);
    }

    public void MakeObject(){
        //블럭이 생성될떄마다 (블럭이 바닥에 닿은 직후) MESH를 refresh
        if(top)
            return;

        Form a = nextObj;
        if (itemModeBool.equals(true)) {
            if (linesNo/10 > itemCount) {
                nextObj = Controller.makeItem();
                itemCount = linesNo/10;
            } else {
                nextObj = Controller.makeRect("o");
            }
        } else {
            nextObj = Controller.makeRect("o");
        }
        object = a;
        // 블럭 생성 직후 겹치는 여부 확인
        isOverlap();

        if (top)
            return;

        // 생성된 object가 weight 블럭이면
        if (object instanceof FormSix) {
            FormSix formSix = (FormSix) object;
            System.out.println("weight Block!");
            isWeightBlock = true;
            group.getChildren().addAll(a.a, a.b, a.c, a.d, formSix.e, formSix.f);
        } else {
            group.getChildren().addAll(a.a, a.b, a.c, a.d);
        }

        // 생성된 nextObj가 weight 블럭이면
        if (nextObj instanceof FormSix) {
            FormSix formSix = (FormSix) nextObj;
            nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d, formSix.e, formSix.f);
        } else {
            nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
        }

        if(isTest)
            return;

        if (Setting.keySettingBool.getText().equals("Arrow Keys")) {
            moveOnKeyPressArrow(a, scene);
        } else {
            moveOnKeyPressWASD(a, scene);
        }
        directKeyPressed = false;
        // 수정 필요성 있음

    }

    public boolean moveA(Form form) {
        return (MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
    }

    public boolean moveB(Form form) {
        return (MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
    }

    public boolean moveC(Form form) {
        return (MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
    }

    public boolean moveD(Form form) {
        return (MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
    }

    public boolean cB(NewShape rect, int x, int y) {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0)
            xb = rect.getX() + x * MOVE <= XMAX - SIZE;
        if (x < 0)
            xb = rect.getX() + x * MOVE >= 0;
        if (y >= 0)
            yb = rect.getY() - y * MOVE >= 0;
        if (y < 0)
            yb = rect.getY() - y * MOVE <= YMAX - SIZE;
        return xb && yb && MESH[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
    }

    public void isOverlap() {
        boolean isEOG = false;
        while (MESH[(int) object.a.getX() / SIZE][((int) object.a.getY() / SIZE)] == 1 || MESH[(int) object.b.getX() / SIZE][((int) object.b.getY() / SIZE)] == 1 ||
                MESH[(int) object.c.getX() / SIZE][((int) object.c.getY() / SIZE)] == 1 || MESH[(int) object.d.getX() / SIZE][((int) object.d.getY() / SIZE)] == 1) {
            isEOG = true;
            object.a.setY(object.a.getY() - MOVE);
            object.b.setY(object.b.getY() - MOVE);
            object.c.setY(object.c.getY() - MOVE);
            object.d.setY(object.d.getY() - MOVE);
            top = true;
        }

        if(isTest)
            return;

        if (isEOG) {
            group.getChildren().addAll(object.a, object.b, object.c, object.d);
            nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
            showGameover();
        }
    }

    public boolean checkGameover() {
        // 블럭이 데드라인을 넘어가면 top을 true로 만들어 GAME OVER 판정
        for (int i = 0; i < XMAX / SIZE; i++) {
            if (MESH[i][DEADLINEGAP - 1] == 1) {
                top = true;
                return true;
            }
        }
        return false;
    }

    public void showGameover() {
        if(!game)
            return;

        game = false;
        if(cp){
            //cpModeEnd();
            return;
        }
        updateScoretext();
        Text over = new Text("GAME OVER");
        over.setFill(Color.RED);
        over.setStyle(String.format("-fx-font: %d arial;", XMAX/5 ));
        over.setY(YMAX/2);
        over.setX(10);
        group.getChildren().add(over);

        if (Leaderboard.topScores.get(9) < score) {
            TextInputDialog dialog = new TextInputDialog("AAA");
            dialog.setTitle("Leaderboard");
            dialog.setHeaderText(null);
            dialog.setContentText("이름을 입력해주세요. (3글자)");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                name = result.get();
                while(name.length()!=3)
                {
                    dialog.setContentText("글자수가 올바르지 않습니다.");

                    result = dialog.showAndWait();

                    if(result.isPresent()){
                        name = result.get();
                    }
                    else{
                        name="___";
                        score=0;
                        break;
                    }
                }
                System.out.println("name length :" + name.length());
                Leaderboard.addScore(score, name,level.ordinal()+itemModeInt*3);
                System.out.println("add score difficulty : "+(level.ordinal()+itemModeInt*3));
            }
        }
        Leaderboard.saveScores(Leaderboard.fileName);

        deleteOldGame();
        if(window != null) {
            window.setWidth(440);
            window.setHeight(613);
        }

        LeaderBoard_menu leader = new LeaderBoard_menu();
        if (!StartMenu.isLeaderboardOn) {
            try {
                leader.start(window);
                StartMenu.isLeaderboardOn = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LeaderBoard_menu.LeaderBoard();
            window.setScene(LeaderBoard_menu.scene);
        }
    }

    public void speedUp() {
        if (level == Difficulty.Easy) {
            bonusScore += BonusRate;
            dropPeriod -= SpeedUpRate * 0.8;
        } else if (level == Difficulty.Normal) {
            bonusScore += BonusRate;
            dropPeriod -= SpeedUpRate;
        } else {
            bonusScore += BonusRate;
            dropPeriod -= SpeedUpRate * 1.2;
        }

        if (dropPeriod < limitDropPeriod)
            dropPeriod = limitDropPeriod;
    }
    public Timer cpTimer(int delay){
        Timer cpTimer = new Timer();
        TimerTask cpTask = new TimerTask() {
            @Override
            public void run() {
                if(isPaused || !game){
                    cpTimer.cancel();
                    return;
                }
                if(cpCounter >= 10){
                    timerText.setFill(Color.BLACK);
                    String time = Integer.toString(cpCounter);
                    timerText.setText(time);
                    cpCounter--;
                }
                else if(cpCounter < 10 && cpCounter > 0){
                    timerText.setFill(Color.RED);
                    String time = Integer.toString(cpCounter);
                    timerText.setText(time);
                    System.out.println("You have "+cpCounter+" seconds");
                    cpCounter--;
                }else if(cpCounter <= 0){
                    System.out.print("Game Ends");
                    showGameover();
                }
            }

        };
        if(isPaused){
            cpTimer.cancel();
            cpTask.cancel();
            cpTimer.purge();
        }else{
            cpTimer.schedule(cpTask,0, 1000);
        }
        return cpTimer;
    }


    public Timer startTimer(int delay) {
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if(isPaused || !game){
                            fall.cancel();
                            return;
                        }

                        if (!top && game) {
                            if (score / 5000 >= bonusScore / BonusRate && dropPeriod != limitDropPeriod) {
                                if (dropPeriod > limitDropPeriod) {
                                    System.out.println(dropPeriod);
                                    speedUp();
                                    System.out.println(dropPeriod);
                                    fall.cancel();
                                    timer = startTimer(0);   // start the time again with a new delay time
                                }
                            } else {
                                BlockDown(object);//
                                updateScoretext();
                                showQueue(pid);
                            }
                        }
                    }
                });
            }
        };
        if(isPaused){
            fall.cancel();
            task.cancel();
            fall.purge();
        }else{
            fall.scheduleAtFixedRate(task, delay, dropPeriod);
        }
        //fall.scheduleAtFixedRate(task, delay, dropPeriod);
        return fall;
    }

    public void continueGame(String what){

        if(what.equals("Restart")) {
            XMAX = SIZE * 10;
            YMAX = SIZE * (20 + DEADLINEGAP);
            if(window != null) {
                window.setWidth(XMAX + 140 + SIZE * 2);
                window.setHeight(YMAX + 38 - SIZE);
                if(cp && pid == 1) {
                    window.setX(0);
                    window.setY(0);
                }
                if(cp && pid == 2) {
                    window.setX(Screen.getPrimary().getBounds().getWidth()/2);
                    window.setY(0);
                }
            }
            if(cp && pid == 1 && inputThread != null)
                inputThread.interrupt();
        }



        timeStop = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                group.getChildren().add(continueCounter);
            }
        });
        Timer fall = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if(continueCounter.getText().equals("4")) {
                            System.out.println("3");
                            continueCounter.setText("3");
                        }else if(continueCounter.getText().equals("3")) {
                            System.out.println("2");
                            continueCounter.setText("2");
                        }
                        else if(continueCounter.getText().equals("2")) {
                            System.out.println("1");
                            continueCounter.setText("1");
                        }
                        else if(continueCounter.getText().equals("1")) {
                            System.out.println("땅");
                            continueCounter.setText("4");
                            isPaused = false;
                            timeStop = false;
                            group.getChildren().remove(continueCounter);
                            if(what.equals("Restart")) {
                                if(cp && pid == 1)
                                    createInputThread();
                                setNewGame();
                            }
                            timer = startTimer(dropPeriod);
                            fall.cancel();
                        }
                    }
                });
            }
        };
        if(window != null) {
            window.setWidth(XMAX + 140 + SIZE * 2);
            window.setHeight(YMAX + 38 - SIZE);
        }
        fall.scheduleAtFixedRate(task, 0, 1000);
    }

    public void updateScoretext() {
        scoretext.setText("Score: " + Integer.toString(score));
        linetesxt.setText("Lines: " + Integer.toString(linesNo));
    }

    public void setNewGame() {

        XMAX = SIZE * 10;
        YMAX = SIZE * (20 + DEADLINEGAP);
        if(window != null) {
            window.setWidth(XMAX + 140 + SIZE * 2);
            window.setHeight(YMAX + 38 - SIZE);
        }

        MESH = new int[XMAX / SIZE][YMAX / SIZE];
        //P1,P2 공격확인용 MESH 초기화
        P1_previous_MESH = new int[XMAX / SIZE][YMAX / SIZE];
        P2_previous_MESH = new int[XMAX / SIZE][YMAX / SIZE];
        P1_attackQueue = new LinkedList<>();
        P2_attackQueue = new LinkedList<>();
        P1_attackArray=new int [XMAX/SIZE][YMAX/SIZE];
        P2_attackArray=new int [XMAX/SIZE][YMAX/SIZE];
        /*
        for(int i = 0; i < YMAX/SIZE; i++){
            System.out.println(String.format("%02d 번째 줄 : ", i) + MESH[0][i] + MESH[1][i] + MESH[2][i] + MESH[3][i] + MESH[4][i] + MESH[5][i] + MESH[6][i]
                    + MESH[7][i] + MESH[8][i] + MESH[9][i]);
        }
        System.out.println();
         */
        inputQueue.clear();
        score = 0;
        isPaused = false;
        pauseCount = pause_max - 1;
        linesNo = 0;
        //itemModeBool = false;
        top = false;
        game = true;
        itemCount = 0;
        dropPeriod = 1000;
        bonusScore = 10;
        isPaused = false;
        timeStop = false;
        directKeyPressed = false;
        isWeightBlock = false;
        weightIsLocked = false;
        itemAnim = false;
        P1_showIsChanged = false;
        P2_showIsChanged = false;
        updateScoretext();

        // 일시 정지 UI 셋팅
        pauseBox.setWidth(XMAX + 50);
        pauseBox.setHeight(YMAX - SIZE - 100);
        pauseBox.setFill(Color.IVORY);
        pauseBox.setOpacity(0.5);
        pauseBox.setStroke(Color.BLACK);
        pauseBox.setX(50);
        pauseBox.setY(50);

        pauseText.setX(60);
        pauseText.setY(YMAX/7);
        pauseText.setFill(Color.BLACK);
        pauseText.setStyle(String.format("-fx-font: %d arial;", XMAX/5 ));

        pause3_continue.setStyle(String.format("-fx-font: %d arial;", SIZE ));
        pause3_continue.setX(XMAX/2);
        pause3_continue.setY(YMAX/2 - SIZE*4);
        pause3_continue.setFill(Color.RED);

        pause2_restart.setStyle(String.format("-fx-font: %d arial;", SIZE ));
        pause2_restart.setX(XMAX/2);
        pause2_restart.setY(YMAX/2 - SIZE*2);
        pause2_restart.setFill(Color.BLACK);

        pause1_menu.setStyle(String.format("-fx-font: %d arial;", SIZE ));
        pause1_menu.setX(XMAX/2);
        pause1_menu.setY(YMAX/2);
        pause1_menu.setFill(Color.BLACK);

        pause0_quit.setStyle(String.format("-fx-font: %d arial;", SIZE ));
        pause0_quit.setX(XMAX/2);
        pause0_quit.setY(YMAX/2 + SIZE*2);
        pause0_quit.setFill(Color.BLACK);

        continueCounter.setText("4");
        continueCounter.setStyle(String.format("-fx-font: %d arial;", SIZE*5 ));
        continueCounter.setX(XMAX/2);
        continueCounter.setY(YMAX/2 + SIZE*2);
        continueCounter.setFill(Color.RED);

        //초기 시작 시, Continue 에 커서
        pauseSelected = pauseSelect.get(pauseCount);

        pausePane.getChildren().addAll(pauseBox, pauseText, pause3_continue, pause2_restart, pause1_menu, pause0_quit);

        nextObj = Controller.makeRect("o");

        Leaderboard.loadScores(Leaderboard.fileName);

        if (level == Difficulty.Easy) {
            limitDropPeriod = 300;
        } else if (level == Difficulty.Normal) {
            limitDropPeriod = 200;
        } else {
            limitDropPeriod = 100;
        }

        for (int[] a : MESH) {
            Arrays.fill(a, 0);
        }
        Line deadLine = new Line(0, SIZE * (DEADLINEGAP - 1), XMAX, SIZE * (DEADLINEGAP - 1));
        deadLine.setStroke(Color.RED);
        Line line = new Line(XMAX, 0, XMAX, YMAX);
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        linetesxt.setStyle("-fx-font: 20 arial;");
        linetesxt.setY(100);
        linetesxt.setX(XMAX + 5);
        linetesxt.setFill(Color.GREEN);
        Text nextText = new Text("Next Block");
        nextText.setStyle("-fx-font: 20 arial;");
        nextText.setY(150);
        nextText.setX(XMAX + 5);
        nextText.setFill(Color.GREEN);
        // tmsec / 1000 && tostring
        timerText.setText(Integer.toString(cpTime/1000));
        timerText.setStyle(String.format("-fx-font: %d arial;", 50 ));
        timerText.setX(XMAX + 5);
        timerText.setY(200);
        timerText.setFill(Color.BLACK);

        nextObjectEdge.setWidth(SIZE * 4);
        nextObjectEdge.setHeight(SIZE * 4);
        nextObjectEdge.setFill(Color.WHITE);
        //nextObjectEdge.setOpacity(0.5);
        nextObjectEdge.setStroke(Color.BLACK);
        nextObjectEdge.setY(220 + SIZE*2);
        nextObjectEdge.setX(XMAX / 2 + SIZE * 5);
        if(cp == true && tm == true){
            group.getChildren().addAll(nextObjectEdge, scoretext, line, deadLine, linetesxt, nextText, timerText);
        }else{
            group.getChildren().addAll(nextObjectEdge, scoretext, line, deadLine, linetesxt, nextText);
        }

        Form a = nextObj;
        group.getChildren().addAll(a.a, a.b, a.c, a.d);

        object = a;
        nextObj = Controller.makeRect("o");
        nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
        nextObjPane.setLayoutY(200);
        nextObjPane.setLayoutX(XMAX / 2 + SIZE * 2);
        group.getChildren().add(nextObjPane);

        group.getChildren().add(animPane);



        if(isTest)
            return;

        if(itemModeBool)
        {
            LeaderBoard_menu.mode="ITEM";
            itemModeInt=1;
        }
        else
        {
            LeaderBoard_menu.mode="STANDARD";
            itemModeInt=0;
        }
        switch (level)
        {
            case Easy:
                LeaderBoard_menu.difficulty="EASY";
                break;
            case Normal:
                LeaderBoard_menu.difficulty="NORMAL";
                break;
            case Hard:
                LeaderBoard_menu.difficulty="HARD";
                break;
        }
        if(cp){
            attackShowEdge.setWidth(SIZE * 5);
            attackShowEdge.setHeight(SIZE * 5);
            attackShowEdge.setFill(Color.WHITE);
            //attackShowEdge.setOpacity(0.5);
            attackShowEdge.setStroke(Color.BLACK);
            attackShowEdge.setY(200 + SIZE * 7.5);
            attackShowEdge.setX(XMAX / 2 + SIZE * 5);
            showQueuePane.setLayoutY(200);
            showQueuePane.setLayoutX(XMAX/2+SIZE*5);
            group.getChildren().addAll(attackShowEdge, showQueuePane);

            if(pid == 1){
                window.setX(0);
                window.setY(0);
                isArroyKey = false;
                moveOnKeyPressWASD(a, scene);
                // Timer모드 타이머 돌리기
                if(cp == true && tm == true){
                    cpCounter = cpMaxCounter;
                    //50초 후 10초 카운트 -> 카운트 소진 시, 게임 종료
                    cpTimer = cpTimer(cpTime);
                    System.out.println("cp timer Mode Start");
                }

            }else if(pid == 2){
                isArroyKey = true;
                moveOnKeyPressArrow(a, scene);
            }else {
                System.out.println("Pid BUG !!!!!!!!!!!!!!!");
            }
        }else {
            if (Setting.keySettingBool.getText().equals("Arrow Keys")) {
                isArroyKey = true;
                moveOnKeyPressArrow(a, scene);
            } else {
                isArroyKey = false;
                moveOnKeyPressWASD(a, scene);
            }
        }


    }

    public void deleteOldGame() {
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                group.getChildren().clear();
                nextObjPane.getChildren().clear();
                pausePane.getChildren().clear();
                showQueuePane.getChildren().clear();
            }
        });
    }

    public void deleteOldGame_NoFX() {
            group.getChildren().clear();
            nextObjPane.getChildren().clear();
            pausePane.getChildren().clear();
            showQueuePane.getChildren().clear();
    }

    public void pauseColorReset(){
        pause3_continue.setFill(Color.BLACK);
        pause2_restart.setFill(Color.BLACK);
        pause1_menu.setFill(Color.BLACK);
        pause0_quit.setFill(Color.BLACK);
    }

    public void pauseColoring(){
        switch (pauseCount){
            case 3:
                pauseColorReset();
                pause3_continue.setFill(Color.RED);
                break;
            case 2:
                pauseColorReset();
                pause2_restart.setFill(Color.RED);
                break;
            case 1:
                pauseColorReset();
                pause1_menu.setFill(Color.RED);
                break;
            case 0:
                pauseColorReset();
                pause0_quit.setFill(Color.RED);
                break;
        }
    }

    public void waitForTimer(){
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        itemAnim = false;
                        RemoveRows(group, 0);
                    }
                });
            }
        };
        fall.schedule(task,1000);
    }

    public void deleteAnim1(Node node, Pane pane, int cnt) {
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                NewShape a = (NewShape) node;
                Platform.runLater(new Runnable() {
                    public void run() {
                        a.setFill(Color.BLACK);
                        deleteAnim2(node, pane,cnt + 1);
                    }
                });
            }
        };
        fall.schedule(task,100);

    }

    public void deleteAnim2(Node node, Pane pane, int cnt) {
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                NewShape a = (NewShape) node;
                Platform.runLater(new Runnable() {
                    public void run() {
                        if(cnt == 9){
                            pane.getChildren().remove(node);
                            return;
                        }
                        a.setFill(Color.IVORY);
                        deleteAnim1(node, pane,cnt + 1);
                    }
                });
            }
        };
        fall.schedule(task, 100);
    }

    public void createTetrisThread(){
        if(cp) {
            player2 = new Tetris();
            tetrisThread = new Thread(player2);

            tetrisThread.start();
        }
    }

    public void createInputThread(){
        if(cp) {
            inputThread = new Thread(new InputThread(inputQueue, this));

            inputThread.start();
        }
    }

    public void cpModeEnd(){
        //deleteOldGame();
        timeStop = true;
        System.out.println("cpModeEnd");
        if(pid == 1){
            String winner = "";

            if(tm == true){
                System.out.println("Timer MOde Ends");
                System.out.println("score1 :" + score + "player2.score :" + player2.score);
                if(score > player2.score){
                    winner = "Player 1 WIN";
                }else if(score < player2.score){
                    winner = "Player 2 WIN";
                }else {
                    winner = "DRAW";
                }
            }else{
                if(checkGameover()){
                    winner = "Player 2 WIN";
                }else {
                    winner = "Player 1 WIN";
                }
            }
            winnerText1 = new Text(winner);
            winnerText1.setFill(Color.RED);
            winnerText1.setStroke(Color.BLACK);
            winnerText1.setStyle(String.format("-fx-font: %d arial;", XMAX/5 ));
            winnerText1.setY(YMAX/2);
            winnerText1.setX(10);

            winnerText2 = new Text(winner);
            winnerText2.setFill(Color.RED);
            winnerText2.setStroke(Color.BLACK);
            winnerText2.setStyle(String.format("-fx-font: %d arial;", XMAX/5 ));
            winnerText2.setY(YMAX/2);
            winnerText2.setX(10);
            Platform.runLater(new Runnable() {
                @Override
                public void run(){
                    group.getChildren().add(winnerText1);
                    player2.group.getChildren().add(winnerText2);
                }
            });
            System.out.println("p1 cpModeEnd is called");
        }else if(pid == 2){
            //window.close();
            System.out.println("p2 cpModeEnd is called");
        }else {
            System.out.println("Pid BUG !!!!!!!!!");
        }



        /*
        if(score > player2.score){

        }else if(score < player2.score){

        }else {

        }

         */
    }

    public Form getObject(){
        return object;
    }

    public boolean getItemAnim() {
        return itemAnim;
    }

    public boolean getTimeStop() {
        return timeStop;
    }

    public boolean getGame() {
        return game;
    }

    public void setPid(int id) {
        pid = id;
    }

    // 아이템 모드용 메서드들

    public void weightDown(Form form, Pane pane) {
        if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                || form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
            weightIsLocked = true;
        }

        if (form.a.getY() != YMAX - SIZE) {
            ArrayList<Node> rects = new ArrayList<Node>();

            for (Node node : pane.getChildren()) {
                if (node instanceof NewShape)
                    rects.add(node);
            }
            // SCORE 아이템 있으면 10만점 추가 후 삭제
            scoreItem(pane, (int) (form.a.getY() + SIZE), (int) form.a.getX(), (int) form.d.getX());
            for (Node node : rects) {
                if (node instanceof NewShape) {
                    NewShape a = (NewShape) node;
                    if (a.getX() >= form.a.getX() && a.getX() <= form.d.getX() && a.getY() == form.a.getY() + SIZE) {
                        pane.getChildren().remove(node);
                        animPane.getChildren().add(node);
                        deleteAnim1(node, animPane, 0);
                    }
                }
            }
            MESH[(int) form.a.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] = 0;
            MESH[(int) form.b.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] = 0;
            MESH[(int) form.c.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] = 0;
            MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] = 0;
        } else {
            isWeightBlock = false;
            weightIsLocked = false;
        }
    }

    public void weightDelete(Pane pane){
        ArrayList<Node> rects = new ArrayList<Node>();

        for (Node node : pane.getChildren()) {
            if (node instanceof NewShape)
                rects.add(node);
        }

        for (Node node : rects) {
            if (node instanceof NewShape){
                NewShape a = (NewShape) node;
                if(a.getText().equals("M")){
                    MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                    pane.getChildren().remove(node);
                    animPane.getChildren().add(node);
                    deleteAnim1(node, animPane, 0);
                }
            }
        }

        timer.cancel();
        waitForTimer();
        itemAnim = true;
    }

    public void lineClearItem(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Node> newrects = new ArrayList<Node>();


        for (Node node : pane.getChildren()) {
            if (node instanceof NewShape)
                rects.add(node);
        }
        double LY = -1;
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getText() == "L") {
                LY = a.getY();
                break;
            }
        }
        if (LY == -1) {
            //System.out.println("It is not Line Clear Item!!");
            return;
        }
        // SCORE 아이템 있으면 10만점 추가 후 삭제
        scoreItem(pane, (int) LY, 0, XMAX - SIZE);
        score += bonusScore * 100;
        linesNo++;
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getY() == LY) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
                animPane.getChildren().add(node);
                deleteAnim1(node, animPane, 0);
            } else
                newrects.add(node);
        }
        timer.cancel();
        // 애니메이션 종료 후 진행합니다.

        Timer fall = new Timer();
        double finalLY = LY;
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //
                        for (Node node : newrects) {
                            NewShape a = (NewShape) node;
                            if (a.getY() < finalLY) {
                                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                                a.setY(a.getY() + SIZE);
                            }
                        }

                        rects.clear();
                        newrects.clear();
                        for (Node node : pane.getChildren()) {
                            if (node instanceof NewShape)
                                rects.add(node);
                        }
                        for (Node node : rects) {
                            NewShape a = (NewShape) node;
                            try {
                                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                            } catch (ArrayIndexOutOfBoundsException e) {
                            }
                        }
                        itemAnim = false;
                        RemoveRows(group, 1);
                        //MakeObject();
                        //RemoveRows(group);
                        //timer = startTimer();
                    }
                });
            }
        };

        itemAnim = true;
        //waitForTimer();
        fall.schedule(task,1000);




    }

    public void bombItem(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();

        for (Node node : pane.getChildren()) {
            if (node instanceof NewShape)
                rects.add(node);
        }
        double BY = -1;
        double BX = -1;
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getText() == "B") {
                BX = a.getX();
                BY = a.getY();
                break;
            }
        }

        if (BX == -1 || BY == -1) {
            //System.out.println("It is not Bomb Item!!");
            return;
        }
        // SCORE 아이템 있으면 10만점 추가 후 삭제
        scoreItem(pane, (int) (BY - SIZE * 2), (int) (BY + SIZE * 2), (int) (BX - SIZE * 2), (int) (BX + SIZE * 2));
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getX() <= BX + SIZE * 2 && a.getX() >= BX - SIZE * 2 && a.getY() <= BY + SIZE * 2 && a.getY() >= BY - SIZE * 2) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
                animPane.getChildren().add(node);
                deleteAnim1(node, animPane, 0);
            }
        }
        timer.cancel();
        waitForTimer();
        itemAnim = true;
    }

    public void fillItem(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();

        for (Node node : pane.getChildren()) {
            if (node instanceof NewShape)
                rects.add(node);
        }
        double FY = -1;
        double FX = -1;
        Color tempColor = Color.BLACK;
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getText() == "F") {
                FX = a.getX();
                FY = a.getY();
                tempColor = (Color) a.getFill();
                break;
            }
        }

        if (FX == -1 || FY == -1) {
            //System.out.println("It is not Fill Item!!");
            return;
        }
        // SCORE 아이템 있으면 10만점 추가 후 일반 블럭으로 변경
        scoreItem(pane, (int) (FY - SIZE), (int) (FY + SIZE), (int) (FX - SIZE), (int) (FX + SIZE));
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getX() <= FX + SIZE && a.getX() >= FX - SIZE && a.getY() <= FY + SIZE && a.getY() >= FY - SIZE) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
            }
        }
        for (int i = (int) FX - SIZE; i <= (int) FX + SIZE; ) {
            for (int j = (int) FY - SIZE; j <= (int) FY + SIZE; ) {
                if (i <= XMAX - SIZE && i >= 0 && j <= YMAX - SIZE && j >= DEADLINEGAP * SIZE) {
                    MESH[i / SIZE][j / SIZE] = 1;
                    NewShape SingleNewShape = new NewShape(0, 0, "o");
                    SingleNewShape.setX(i);
                    SingleNewShape.setY(j);
                    SingleNewShape.setFill(tempColor);
                    group.getChildren().add(SingleNewShape);
                }
                j += SIZE;
            }
            i += SIZE;
        }

    }

    public void scoreItem(Pane pane, int y, int x1, int x2) {
        ArrayList<Node> rects = new ArrayList<Node>();

        for (Node node : pane.getChildren()) {
            if (node instanceof NewShape)
                rects.add(node);
        }
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getText() == "S" && a.getY() == y && a.getX() >= x1 && a.getX() <= x2) {
                System.out.println("It is SCORE Item!!");
                score += 100000;
            }
        }
    }

    public void scoreItem(Pane pane, int y1, int y2, int x1, int x2) {
        ArrayList<Node> rects = new ArrayList<Node>();

        for (Node node : pane.getChildren()) {
            if (node instanceof NewShape)
                rects.add(node);
        }
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getText() == "S" && a.getY() >= y1 && a.getY() <= y2 && a.getX() >= x1 && a.getX() <= x2) {
                System.out.println("It is SCORE Item!!");
                score += 100000;
            }
        }
    }
    //이번 블럭이 바닥에 닿음으로써 줄이 없어지는지 확인

    //없어진다면
    //지워지는 줄 (i) 확인
    //(i)줄을 queue에 저장
    
    //아니라면 놓음으로써 변화한 MESH를 update

    //블럭이 바닥에 닿기전 MESH를 따로 저장 (기존 MESH는 바닥에 블럭이 닿았을때 줄이 제거 되는지 확인해야함)

    //줄 없어지는 바로 밑에 line받아서 queue에 저장하는 inputQueue함수
    //다끝나고 나서(줄이 지워지거나 블럭이 바닥에 닿거나) 뒤늦게 MESH를 카피하는 refresh_previous_MESH함수


    //새로 만든 변수
    //public int[][] previous_MESH => 기존의 MESH는 줄 없앨지 판단여부로 사용하기 때문에 그전의 상태를 보관하기 위한 MESH 필요 (대전모드이므로 2개 만들어야함)
    //private Queue<int[]> attackQueue => 삭제한 줄을 보관할 큐 (대전모드 이므로 2개 만들어야함)
    //일단은 P1만 사용해서 싱글플레이로 테스트

    //새로 만든 함수
    //public void inputDeleteQueue(int line, int pid)
    //public void refreshPreviousMESH(int pid)
    //public void generateAttackLine(int pid)
    //public void showQueue(int pid ,int[] delete_line)
    //public void removeShow(int pid)

    //줄을 없앨때 실행됨 => 줄을 없애고 블럭이 생성되기때문에 블럭이 생성되기 전으로 수정
    //플레이어 1 이 실행 할 경우 P1 attackQueue에 플레이어 1 이 지운 라인 저장
    //끝날때 ShowQueue함수로 연동
    public void inputDeleteQueue(int line, int pid)
    {
        int[] delete_line = new int[XMAX / SIZE];
        switch(pid)
        {
            case 1:
                //기존 MESH의 첫번째 인덱스가 X, 두번째 인덱스가 Y인데 같은 Y축을 통째로 넣어야 하므로 값을 복사하여 큐에 추가
                if(P1_attackQueue.size()<10)
                {
                    for(int i=0;i<delete_line.length;i++)
                    {
                        delete_line[i]=P1_previous_MESH[i][line];
                    }
                    P1_attackQueue.add(delete_line);

                    //attackArray 아래에 한줄 공간 만들기
                    for(int i=1;i<YMAX/SIZE-1;i++)
                    {
                        for(int j=0;j<XMAX/SIZE;j++)
                        {
                            P1_attackArray[j][i]=P1_attackArray[j][i+1];
                        }
                    }

                    //방금 삭제한 줄 추가
                    for(int i=0;i<XMAX/SIZE;i++)
                    {
                        P1_attackArray[i][YMAX/SIZE-1]=delete_line[i];
                    }

                    P1_showIsChanged=true;
                }
                break;
            case 2:
                //기존MESH의 첫번째 인덱스가 X, 두번째 인덱스가 Y인데 같은 Y축을 통째로 넣어야 하므로 값을 복사하여 큐에 추가
                if(P2_attackQueue.size()<10)
                {
                    for(int i=0;i<delete_line.length;i++)
                    {
                        delete_line[i]=P2_previous_MESH[i][line];
                    }
                    P2_attackQueue.add(delete_line);

                    for(int i=1;i<YMAX/SIZE-1;i++)//attackArray 아래에 한줄 공간 만들기
                    {
                        for(int j=0;j<XMAX/SIZE;j++)
                        {
                            P2_attackArray[j][i]=P2_attackArray[j][i+1];
                        }
                    }

                    for(int i=0;i<XMAX/SIZE;i++)//방금 삭제한 줄 추가
                    {
                        P2_attackArray[i][YMAX / SIZE - 1] = delete_line[i];
                    }

                    P2_showIsChanged=true;
                }
                break;
        }
    }

    public void refreshPreviousMESH(int pid)
    {
        switch(pid)
        {
            case 1:
                for(int i=0;i<XMAX/SIZE;i++)
                {
                    for(int j=0;j<YMAX/SIZE;j++)
                    {
                        P1_previous_MESH[i][j]=MESH[i][j];
                    }
                }
                break;
            case 2:
                for(int i=0;i<XMAX/SIZE;i++)
                {
                    for(int j=0;j<YMAX/SIZE;j++)
                    {
                        P2_previous_MESH[i][j]=MESH[i][j];
                    }
                }
                break;
        }
    }

    //큐가 비어있는지는 바닥에 블럭이 닿을때 마다 판단 => 판단은 실행중인 코드 내에서
    //큐가 비어있지 않으면 큐안에 들어있는 원소의 갯수를 구해서 원소 갯수만큼 MESH를 위로 올림 => 1
    //(Y축이 20칸 이므로 큐에 5칸이 차있으면 19~5를 14~0으로 옮김)
    //이후 윗공간부터 차례대로 한줄씩 채우며 팝 => 2
    //MESH는 MESH[X][Y]순서 이고 왼쪽상단이 0,0
    public void generateAttackLine(int pid)
    {
        switch(pid)
        {
            case 1:
                int queueCount1 = P2_attackQueue.size();
                ArrayList<Node> rects = new ArrayList<Node>();
                for(int i=queueCount1;i<YMAX/SIZE;i++)
                {
                    for(int j=0;j<XMAX/SIZE;j++)
                    {
                        MESH[j][i-queueCount1]=MESH[j][i];   //1
                    }
                }
                //블럭을 그래픽적으로 이동

                for (Node node : group.getChildren()) {
                    if (node instanceof NewShape)
                        rects.add(node);
                }
                for (Node node : rects) {
                    NewShape a = (NewShape) node;
                    a.setY(a.getY() - SIZE * queueCount1);
                }

               for(int i=YMAX/SIZE-queueCount1;i<YMAX/SIZE;i++)
                {
                    for(int j=0;j<XMAX/SIZE;j++)
                    {
                        MESH[j][i]=P2_attackQueue.peek()[j];    //2

                        //블럭을 그래픽적으로 생성
                        //1872번 줄 아이템 부분을 참고하여 블럭 줄을 생성하는 함수를 구현

                        if(MESH[j][i]==1)
                        {
                            NewShape SingleNewShape = new NewShape(0,0,"o");
                            SingleNewShape.setX(j*SIZE);
                            SingleNewShape.setY(i*SIZE);
                            SingleNewShape.setFill(Color.DARKGRAY);
                            group.getChildren().add(SingleNewShape);
                        }
                    }
                    P2_attackQueue.remove();
                }
                break;
            case 2:
                int queueCount2 = P1_attackQueue.size();
                ArrayList<Node> rects2 = new ArrayList<Node>();
                for(int i=queueCount2;i<YMAX/SIZE;i++)
                {
                    for(int j=0;j<XMAX/SIZE;j++)
                    {
                        MESH[j][i-queueCount2]=MESH[j][i];   //1
                    }
                }
                //블럭을 그래픽적으로 이동

                for (Node node : group.getChildren()) {
                    if (node instanceof NewShape)
                        rects2.add(node);
                }
                for (Node node : rects2) {
                    NewShape a = (NewShape) node;
                    a.setY(a.getY() - SIZE * queueCount2);
                }

                for(int i=YMAX/SIZE-queueCount2;i<YMAX/SIZE;i++)
                {
                    for(int j=0;j<XMAX/SIZE;j++)
                    {
                        MESH[j][i]=P1_attackQueue.peek()[j];    //2

                        //블럭을 그래픽적으로 생성
                        //1872번 줄 아이템 부분을 참고하여 블럭 줄을 생성하는 함수를 구현

                        if(MESH[j][i]==1)
                        {
                            NewShape SingleNewShape = new NewShape(0,0,"o");
                            SingleNewShape.setX(j*SIZE);
                            SingleNewShape.setY(i*SIZE);
                            SingleNewShape.setFill(Color.DARKGRAY);
                            group.getChildren().add(SingleNewShape);
                        }
                    }
                    P1_attackQueue.remove();
                }
                break;
        }
        removeShow(pid);
    }

    //다음블럭 아래쪽 위치에 작은 테트리스 칸을 만들어서 큐 표시
    //매개변수 pid는 공격을 하는 id(블럭을 지운id)
    public void showQueue(int pid)
    {
        switch (pid)
        {
            case 1:
                if(P2_showIsChanged)    //P2가 공격을 하면, P1의 ShowQueue가 실행
                {
                    //showQueuePane의 모든 블럭을 삭제 하기위해 temp를 만들어서 노드 저장 후 삭제
                    ArrayList<Node> tempNode = new ArrayList<Node>();

                    for(Node node : showQueuePane.getChildren())
                    {
                        tempNode.add(node);
                    }

                    for(Node node : tempNode)
                    {
                        showQueuePane.getChildren().remove(node);
                    }

                    //P2_AttackArray 그래픽으로 출력
                    for(int i=0;i<XMAX/SIZE;i++)
                    {
                        for(int j=0;j<YMAX/SIZE;j++)
                        {
                            if(P2_attackArray[i][j]==1)
                            {
                                NewShape SingleNewShape = new NewShape(0,0,"o", MOVE*1.8*0.5);
                                SingleNewShape.setX(i*SIZE*0.5);
                                SingleNewShape.setY(j*SIZE*0.5);
                                SingleNewShape.setFill(Color.DARKGRAY);
                                showQueuePane.getChildren().add(SingleNewShape);
                            }
                        }
                    }
                    P2_showIsChanged=false;
                }
                break;
            case 2:
                if(P1_showIsChanged)
                {
                    //showQueuePane의 모든 블럭을 삭제 하기위해 temp를 만들어서 노드 저장 후 삭제
                    ArrayList<Node> tempNode = new ArrayList<Node>();

                    for(Node node : showQueuePane.getChildren())
                    {
                        tempNode.add(node);
                    }

                    for(Node node : tempNode)
                    {
                        showQueuePane.getChildren().remove(node);
                    }

                    //P2_AttackArray 그래픽으로 출력
                    for(int i=0;i<XMAX/SIZE;i++)
                    {
                        for(int j=0;j<YMAX/SIZE;j++)
                        {
                            if(P1_attackArray[i][j]==1)
                            {
                                NewShape SingleNewShape = new NewShape(0,0,"o", MOVE*1.8*0.5);
                                SingleNewShape.setX(i*SIZE*0.5);
                                SingleNewShape.setY(j*SIZE*0.5);
                                SingleNewShape.setFill(Color.DARKGRAY);
                                showQueuePane.getChildren().add(SingleNewShape);
                            }
                        }
                    }
                    P1_showIsChanged=false;
                }
                break;
        }

    }

    public void removeShow(int pid)
    {
        switch (pid)
        {
            case 1:
                for(int i=0;i<XMAX/SIZE;i++)
                {
                    for(int j=0;j<YMAX/SIZE;j++)
                    {
                        P2_attackArray[i][j]=0;
                    }
                }

                ArrayList<Node> tempNode = new ArrayList<Node>();

                for(Node node : showQueuePane.getChildren())
                {
                    tempNode.add(node);
                }

                for(Node node : tempNode)
                {
                    showQueuePane.getChildren().remove(node);
                }
                break;
            case 2:
                for(int i=0;i<XMAX/SIZE;i++)
                {
                    for(int j=0;j<YMAX/SIZE;j++)
                    {
                        P1_attackArray[i][j]=0;
                    }
                }

                ArrayList<Node> tempNode2 = new ArrayList<Node>();

                for(Node node : showQueuePane.getChildren())
                {
                    tempNode2.add(node);
                }

                for(Node node : tempNode2)
                {
                    showQueuePane.getChildren().remove(node);
                }
                break;
        }

    }

    public void GenerateLine()
    {
        switch(pid)
        {
            case 1:
                if(!P2_attackQueue.isEmpty())
                    generateAttackLine(pid);    //1P는 2P의 공격이 차있으면 공격 줄 생성
                break;
            case 2:
                if(!P1_attackQueue.isEmpty())
                    generateAttackLine(pid);    //2P는 1P의 공격이 차있으면 공격 줄 생성
                break;
        }
        refreshPreviousMESH(pid);
    }
}