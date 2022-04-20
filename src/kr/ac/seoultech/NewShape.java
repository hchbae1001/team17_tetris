package kr.ac.seoultech;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NewShape extends Text{

    public NewShape() {
        super();
        setText("o");
    }

    //public NewShape(String shape) {
    //    super(shape);
    //}


    public NewShape(double x, double y, String shape) {
        super(x,y,shape);
        double fsize = Tetris.MOVE * 1.8;
        setStyle(String.format("-fx-font: %f arial;", fsize));
        setFill(Color.ORANGE);
        //setX(x);
        //setY(y);
    }

}
