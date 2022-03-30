package kr.ac.seoultech;

import javafx.scene.paint.Color;

public class FormSix extends  Form{


    public NewShape e;
    public NewShape f;

    public FormSix(NewShape a, NewShape b, NewShape c, NewShape d, NewShape e, NewShape f, String name){
        super(a, b, c, d, name);
        this.e = e;
        this.f = f;
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
