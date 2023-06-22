package org.example;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import static java.lang.Math.abs;

public class Consultant {
    static int quantity;
    private int movementSpeed;
    static final int appearY=75;
    private Circle model;
    public Consultant(){
        movementSpeed=(int)(Math.random()*1000)+200;
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
    public PathTransition movementYX(double x, double y){
        Polyline path=new Polyline();
        path.getPoints().addAll(new Double[]{
                model.getCenterX(), model.getCenterY(),
                model.getCenterX(), y,
                x, y});
        int time=(int)((abs(model.getCenterX()-x)+abs(model.getCenterY()-y))/100*movementSpeed);
        PathTransition transitionYX = new PathTransition(Duration.millis(time),path);
        transitionYX.setNode(model);
        this.model.setCenterX(x);
        this.model.setCenterY(y);
        return transitionYX;
    }
    public PathTransition movementXY(double x, double y){
        Polyline path=new Polyline();
        path.getPoints().addAll(new Double[]{
                model.getCenterX(), model.getCenterY(),
                x, model.getCenterY(),
                x, y});
        int time=(int)((abs(model.getCenterX()-x)+abs(model.getCenterY()-y))/100*movementSpeed);
        PathTransition transitionXY = new PathTransition(Duration.millis(time),path);
        transitionXY.setNode(model);
        this.model.setCenterX(x);
        this.model.setCenterY(y);
        return transitionXY;
    }
}
