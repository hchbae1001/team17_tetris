package kr.ac.seoultech;

import javafx.scene.paint.Color;

public class FormSix extends  Form{


    public NewShape e;
    public NewShape f;

    public FormSix(NewShape a, NewShape b, NewShape c, NewShape d, NewShape e, NewShape f, String name){
        super(a, b, c, d, name);
        this.e = e;
        this.e.setText("M");
        this.f = f;
        this.f.setText("M");

        double fsize = Tetris.MOVE*1.3;
        this.a.setStyle(String.format("-fx-font: %f arial;", fsize));
        this.b.setStyle(String.format("-fx-font: %f arial;", fsize));
        this.c.setStyle(String.format("-fx-font: %f arial;", fsize));
        this.d.setStyle(String.format("-fx-font: %f arial;", fsize));
        this.e.setStyle(String.format("-fx-font: %f arial;", fsize));
        this.f.setStyle(String.format("-fx-font: %f arial;", fsize));

        if(!Form.colorBlindMode){
            color = Color.HOTPINK;
        }else{
            color = Color.rgb(243, 92, 25);
        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
        this.e.setFill(color);
        this.f.setFill(color);
    }
}
