package kr.ac.seoultech;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NewShape extends Text{

    public static final int MOVE = Tetris.MOVE;
    public static final int SIZE = Tetris.SIZE;


    public NewShape() {
        super();
        setText("o");
    }

    public NewShape(String shape) {
        super(shape);
    }


    public NewShape(double x, double y, String shape) {
        super(x,y,shape);
        double fsize = MOVE * 1.8;
        setStyle(String.format("-fx-font: %f arial;", fsize));
        setFill(Color.ORANGE);
        //setX(x);
        //setY(y);
    }

}
