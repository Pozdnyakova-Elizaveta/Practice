package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shelf {
    static int quantity;
    private Rectangle model;
    public Shelf(){
        model=new Rectangle();
        if (quantity<4) {
            model.setY(270);
            model.setX(quantity*200+300);
        }
        else {
            model.setY(470);
            model.setX((quantity-4)*200+300);
        }
        model.setWidth(50);
        model.setHeight(150);
        model.setFill(Color.ROSYBROWN);
        model.setStrokeWidth(2);
        model.setStroke(Color.BLACK);
        quantity++;
    }
    public Rectangle getModel(){
        return  model;
    }
}
