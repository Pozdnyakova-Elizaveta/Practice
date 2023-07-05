package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Timer;
import java.util.TimerTask;


public class Consultant {
    static int quantity;
    private String status;
    private int movementSpeed;
    static final int appearY=75;
    private Circle model;
    public Consultant(){
        movementSpeed=(int)(Math.random()*10)+1;
        status="wait";
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
    public int getMovementSpeed(){
        return movementSpeed;
    }

    public void updateConsultant(GraphicsContext gc){
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2);
    }
   public String getStatus(){
        return status;
   }
   public void setStatus(String s){
        this.status=s;
   }
   public void onStock(){
        if (model.getCenterX()==120){
            movmentYY(550);
        }
        if (model.getCenterX()==120 && model.getCenterY()==550) {
            long start=System.currentTimeMillis();
            while (System.currentTimeMillis()-start<1000){}
            status = "to shelf";
        }
        if (model.getCenterY()!=Shelf.secondLine-30 && model.getCenterX()!=120) {
            movmentYY(Shelf.secondLine - 30);
       }
       if (model.getCenterY() == Shelf.secondLine - 30) movmentXX(120);
   }
   public void toShelf(Shelf shelf){
       if (model.getCenterY()!=Shelf.firstLine-30 && model.getCenterX()!=shelf.getModel().getX()+70) {
           movmentYY(Shelf.firstLine-30);
       }
       if (model.getCenterY()==Shelf.firstLine-30) movmentXX((int)shelf.getModel().getX()+70);
       if (model.getCenterX()==shelf.getModel().getX()+70) movmentYY((int)shelf.getModel().getY()+80);
       if (model.getCenterX()==shelf.getModel().getX()+70 && model.getCenterY()==shelf.getModel().getY()+80) {
           long start=System.currentTimeMillis();
           while (System.currentTimeMillis()-start<1000){}
           shelf.setNumberGoods(10);
           for (int i=0;i!=shelf.getNumberGoods();i++){
               shelf.setPrice((int)(Math.random()*71)+30);
           }
           shelf.updateText();
           shelf.setFilling(0);
           status = "place";
       }
   }
   public void help(Customer c){
       if (model.getCenterY()!=Shelf.secondLine-30&& model.getCenterX()!=c.getModel().getCenterX()+40) {
           movmentYY(Shelf.secondLine - 30);
       }
       if (model.getCenterY()==Shelf.secondLine-30 && model.getCenterX()!=c.getModel().getCenterX()+40) movmentXX((int)c.getModel().getCenterX()+40);
       if (model.getCenterX()==c.getModel().getCenterX()+40) movmentYY((int)c.getModel().getCenterY());
       if (model.getCenterY()==c.getModel().getCenterY() && model.getCenterX()==c.getModel().getCenterX()+40){
           long start=System.currentTimeMillis();
           while (System.currentTimeMillis()-start<1000){}
           c.setNeedHelp(false);
           c.setFilling(0);
           status="place";
       }
   }
   public void place(int index){
       if (model.getCenterY()==appearY && model.getCenterX()==850+index*50) {
           status = "wait";
       }
       if (model.getCenterX()==850+index*50) movmentYY(appearY);
       if (model.getCenterY()!=Shelf.secondLine-30&& model.getCenterX()!=850+index*50) {
           movmentYY(Shelf.secondLine - 30);
       }
       if (model.getCenterY() == Shelf.secondLine - 30) movmentXX(850+index*50);
   }
   public void movmentYY(int y){
       if (y < model.getCenterY()) {
           model.setCenterY(model.getCenterY() - 1);
       }
       if (y > model.getCenterY()) {
           model.setCenterY(model.getCenterY() + 1);
       }
   }
    public void movmentXX(int x){
        if (x < model.getCenterX()) {
            model.setCenterX(model.getCenterX() - 1);
        }
        if (x > model.getCenterX()) {
            model.setCenterX(model.getCenterX() + 1);
        }
    }
}
