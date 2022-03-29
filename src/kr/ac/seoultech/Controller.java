package kr.ac.seoultech;

//import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
//import javafx.scene.text.Text;
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
}