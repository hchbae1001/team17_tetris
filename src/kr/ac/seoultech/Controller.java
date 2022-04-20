package kr.ac.seoultech;

//import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
//import javafx.scene.text.Text;
import javafx.scene.text.Font;
import kr.ac.seoultech.*;

public class Controller {
    // Getting the numbers and the MESH from Tetris
    //public static final int DEADLINEGAP = Tetris.DEADLINEGAP;
    //public static final int MOVE = Tetris.MOVE;
    //public static final int SIZE = Tetris.SIZE;
    //public static int XMAX = Tetris.XMAX;
    //public static int YMAX = Tetris.YMAX;
    //public static int[][] Tetris.MESH = Tetris.MESH;

    public static void MoveRight(Form form) {
        if (form.a.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE && form.b.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE
                && form.c.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE && form.d.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE) {
            int movea = Tetris.MESH[((int) form.a.getX() / Tetris.SIZE) + 1][((int) form.a.getY() / Tetris.SIZE)];
            int moveb = Tetris.MESH[((int) form.b.getX() / Tetris.SIZE) + 1][((int) form.b.getY() / Tetris.SIZE)];
            int movec = Tetris.MESH[((int) form.c.getX() / Tetris.SIZE) + 1][((int) form.c.getY() / Tetris.SIZE)];
            int moved = Tetris.MESH[((int) form.d.getX() / Tetris.SIZE) + 1][((int) form.d.getY() / Tetris.SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                if(form instanceof FormSix){
                    if(((FormSix) form).e.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE && ((FormSix) form).f.getX() + Tetris.MOVE <= Tetris.XMAX - Tetris.SIZE) {
                        int movee = Tetris.MESH[((int) ((FormSix) form).e.getX() / Tetris.SIZE) + 1][((int) ((FormSix) form).e.getY() / Tetris.SIZE)];
                        int movef = Tetris.MESH[((int) ((FormSix) form).f.getX() / Tetris.SIZE) + 1][((int) ((FormSix) form).f.getY() / Tetris.SIZE)];
                        if (movee == 0 && movef == 0) {
                            ((FormSix) form).e.setX(((FormSix) form).e.getX() + Tetris.MOVE);
                            ((FormSix) form).f.setX(((FormSix) form).f.getX() + Tetris.MOVE);
                        } else {
                            return;
                        }
                    }
                }
                form.a.setX(form.a.getX() + Tetris.MOVE);
                form.b.setX(form.b.getX() + Tetris.MOVE);
                form.c.setX(form.c.getX() + Tetris.MOVE);
                form.d.setX(form.d.getX() + Tetris.MOVE);
            }/*else{
                for(int i = 0; i < Tetris.YMAX/Tetris.SIZE; i++){
                    System.out.println(String.format("%02d 번째 줄 : ", i) + Tetris.MESH[0][i] + Tetris.MESH[1][i] + Tetris.MESH[2][i] + Tetris.MESH[3][i] + Tetris.MESH[4][i] + Tetris.MESH[5][i] + Tetris.MESH[6][i]
                            + Tetris.MESH[7][i] + Tetris.MESH[8][i] + Tetris.MESH[9][i]);
                }
                System.out.println();
            }
            */
        }
    }

    public static void MoveLeft(Form form) {
        if (form.a.getX() - Tetris.MOVE >= 0 && form.b.getX() - Tetris.MOVE >= 0 && form.c.getX() - Tetris.MOVE >= 0
                && form.d.getX() - Tetris.MOVE >= 0) {
            int movea = Tetris.MESH[((int) form.a.getX() / Tetris.SIZE) - 1][((int) form.a.getY() / Tetris.SIZE)];
            int moveb = Tetris.MESH[((int) form.b.getX() / Tetris.SIZE) - 1][((int) form.b.getY() / Tetris.SIZE)];
            int movec = Tetris.MESH[((int) form.c.getX() / Tetris.SIZE) - 1][((int) form.c.getY() / Tetris.SIZE)];
            int moved = Tetris.MESH[((int) form.d.getX() / Tetris.SIZE) - 1][((int) form.d.getY() / Tetris.SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                if(form instanceof FormSix) {
                    if (((FormSix) form).e.getX() - Tetris.MOVE >= 0 && ((FormSix) form).f.getX() - Tetris.MOVE >= 0) {
                        int movee = Tetris.MESH[((int) ((FormSix) form).e.getX() / Tetris.SIZE) - 1][((int) ((FormSix) form).e.getY() / Tetris.SIZE)];
                        int movef = Tetris.MESH[((int) ((FormSix) form).f.getX() / Tetris.SIZE) - 1][((int) ((FormSix) form).f.getY() / Tetris.SIZE)];
                        if (movee == 0 && movef == 0) {
                            ((FormSix) form).e.setX(((FormSix) form).e.getX() - Tetris.MOVE);
                            ((FormSix) form).f.setX(((FormSix) form).f.getX() - Tetris.MOVE);
                        } else {
                            return;
                        }
                    }
                }
                form.a.setX(form.a.getX() - Tetris.MOVE);
                form.b.setX(form.b.getX() - Tetris.MOVE);
                form.c.setX(form.c.getX() - Tetris.MOVE);
                form.d.setX(form.d.getX() - Tetris.MOVE);
            }/*else{
                for(int i = 0; i < Tetris.YMAX/Tetris.SIZE; i++){
                    System.out.println(String.format("%02d 번째 줄 : ", i) + Tetris.MESH[0][i] + Tetris.MESH[1][i] + Tetris.MESH[2][i] + Tetris.MESH[3][i] + Tetris.MESH[4][i] + Tetris.MESH[5][i] + Tetris.MESH[6][i]
                            + Tetris.MESH[7][i] + Tetris.MESH[8][i] + Tetris.MESH[9][i]);
                }
                System.out.println();


            }
            */
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
        NewShape a = new NewShape(Tetris.SIZE-1, Tetris.SIZE-1, shape), b = new NewShape(Tetris.SIZE-1, Tetris.SIZE-1, shape), c = new NewShape(Tetris.SIZE-1, Tetris.SIZE-1, shape),
                d = new NewShape(Tetris.SIZE-1, Tetris.SIZE-1, shape);
        if (block < 10) {
            a.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            name = "j";
        } else if (block < 20) {
            a.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            name = "l";
        } else if (block < 30) {
            a.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            d.setX(Tetris.XMAX / 2);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            name = "o";
        } else if (block < 40) {
            a.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            d.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            name = "s";
        } else if (block < 50) {
            a.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            name = "t";
        } else if (block < 60) {
            a.setX(Tetris.XMAX / 2);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2 );
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP));
            name = "z";
        } else {
            a.setX(Tetris.XMAX / 2 - Tetris.SIZE - Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            name = "i";
        }
        return new Form(a, b, c, d, name);
    }

    public static Form makeItem() {
        int block = (int) (Math.random() * 50);
        String name;

        if (block < 10) {
            NewShape a = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), b = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), c = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"),
                    d = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), e = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M"), f = new NewShape(Tetris.SIZE - 1, Tetris.SIZE - 1, "M");
            a.setX(Tetris.XMAX / 2 - Tetris.SIZE - Tetris.SIZE);
            a.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            b.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            b.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            c.setX(Tetris.XMAX / 2);
            c.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            d.setX(Tetris.XMAX / 2 + Tetris.SIZE);
            d.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 1));
            e.setX(Tetris.XMAX / 2 - Tetris.SIZE);
            e.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 2));
            f.setX(Tetris.XMAX / 2);
            f.setY(Tetris.SIZE * (Tetris.DEADLINEGAP - 2));
            return new FormSix(a, b, c, d, e, f, "weight");
        }

        Form form = makeRect("o");
        String itemShape;
        double fsize = Tetris.MOVE*1.3;
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