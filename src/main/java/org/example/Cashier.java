package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cashier {
    static int quantity;
    static final int appearY=75;
    private Circle model;
    public Cashier(){
        model=new Circle();
        model.setCenterX(100+quantity*100);
        model.setCenterY(appearY);
        model.setRadius(15);
        model.setFill(Color.WHITE);
        model.setStrokeWidth(2);
        model.setStroke(Color.BLACK);
        quantity++;
    }
    public Circle getModel(){
        return  model;
    }
}
