package org.example;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Math.abs;

public class Customer {
    static final int appearX=750;
    static final int appearY=25;
    private int movementSpeed;
    private int amountMoney;
    private boolean needHelp;
    private int purchases;
    private int validQueue;
    private Circle model;
    public Customer(){
        movementSpeed=(int)(Math.random()*1000)+400;
        model=new Circle();
        model.setCenterX(appearX);
        model.setCenterY(appearY);
        model.setRadius(15);
        model.setFill(Color.WHITE);
        model.setStrokeWidth(5);
        model.setStroke(Color.BLACK);
    }
    public Circle getModel(){
        return  model;
    }
    public int getValidQueue(){
        return validQueue;
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
                        if (y < model.getCenterY()) {
                            model.setCenterY(model.getCenterY() - 1);
                        }
                        if (y > model.getCenterY()) {
                            model.setCenterY(model.getCenterY() + 1);
                        }

                        if (y == model.getCenterY()) {
                            timer.cancel();
                        }
            }
        }, 0,10);
    }
    public void angry(){
        model.setFill(Color.RED);
    }
    public void updateCustomer(GraphicsContext gc){
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2);
    }

}
