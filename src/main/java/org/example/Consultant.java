package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Consultant extends MovingModel {
    static int quantity;
    static final int appearY=75;
    public Consultant(){
        super(10,850+quantity*50,appearY,Color.DARKRED);
        status="wait";
        quantity++;
    }

    public void updateConsultant(GraphicsContext gc){
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2);
    }
   public void onStock(){
        if (model.getCenterX()==120){
            movementYY(550);
        }
        if (model.getCenterX()==120 && model.getCenterY()==550) {
            long start=System.currentTimeMillis();
            while (System.currentTimeMillis()-start<1000){}
            status = "to shelf";
        }
        if (model.getCenterY()!=Shelf.secondLine-30 && model.getCenterX()!=120) {
            movementYY(Shelf.secondLine - 30);
       }
       if (model.getCenterY() == Shelf.secondLine - 30) movementXX(120);
   }
   public void toShelf(Shelf shelf){
       if (model.getCenterY()!=Shelf.firstLine-30 && model.getCenterX()!=shelf.getModel().getX()+70) {
           movementYY(Shelf.firstLine-30);
       }
       if (model.getCenterY()==Shelf.firstLine-30) movementXX((int)shelf.getModel().getX()+70);
       if (model.getCenterX()==shelf.getModel().getX()+70) movementYY((int)shelf.getModel().getY()+80);
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
           movementYY(Shelf.secondLine - 30);
       }
       if (model.getCenterY()==Shelf.secondLine-30 && model.getCenterX()!=c.getModel().getCenterX()+40) movementXX((int)c.getModel().getCenterX()+40);
       if (model.getCenterX()==c.getModel().getCenterX()+40) movementYY((int)c.getModel().getCenterY());
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
       if (model.getCenterX()==850+index*50) movementYY(appearY);
       if (model.getCenterY()!=Shelf.secondLine-30&& model.getCenterX()!=850+index*50) {
           movementYY(Shelf.secondLine - 30);
       }
       if (model.getCenterY() == Shelf.secondLine - 30) movementXX(850+index*50);
   }
}
