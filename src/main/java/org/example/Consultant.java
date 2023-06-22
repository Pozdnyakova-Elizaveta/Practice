package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Consultant {
    static int quantity;
    static final int appearY=75;
    private Circle model;
    public Consultant(){
        model=new Circle();
        model.setCenterX(850+quantity*50);
        model.setCenterY(appearY);
        model.setRadius(15);
        model.setFill(Color.WHITE);
        model.setStrokeWidth(2);
        model.setStroke(Color.DARKRED);
        quantity++;
    }
    public Circle getModel(){
        return  model;
    }
}
