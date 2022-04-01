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
//import javafx.scene.shape.Rectangle;
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
    public static int[][] MESH;
    public static final int BonusRate = 10;
    public static final int SpeedUpRate = 50;

    public enum Difficulty {Easy, Normal, Hard}

    public static Difficulty level = Difficulty.Easy;
    private static Pane group = new Pane();
    private static Form object;
    public static Scene scene;
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
    private static boolean directKeyPressed = false;
    public static boolean isWeightBlock = false;
    private static boolean weightIsLocked = false;
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
        MESH = new int[XMAX / SIZE][YMAX / SIZE];
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
        group.getChildren().addAll(nextObjPane);

        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.setResizable(false);
        stage.show();
        startTimer();

    }

    private void moveOnKeyPressArrow(Form form) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE:
                        directKeyPressed = true;
                        DirectlyMoveDown(form);
                        break;
                    case RIGHT:
                        if (weightIsLocked)
                            break;
                        Controller.MoveRight(form);
                        break;
                    case DOWN:
                        MoveDown(form);
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
        });
    }

    // 블럭 이동 키입력
    private void moveOnKeyPressWASD(Form form) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!directKeyPressed && !top) {
                    switch (event.getCode()) {
                        case SPACE:
                            directKeyPressed = true;
                            DirectlyMoveDown(form);
                            break;
                        case D:
                            if (weightIsLocked)
                                break;
                            Controller.MoveRight(form);
                            break;
                        case S:
                            MoveDown(form);
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
                break;
            case "s":
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
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
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
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
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
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
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
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
        }
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
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
                lines.add(i);
            }
            //lines.add(i + lines.size());
            full = 0;
        }
        if (lines.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof NewShape)
                        rects.add(node);
                }
                score += (bonusScore * 100 * lines.size());
                linesNo++;

                for (Node node : rects) {
                    NewShape a = (NewShape) node;
                    if (a.getY() == lines.get(0) * SIZE) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        pane.getChildren().remove(node);
                    } else
                        newrects.add(node);
                }

                for (Node node : newrects) {
                    NewShape a = (NewShape) node;
                    if (a.getY() < lines.get(0) * SIZE) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }
                lines.remove(0);
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
            } while (lines.size() > 0);
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
        score = score + bonusScore;
        updateScoretext();
        if (isWeightBlock)
            weightDown(form, group);
        if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                || form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
            MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
            if (form instanceof FormSix) {
                MESH[(int) ((FormSix) form).e.getX() / SIZE][(int) ((FormSix) form).e.getY() / SIZE] = 1;
                MESH[(int) ((FormSix) form).f.getX() / SIZE][(int) ((FormSix) form).f.getY() / SIZE] = 1;
            }
            RemoveRows(group);

            checkGameover(form);
            if (top)
                return;
            Form a = nextObj;
            if (itemModeBool.equals(true)) {

                if (linesNo > itemCount) {
                    nextObj = Controller.makeItem("o");
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
    }

    private void DirectlyMoveDown(Form form) {
        while (true) {
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
                }
                RemoveRows(group);

                checkGameover(form);
                if (top)
                    return;
                Form a = nextObj;
                if (itemModeBool.equals(true)) {
                    if (linesNo > itemCount) {
                        nextObj = Controller.makeItem("o");
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
        }
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
        game = false;
        updateScoretext();
        Text over = new Text("GAME OVER");
        over.setFill(Color.RED);
        over.setStyle("-fx-font: 50 arial;");
        over.setY(250);
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

    private void startTimer() {
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        // 데드라인을 넘어가는 경우(GAME OVER)는 MoveDown에서 다음 블럭이 생성되기 직전에 판정한다.
                        // 마지막 결과가 나온 뒤에는 game 변수가 false 가 된다.

                        // Exit
                        //if (top && !game) {
                        //    //System.exit(0);
                        //}

                        if (!top && game) {
                            System.out.println(dropPeriod);

                            if (score / 5000 >= bonusScore / BonusRate && dropPeriod != limitDropPeriod) {
                                if (dropPeriod > limitDropPeriod) {
                                    speedUp();
                                    fall.cancel(); // cancel time
                                    startTimer();   // start the time again with a new delay time
                                }
                            } else {

                                MoveDown(object);
                                updateScoretext();
                            }


                        }
                    }
                });
            }
        };
        fall.scheduleAtFixedRate(task, 0, dropPeriod);
    }

    private void updateScoretext() {
        scoretext.setText("Score: " + Integer.toString(score));
        linetesxt.setText("Lines: " + Integer.toString(linesNo));
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
            System.out.println("It is not Line Clear Item!!");
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
            } else
                newrects.add(node);

        }

        for (Node node : newrects) {
            NewShape a = (NewShape) node;
            if (a.getY() < LY) {
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
            System.out.println("It is not Bomb Item!!");
            return;
        }
        // SCORE 아이템 있으면 10만점 추가 후 삭제
        socreItem(pane, (int) (BY - SIZE * 2), (int) (BY + SIZE * 2), (int) (BX - SIZE * 2), (int) (BX + SIZE * 2));
        for (Node node : rects) {
            NewShape a = (NewShape) node;
            if (a.getX() <= BX + SIZE * 2 && a.getX() >= BX - SIZE * 2 && a.getY() <= BY + SIZE * 2 && a.getY() >= BY - SIZE * 2) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
            }
        }

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
            System.out.println("It is not Fill Item!!");
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