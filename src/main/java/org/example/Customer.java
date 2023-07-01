package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Customer {
    static final int appearX=750;
    static final int appearY=25;
    private int movementSpeed;
    private int amountMoney;
    private boolean needHelp;
    private int numCheckout;
    private int numQueue;
    private int purchases;
    private ArrayList<String> productList;
    private String status;
    private int validQueue;
    private Circle model;
    public Customer(){
        status="entry";
        amountMoney=(int)(Math.random()*100)+10;
        purchases=(int)(Math.random()*5+1);
        movementSpeed=(int)(Math.random()*25)+2;
        int sizeProductList=(int)(Math.random()*5+1);
        productList= new ArrayList<String>();
        for (int i=0;i!=sizeProductList;i++) productList.add(Shelf.type[(int)(Math.random()*5)]);
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
    public int getPurchases(){
        return purchases;
    }
    public int getNumCheckout(){
        return numCheckout;
    }
    public void setNumCheckout(int num){
        this.numCheckout=num;
    }
    public int getNumQueue(){
        return numQueue;
    }
    public void setNumQueue(int num){
        this.numQueue=num;
    }
    public int getAmountMoney(){
        return amountMoney;
    }
    public void movementCheckout(Cashier c){
        if (model.getCenterY()!=Shelf.firstLine-60 && model.getCenterY()!=Shelf.secondLine-60 && model.getCenterX()!=c.getModel().getCenterX()) {
            if (Shelf.quantity < 5) movmentYY(Shelf.firstLine - 60);
            if (Shelf.quantity >= 5) movmentYY(Shelf.secondLine - 60);
        }
        if (model.getCenterY()==Shelf.secondLine-60 && model.getCenterX()!=c.getModel().getCenterX()) movmentXX((int)c.getModel().getCenterX());
        if (model.getCenterX()==c.getModel().getCenterX()) {
            movmentYY((int)c.getModel().getCenterY()+90+35*(c.getQueueBuyers()));
        }
        if (model.getCenterX()==c.getModel().getCenterX() && model.getCenterY()==c.getModel().getCenterY()+90+35*(c.getQueueBuyers())){
            numQueue=c.getQueueBuyers();
            c.setQueueBuyers(c.getQueueBuyers()+1);
            status="queue";
        }
    }
    public void colorChange(Color color){
        model.setFill(color);
    }
    public void updateCustomer(GraphicsContext gc){
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(), model.getRadius() * 2, model.getRadius() * 2);
    }
    public int getMovementSpeed(){
        return movementSpeed;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
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
