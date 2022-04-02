package kr.ac.seoultech;

import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {
    // The variables
    // 데드라인은 최소한 2 이상 ,하지만 일자 블럭 생성 직후부터 회전이 가능하려면 4 이상을 사용해야 함
    public static final int DEADLINEGAP = 4;
    public static int MOVE = Setting.MOVE;
    public static int SIZE = Setting.SIZE;
    public static int XMAX = SIZE * 10;
    public static int YMAX = SIZE * (20 - DEADLINEGAP);
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    public static final int BonusRate = 10;
    public static final int SpeedUpRate = 50;

    public enum Difficulty {Easy, Normal, Hard}

    public static Difficulty level = Difficulty.Easy;
    Timer fall = new Timer();
    private static Pane animPane = new Pane();
    private static Pane group = new Pane();
    private static Form object;
    public static Scene scene;

    private static Timer timer;
    // 일시 정지 UI
    private static Pane pausePane = new Pane();
    final private static ArrayList<String> pauseSelect = new ArrayList<String>(Arrays.asList(
            "Quit game", "Go To Menu", "Restart", "Continue"));
    private static String pauseSelected = "";
    final private static Integer pause_max = pauseSelect.size();
    private static Integer pauseCount = pauseSelect.size() - 1;
    final private static Rectangle pauseBox = new Rectangle();
    final private static Text pauseText = new Text("Pause");
    final private static Text pause3_continue = new Text("Continue");
    final private static Text pause2_restart = new Text("Restart");
    final private static Text pause1_menu = new Text("Go To Menu");
    final private static Text pause0_quit = new Text("Quit game");
    private static boolean timeStop = false;
    //
    public static int score = 0;
    private static boolean top = false;
    private static boolean game = true;
    private static Form nextObj;
    private static Pane nextObjPane = new Pane();
    private static int linesNo = 0;
    private static Text scoretext = new Text("Score: ");
    private static Text linetesxt = new Text("Lines: ");

    private static int itemCount = 0;
    private static int dropPeriod = 1000;
    private static int bonusScore = 10;
    private static int limitDropPeriod = 300;
    private static boolean isPaused = false;
    private static boolean directKeyPressed = false;
    public static boolean isWeightBlock = false;
    private static boolean weightIsLocked = false;
    private static boolean itemAnim = false;
    private static boolean rowRemoved = false;
    private static Text continueCounter = new Text("4");
    LeaderBoard_menu leaderBoard_menu = new LeaderBoard_menu();

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage window;
    public static String name;
    public static Boolean itemModeBool;
    public static int itemModeInt;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(level);
        System.out.println(itemModeBool);
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
        window = stage;
        XMAX = SIZE * 10;
        YMAX = SIZE * (20 + DEADLINEGAP);
        scene = new Scene(group, XMAX + 150, YMAX - SIZE);
        setNewGame();
        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.setResizable(false);
        stage.show();
        timer = startTimer(dropPeriod);

    }


    // 키입력
    private void moveOnKeyPressArrow(Form form) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(itemAnim)
                    return;
                if(!timeStop) {
                    if (isPaused) {
                        switch (event.getCode()) {
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
                                            window.setScene(StartMenu.scene);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "Quit game":
                                        System.out.println("exit");
                                        window.close();
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
                        switch (event.getCode()) {
                            case ESCAPE:
                                //fall.cancel();
                                //fall.purge();
                                group.getChildren().add(pausePane);
                                isPaused = true;
                                break;
                            case SPACE:
                                directKeyPressed = true;
                                MoveDown(form);
                                break;
                            case RIGHT:
                                if (weightIsLocked)
                                    break;
                                Controller.MoveRight(form);
                                break;
                            case DOWN:
                                MoveDown(form);//
                                break;
                            case LEFT:
                                if (weightIsLocked)
                                    break;
                                Controller.MoveLeft(form);
                                break;
                            case UP:
                                MoveTurn(form);
                                break;
                        }
                    }
                }
            }
        });
    }

    // 키입력
    private void moveOnKeyPressWASD(Form form) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!timeStop) {
                    if (isPaused) {
                        switch (event.getCode()) {
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
                                            window.setScene(StartMenu.scene);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "Quit game":
                                        System.out.println("exit");
                                        window.close();
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
                        switch (event.getCode()) {
                            case ESCAPE:
                                //fall.cancel();
                                //fall.purge();
                                group.getChildren().add(pausePane);
                                isPaused = true;
                                break;
                            case SPACE:
                                directKeyPressed = true;
                                MoveDown(form);
                                break;
                            case D:
                                if (weightIsLocked)
                                    break;
                                Controller.MoveRight(form);
                                break;
                            case S:
                                MoveDown(form);//
                                break;
                            case A:
                                if (weightIsLocked)
                                    break;
                                Controller.MoveLeft(form);
                                break;
                            case W:
                                MoveTurn(form);
                                break;
                        }
                    }
                }
            }
        });
    }

    private void MoveTurn(Form form) {
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

    private void RemoveRows(Pane pane) {
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
            for (int j = 0; j < MESH.length; j++) {
                if (MESH[j][i] == 1)
                    full++;
            }
            if (full == MESH.length) {
                if (itemModeBool.equals(true)) {
                    socreItem(pane, i * SIZE, 0, XMAX - SIZE);
                }
                lines = i;
                break;
            }
            full = 0;
        }
        if (lines != -1) {
            rowRemoved = true;
            for (Node node : pane.getChildren()) {
                if (node instanceof NewShape)
                    rects.add(node);
            }
            score += (bonusScore * 100);
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
                            RemoveRows(group);
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
                                MakeObject();
                                timer = startTimer(dropPeriod);

                            }
                        });
                    }
                };
                fall1.schedule(task1, 1000);
            }else {
                MakeObject();
                timer = startTimer(dropPeriod);
            }
        }
        updateScoretext();
    }

    private void MoveDown(NewShape rect) {
        if (rect.getY() + MOVE < YMAX)
            rect.setY(rect.getY() + MOVE);

    }

    private void MoveRight(NewShape rect) {
        if (rect.getX() + MOVE <= XMAX - SIZE)
            rect.setX(rect.getX() + MOVE);
    }

    private void MoveLeft(NewShape rect) {
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getX() - MOVE);
    }

    private void MoveUp(NewShape rect) {
        if (rect.getY() - MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    private void MoveDown(Form form) {
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
                RemoveRows(group);
                checkGameover(form);
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

    private void MakeObject(){
        Form a = nextObj;
        if (itemModeBool.equals(true)) {
            if (linesNo > itemCount) {
                nextObj = Controller.makeItem();
                itemCount = linesNo;
            } else {
                nextObj = Controller.makeRect("o");
            }
        } else {
            nextObj = Controller.makeRect("o");
        }
        object = a;
        // 블럭 생성 직후 겹치는 여부 확인
        isOverlap(object);

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

        if (Setting.keySettingBool.getText().equals("Arrow Keys")) {
            moveOnKeyPressArrow(a);
        } else {
            moveOnKeyPressWASD(a);
        }
        directKeyPressed = false;
        // 수정 필요성 있음
    }

    private boolean moveA(Form form) {
        return (MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
    }

    private boolean moveB(Form form) {
        return (MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
    }

    private boolean moveC(Form form) {
        return (MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
    }

    private boolean moveD(Form form) {
        return (MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
    }

    private boolean cB(NewShape rect, int x, int y) {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0)
            xb = rect.getX() + x * MOVE <= XMAX - SIZE;
        if (x < 0)
            xb = rect.getX() + x * MOVE >= 0;
        if (y >= 0)
            yb = rect.getY() - y * MOVE > 0;
        if (y < 0)
            yb = rect.getY() + y * MOVE < YMAX;
        return xb && yb && MESH[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
    }

    private void isOverlap(Form form) {
        boolean isEOG = false;
        while (MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE)] == 1 || MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE)] == 1 ||
                MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE)] == 1 || MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE)] == 1) {
            isEOG = true;
            object.a.setY(object.a.getY() - MOVE);
            object.b.setY(object.b.getY() - MOVE);
            object.c.setY(object.c.getY() - MOVE);
            object.d.setY(object.d.getY() - MOVE);
            top = true;
        }
        if (isEOG) {
            group.getChildren().addAll(object.a, object.b, object.c, object.d);
            nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
            showGameover();
        }
    }

    private void checkGameover(Form form) {
        // 블럭이 데드라인을 넘어가면 top을 true로 만들어 GAME OVER 판정
        for (int i = 0; i < XMAX / SIZE; i++) {
            if (MESH[i][DEADLINEGAP - 1] == 1) {
                top = true;
                showGameover();
                break;
            }
        }
    }

    private void showGameover() {
        if(!game)
            return;
        game = false;
        updateScoretext();
        Text over = new Text("GAME OVER");
        over.setFill(Color.RED);
        over.setStyle(String.format("-fx-font: %d arial;", XMAX/5 ));
        over.setY(YMAX/2);
        over.setX(10);
        group.getChildren().add(over);

        if (Leaderboard.topScores.get(9) < score) {
            TextInputDialog dialog = new TextInputDialog("name");
            dialog.setTitle("Leaderboard");
            dialog.setHeaderText(null);
            dialog.setContentText("이름을 입력해주세요");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                name = result.get();
                Leaderboard.addScore(score, name,level.ordinal()+itemModeInt*3);
                /*
                for (int i = 9; i >= 0; i--) {
                    if (Leaderboard.topScores.get(i) == score && Leaderboard.topUser.get(i) == name) {
                        LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                 */
            }
        }
        Leaderboard.saveScores(Leaderboard.fileName);

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

    private Timer startTimer(int delay) {
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {

                        if(isPaused){
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
                                MoveDown(object);//
                                updateScoretext();
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
        }
        fall.scheduleAtFixedRate(task, delay, dropPeriod);
        return fall;
    }

    public void continueGame(String what){
        timeStop = true;
        group.getChildren().add(continueCounter);
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if(continueCounter.getText().equals("4")) {
                            continueCounter.setText("3");
                        }else if(continueCounter.getText().equals("3")) {
                            continueCounter.setText("2");
                        }
                        else if(continueCounter.getText().equals("2")) {
                            continueCounter.setText("1");
                        }
                        else if(continueCounter.getText().equals("1")) {
                            continueCounter.setText("4");
                            isPaused = false;
                            timeStop = false;
                            group.getChildren().remove(continueCounter);
                            if(what.equals("Restart"))
                                setNewGame();
                            timer = startTimer(dropPeriod);
                            fall.cancel();
                        }
                    }
                });
            }
        };

        fall.scheduleAtFixedRate(task, 0, 1000);
    }

    private void updateScoretext() {
        scoretext.setText("Score: " + Integer.toString(score));
        linetesxt.setText("Lines: " + Integer.toString(linesNo));
    }

    public void setNewGame() {
        MESH = new int[XMAX / SIZE][YMAX / SIZE];
        for(int i = 0; i < YMAX/SIZE; i++){
            System.out.println(String.format("%02d 번째 줄 : ", i) + MESH[0][i] + MESH[1][i] + MESH[2][i] + MESH[3][i] + MESH[4][i] + MESH[5][i] + MESH[6][i]
                    + MESH[7][i] + MESH[8][i] + MESH[9][i]);
        }
        System.out.println();

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
        group.getChildren().addAll(scoretext, line, deadLine, linetesxt, nextText);


        Form a = nextObj;
        group.getChildren().addAll(a.a, a.b, a.c, a.d);
        if (Setting.keySettingBool.getText().equals("Arrow Keys")) {
            moveOnKeyPressArrow(a);
        } else {
            moveOnKeyPressWASD(a);
        }

        object = a;
        nextObj = Controller.makeRect("o");
        nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
        nextObjPane.setLayoutY(200);
        nextObjPane.setLayoutX(XMAX / 2 + SIZE * 2);
        group.getChildren().add(nextObjPane);

        group.getChildren().add(animPane);

    }

    public void deleteOldGame() {
        fall.cancel();
        group.getChildren().clear();
        nextObjPane.getChildren().clear();
        pausePane.getChildren().clear();
    }

    private void pauseColorReset(){
        pause3_continue.setFill(Color.BLACK);
        pause2_restart.setFill(Color.BLACK);
        pause1_menu.setFill(Color.BLACK);
        pause0_quit.setFill(Color.BLACK);
    }

    private void pauseColoring(){
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
                        RemoveRows(group);
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
            socreItem(pane, (int) (form.a.getY() + SIZE), (int) form.a.getX(), (int) form.d.getX());
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
        socreItem(pane, (int) LY, 0, XMAX - SIZE);
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
                        RemoveRows(group);
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
        socreItem(pane, (int) (BY - SIZE * 2), (int) (BY + SIZE * 2), (int) (BX - SIZE * 2), (int) (BX + SIZE * 2));
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
        socreItem(pane, (int) (FY - SIZE), (int) (FY + SIZE), (int) (FX - SIZE), (int) (FX + SIZE));
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

    public void socreItem(Pane pane, int y, int x1, int x2) {
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

    public void socreItem(Pane pane, int y1, int y2, int x1, int x2) {
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
}
