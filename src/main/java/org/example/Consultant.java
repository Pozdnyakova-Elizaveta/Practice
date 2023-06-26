package org.example;

import javafx.animation.PathTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

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
        model.setStrokeWidth(5);
        model.setStroke(Color.DARKRED);
        quantity++;
    }
    public Circle getModel(){
        return  model;
    }
    public void movementX(int x){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (x>model.getCenterX()){
                    model.setCenterX(model.getCenterX()+1);
                }
                if (x<model.getCenterX()){
                    model.setCenterX(model.getCenterX()-1);
                }
                if (x==model.getCenterX()) timer.cancel();
            }
        }, 0,10);

    }
    public void movementY(int y){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (y<model.getCenterY()){
                    model.setCenterY(model.getCenterY()-1);
                }
                if (y>model.getCenterY()){
                    model.setCenterY(model.getCenterY()+1);
                }
                if (y== model.getCenterY()) timer.cancel();
            }
        }, 0,10);
    }
    public void updateConsultant(GraphicsContext gc){
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2);
    }
    public void movement(int y) {
        if (y < model.getCenterY()) {
            model.setCenterY(model.getCenterY() - 1);
        }
        if (y > model.getCenterY()) {
            model.setCenterY(model.getCenterY() + 1);
        }
    }
}
