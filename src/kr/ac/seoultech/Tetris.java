package kr.ac.seoultech;

import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import kr.ac.seoultech.*;

public class Tetris extends Application{
    // The variables
    // 데드라인은 최소한 2 이상 ,하지만 일자 블럭 생성 직후부터 회전이 가능하려면 4 이상을 사용해야 함
    public static final int DEADLINEGAP = 4;
    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    private static Pane group = new Pane();
    private static Form object;
    private static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);
    public static int score = 0;
    private static boolean top = false;
    private static boolean game = true;
    private static Form nextObj = Controller.makeRect("o");
    private static Pane nextObjPane = new Pane();
    private static int linesNo = 0;

    private static Text scoretext = new Text("Score: ");

    private static Text level = new Text("Lines: ");

    private static int dropPeriod = 1000;
    private static int bonusScore = 10;
    private static final int limitDropPeriod = 100;

    private static boolean downPressed = false;

    public static void main(String[] args) {
        Leaderboard.loadScores();
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        for (int[] a : MESH) {
            Arrays.fill(a, 0);
        }
        Line deadLine = new Line(0, SIZE * (DEADLINEGAP - 1), XMAX, SIZE * (DEADLINEGAP - 1));
        deadLine.setStroke(Color.RED);
        Line line = new Line(XMAX, 0, XMAX, YMAX);
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        level.setStyle("-fx-font: 20 arial;");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);
        Text nextText = new Text("Next Block");
        nextText.setStyle("-fx-font: 20 arial;");
        nextText.setY(150);
        nextText.setX(XMAX + 5);
        nextText.setFill(Color.GREEN);
        group.getChildren().addAll(scoretext, line, deadLine, level, nextText);


        Form a = nextObj;
        group.getChildren().addAll(a.a, a.b, a.c, a.d);
        moveOnKeyPress(a);
        object = a;
        nextObj = Controller.makeRect("o");
        nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
        nextObjPane.setLayoutY(200);
        nextObjPane.setLayoutX(XMAX / 2 + SIZE * 3);
        group.getChildren().addAll(nextObjPane);



        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.show();
        startTimer();

        /*
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
                            if(dropPeriod > 100) {
                                sppedUp();
                            }
                            MoveDown(object);
                            scoretext.setText("Score: " + Integer.toString(score));
                            level.setText("Lines: " + Integer.toString(linesNo));
                        }
                    }
                });
            }
        };
        fall.scheduleAtFixedRate(task, 0, dropPeriod);

         */
    }

    // 블럭 이동 키입력
    private void moveOnKeyPress(Form form) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!downPressed && !top) {
                    switch (event.getCode()) {
                        case RIGHT:
                            Controller.MoveRight(form);
                            break;
                        case DOWN:
                            downPressed = true;
                            DirectlyMoveDown(form);
                            break;
                        case LEFT:
                            Controller.MoveLeft(form);
                            break;
                        case UP:
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
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        int full = 0;
        for (int i = 0; i < MESH[0].length; i++) {
            for (int j = 0; j < MESH.length; j++) {
                if (MESH[j][i] == 1)
                    full++;
            }
            if (full == MESH.length)
                lines.add(i);
            //lines.add(i + lines.size());
            full = 0;
        }
        if (lines.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof NewShape)
                        rects.add(node);
                }
                score += (bonusScore*100*lines.size());
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
        if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                || form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
            MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
            RemoveRows(group);

            checkGameover(form);
            if(top)
                return;
            Form a = nextObj;
            nextObj = Controller.makeRect("o");
            object = a;
            // 블럭 생성 직후 겹치는 여부 확인
            isOverlap(object);

            if(top)
                return;
            group.getChildren().addAll(a.a, a.b, a.c, a.d);
            nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
            moveOnKeyPress(a);
            downPressed = false;
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
            }
        }
    }

    private void DirectlyMoveDown(Form form) {
        while(true) {
            score = score + bonusScore;
            if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                    || form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {

                // 수정 필요성 있음
                MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
                MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
                MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
                MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
                RemoveRows(group);

                checkGameover(form);
                if(top)
                    return;
                Form a = nextObj;
                nextObj = Controller.makeRect("o");
                object = a;
                // 블럭 생성 직후 겹치는 여부 확인
                isOverlap(object);

                if(top)
                    return;
                group.getChildren().addAll(a.a, a.b, a.c, a.d);
                nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
                moveOnKeyPress(a);
                downPressed = false;
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

    private void isOverlap(Form form){
        boolean isEOG = false;
        while (MESH[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE)] == 1 || MESH[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE)] == 1 ||
                MESH[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE)] == 1 || MESH[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE)] == 1){
            isEOG = true;
            object.a.setY(object.a.getY() - MOVE);
            object.b.setY(object.b.getY() - MOVE);
            object.c.setY(object.c.getY() - MOVE);
            object.d.setY(object.d.getY() - MOVE);
            top = true;
        }
        if(isEOG){
            group.getChildren().addAll(object.a, object.b, object.c, object.d);
            nextObjPane.getChildren().addAll(nextObj.a, nextObj.b, nextObj.c, nextObj.d);
            showGameover();
        }
    }

    private void checkGameover(Form form){
        // 블럭이 데드라인을 넘어가면 top을 true로 만들어 GAME OVER 판정
        for (int i = 0; i < XMAX / SIZE; i++) {
            if (MESH[i][DEADLINEGAP - 1] == 1) {
                top = true;
                showGameover();
                break;
            }
        }
    }

    private void showGameover(){
        game = false;
        updateScoretext();
        Text over = new Text("GAME OVER");
        over.setFill(Color.RED);
        over.setStyle("-fx-font: 50 arial;");
        over.setY(250);
        over.setX(10);
        group.getChildren().add(over);
        if(Leaderboard.topScores.get(9)<score)
        {
            String name="";
            TextInputDialog dialog = new TextInputDialog("name");
            dialog.setTitle("Leaderboard");
            dialog.setHeaderText(null);
            dialog.setContentText("이름을 입력해주세요");

            Optional<String> result=dialog.showAndWait();

            if(result.isPresent())
            {
                name = result.get();
                Leaderboard.addScore(score,name);
            }
        }
        Leaderboard.saveScores();
    }

    public void speedUp() {
        bonusScore += 10;
        dropPeriod -= 100;
        if(dropPeriod < limitDropPeriod)
            dropPeriod = limitDropPeriod;
    }

    private void startTimer(){
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

                            if (score / 10000 >= bonusScore / 10 && dropPeriod != limitDropPeriod) {
                                if(dropPeriod > limitDropPeriod) {
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

    private void updateScoretext(){
        scoretext.setText("Score: " + Integer.toString(score));
        level.setText("Lines: " + Integer.toString(linesNo));
    }



}
