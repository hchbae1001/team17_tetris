package kr.ac.seoultech;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NewShape extends Text{

    public static final int MOVE = Tetris.MOVE;
    public static final int SIZE = Tetris.SIZE;
    public static int XMAX = Tetris.XMAX;
    public static int YMAX = Tetris.YMAX;



    public NewShape() {
        super();
        setText("o");
    }

    public  NewShape(String shape) {
        super(shape);
    }


    public NewShape(double x, double y, String shape) {
        super(x,y,shape);
        setStyle(String.format("-fx-font: %d arial;", MOVE+20));
        setFill(Color.ORANGE);
        //setX(x);
        //setY(y);
    }

}
