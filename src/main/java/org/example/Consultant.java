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
    private boolean free;
    private int movementSpeed;
    static final int appearY=75;
    private Circle model;
    public Consultant(){
        movementSpeed=(int)(Math.random()*25)+5;
        free=true;
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
                if (x < model.getCenterX()) {
                    model.setCenterX(model.getCenterX() - 1);
                }
                if (x > model.getCenterX()) {
                    model.setCenterX(model.getCenterX() + 1);
                }

                if (x == model.getCenterX()) {
                    timer.cancel();
                }
            }
        }, 0,movementSpeed);
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
        }, 0,movementSpeed);
    }
    public void updateConsultant(GraphicsContext gc){
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2);
    }
   public boolean getFree(){
        return free;
   }
   public void displayGoods(Shelf shelf){
       if (Shelf.quantity<5 )  movementY(Shelf.firstLine-60);
       if (Shelf.quantity>=5 ) movementY(Shelf.secondLine-60);
       Timer t =new Timer();
       t.schedule(new TimerTask() {
           boolean pr=false;
           boolean onsklad=false;
           long time;
           @Override
           public void run() {
               if (shelf.getNumberGoods()==10){
                   shelf.updateText();
                   t.cancel();
               }
               if ((Shelf.quantity<5 && getModel().getCenterY()==Shelf.firstLine-60)||(Shelf.quantity>=5 && getModel().getCenterY()==Shelf.secondLine-60))
                   movementX(120);
               if (model.getCenterX()==120 && !onsklad) {
                   model.setCenterY(550);
               }
               if (model.getCenterY()==550 && model.getCenterX()==120){
                   onsklad=true;
                   if (!pr) {
                       time = System.currentTimeMillis();
                       pr=true;
                   }
                   else {
                       while (System.currentTimeMillis() - time < 3000) {}
                       wayShelf(shelf);
                   }
               }
           }
       },0,100);
   }
   public void wayShelf(Shelf shelf){
       if (Shelf.quantity<5)  movementY(Shelf.firstLine-60);
       if (Shelf.quantity>=5 ) movementY(Shelf.secondLine-60);
       Timer t =new Timer();
       t.schedule(new TimerTask() {
           @Override
           public void run() {
               if ((Shelf.quantity < 5 && model.getCenterY() == Shelf.firstLine - 60) || (Shelf.quantity >= 5 && model.getCenterY() == Shelf.secondLine - 60))
                   movementX((int) (shelf.getModel().getX() + 100));
               if (model.getCenterX() == shelf.getModel().getX() + 100) {
                   shelf.setNumberGoods(10);
                   t.cancel();
               }
           }
       },0,10);
   }
}
