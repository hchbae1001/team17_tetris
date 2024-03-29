package kr.ac.seoultech;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
//import javafx.scene.shape.Polygon;
//import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Form {
    public NewShape a;
    public NewShape b;
    public NewShape c;
    public NewShape d;


    protected Color color;
    final private String name;
    public int form = 1;
    // (colorBlindMode = true) == 색각이상자 모드
    public static boolean colorBlindMode = false;




    public Form(NewShape a, NewShape b, NewShape c, NewShape d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        switch (name) {
            case "j":
                if(!colorBlindMode){
                    color = Color.SLATEGRAY;
                }else{
                    color = Color.YELLOW;
                }
                break;
            case "l":
                if(!colorBlindMode){
                    color = Color.BLACK;
                }else{
                    color = Color.BLACK;
                }
                break;
            case "o":
                if(!colorBlindMode){
                    color = Color.RED;
                }else{
                    //red -> vermilion
                    color = Color.rgb(213,94,0);
                }
                break;
            case "s":
                //99% 의 색각이상자 (적록색맹)을 위한 채도 변경
                if(!colorBlindMode){
                    color = Color.GREEN;
                }else{
                    //green -> bluish green
                    color = Color.rgb(0,158,115);
                }
                break;
            case "t":
                if(!colorBlindMode){
                    color = Color.BLUE;
                }else{
                    //blue -> skyblue
                    color = Color.rgb(135,206,235);
                }
                break;
            case "z":
                if(!colorBlindMode){
                    color = Color.PURPLE;
                }else{
                    color = Color.rgb(150,9,85);
                }
                break;
            case "i":
                if(!colorBlindMode){
                    color = Color.SANDYBROWN;
                }else{
                    color = Color.ORANGE;
                }
                break;
            default:
                color = null;
        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }


    public String getName() {
        return name;
    }


    public void changeForm() {
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
}