package kr.ac.seoultech;

//import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
//import javafx.scene.text.Text;
import javafx.scene.text.Font;
import kr.ac.seoultech.*;

public class Controller {
    // Getting the numbers and the MESH from Tetris
    public static final int DEADLINEGAP = Tetris.DEADLINEGAP;
    public static final int MOVE = Tetris.MOVE;
    public static final int SIZE = Tetris.SIZE;
    public static int XMAX = Tetris.XMAX;
    public static int YMAX = Tetris.YMAX;
    public static int[][] MESH = Tetris.MESH;

    public static void MoveRight(Form form) {
        if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
                && form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
            int movea = MESH[((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
            int moveb = MESH[((int) form.b.getX() / SIZE) + 1][((int) form.b.getY() / SIZE)];
            int movec = MESH[((int) form.c.getX() / SIZE) + 1][((int) form.c.getY() / SIZE)];
            int moved = MESH[((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                if(form instanceof FormSix){
                    if(((FormSix) form).e.getX() + MOVE <= XMAX - SIZE && ((FormSix) form).f.getX() + MOVE <= XMAX - SIZE) {
                        int movee = MESH[((int) ((FormSix) form).e.getX() / SIZE) + 1][((int) ((FormSix) form).e.getY() / SIZE)];
                        int movef = MESH[((int) ((FormSix) form).f.getX() / SIZE) + 1][((int) ((FormSix) form).f.getY() / SIZE)];
                        if (movee == 0 && movef == 0) {
                            ((FormSix) form).e.setX(((FormSix) form).e.getX() + MOVE);
                            ((FormSix) form).f.setX(((FormSix) form).f.getX() + MOVE);
                        } else {
                            return;
                        }
                    }
                }
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    public static void MoveLeft(Form form) {
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
                && form.d.getX() - MOVE >= 0) {
            int movea = MESH[((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
            int moveb = MESH[((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
            int movec = MESH[((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
            int moved = MESH[((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                if(form instanceof FormSix) {
                    if (((FormSix) form).e.getX() - MOVE >= 0 && ((FormSix) form).f.getX() - MOVE >= 0) {
                        int movee = MESH[((int) ((FormSix) form).e.getX() / SIZE) - 1][((int) ((FormSix) form).e.getY() / SIZE)];
                        int movef = MESH[((int) ((FormSix) form).f.getX() / SIZE) - 1][((int) ((FormSix) form).f.getY() / SIZE)];
                        if (movee == 0 && movef == 0) {
                            ((FormSix) form).e.setX(((FormSix) form).e.getX() - MOVE);
                            ((FormSix) form).f.setX(((FormSix) form).f.getX() - MOVE);
                        } else {
                            return;
                        }
                    }
                }
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    public static Form makeRect(String shape) {
        int block;
        if(Tetris.level == Tetris.Difficulty.Easy){
            block = (int) (Math.random() * 72);
        }
        else if(Tetris.level == Tetris.Difficulty.Normal) {
            block = (int) (Math.random() * 70);
        }else{
            block = (int) (Math.random() * 68);
        }
        String name;
        NewShape a = new NewShape(SIZE-1, SIZE-1, shape), b = new NewShape(SIZE-1, SIZE-1, shape), c = new NewShape(SIZE-1, SIZE-1, shape),
                d = new NewShape(SIZE-1, SIZE-1, shape);
        if (block < 10) {
            a.setX(XMAX / 2 - SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE * (DEADLINEGAP));
            c.setX(XMAX / 2);
            c.setY(SIZE * (DEADLINEGAP));
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE * (DEADLINEGAP));
            name = "j";
        } else if (block < 20) {
            a.setX(XMAX / 2 + SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE * (DEADLINEGAP));
            c.setX(XMAX / 2);
            c.setY(SIZE * (DEADLINEGAP));
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE * (DEADLINEGAP));
            name = "l";
        } else if (block < 30) {
            a.setX(XMAX / 2 - SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2);
            b.setY(SIZE * (DEADLINEGAP - 1));
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE * (DEADLINEGAP));
            d.setX(XMAX / 2);
            d.setY(SIZE * (DEADLINEGAP));
            name = "o";
        } else if (block < 40) {
            a.setX(XMAX / 2 + SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2);
            b.setY(SIZE * (DEADLINEGAP - 1));
            c.setX(XMAX / 2);
            c.setY(SIZE * (DEADLINEGAP));
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE * (DEADLINEGAP));
            name = "s";
        } else if (block < 50) {
            a.setX(XMAX / 2 - SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2);
            b.setY(SIZE * (DEADLINEGAP - 1));
            c.setX(XMAX / 2);
            c.setY(SIZE * (DEADLINEGAP));
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE * (DEADLINEGAP - 1));
            name = "t";
        } else if (block < 60) {
            a.setX(XMAX / 2);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE * (DEADLINEGAP - 1));
            c.setX(XMAX / 2 );
            c.setY(SIZE * (DEADLINEGAP));
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE * (DEADLINEGAP));
            name = "z";
        } else {
            a.setX(XMAX / 2 - SIZE - SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE * (DEADLINEGAP - 1));
            c.setX(XMAX / 2);
            c.setY(SIZE * (DEADLINEGAP - 1));
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE * (DEADLINEGAP - 1));
            name = "i";
        }
        return new Form(a, b, c, d, name);
    }

    public static Form makeItem(String shape) {
        int block = (int) (Math.random() * 50);
        String name;

        if (block < 10) {
            NewShape a = new NewShape(SIZE - 1, SIZE - 1, shape), b = new NewShape(SIZE - 1, SIZE - 1, shape), c = new NewShape(SIZE - 1, SIZE - 1, shape),
                    d = new NewShape(SIZE - 1, SIZE - 1, shape), e = new NewShape(SIZE - 1, SIZE - 1, shape), f = new NewShape(SIZE - 1, SIZE - 1, shape);
            a.setX(XMAX / 2 - SIZE - SIZE);
            a.setY(SIZE * (DEADLINEGAP - 1));
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE * (DEADLINEGAP - 1));
            c.setX(XMAX / 2);
            c.setY(SIZE * (DEADLINEGAP - 1));
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE * (DEADLINEGAP - 1));
            e.setX(XMAX / 2 - SIZE);
            e.setY(SIZE * (DEADLINEGAP - 2));
            f.setX(XMAX / 2);
            f.setY(SIZE * (DEADLINEGAP - 2));
            return new FormSix(a, b, c, d, e, f, "weight");
        }

        Form form = makeRect("o");
        String itemShape;
        double fsize = MOVE*1.3;
        if (block < 20) {
            itemShape = "B";
        } else if (block < 30) {
            itemShape = "S";
        } else if (block < 40) {
            itemShape = "F";
        } else {
            itemShape = "L";
        }

        int itemblock = (int) (Math.random() * 40);
        if(itemblock < 10){
            form.a.setStyle(String.format("-fx-font: %f arial;", fsize));
            form.a.setText(itemShape);
        } else if (itemblock < 20) {
            form.b.setStyle(String.format("-fx-font: %f arial;", fsize));
            form.b.setText(itemShape);
        } else if (itemblock < 30) {
            form.c.setStyle(String.format("-fx-font: %f arial;", fsize));
            form.c.setText(itemShape);
        } else {
            form.d.setStyle(String.format("-fx-font: %f arial;", fsize));
            form.d.setText(itemShape);
        }
        return form;
    }
}