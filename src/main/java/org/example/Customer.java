package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Customer {
    static final int appearX=750;
    static final int appearY=25;
    private Circle model;
    public Customer(){
        model=new Circle();
        model.setCenterX(appearX);
        model.setCenterY(appearY);
        model.setRadius(15);
        model.setFill(Color.WHITE);
        model.setStrokeWidth(2);
        model.setStroke(Color.BLACK);
    }
    public Circle getModel(){
        return  model;
    }
}
