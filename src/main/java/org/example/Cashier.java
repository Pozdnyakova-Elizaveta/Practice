package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Timer;
import java.util.TimerTask;

public class Cashier {
    static int quantity;
    private int profit;
    static final int appearY=75;
    private int queueBuyers;
    private int serviceTime;
    private Circle model;
    public Cashier(){
        queueBuyers=0;
        serviceTime=(int)(Math.random()*500)+400;
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
    public int getQueueBuyers(){
        return queueBuyers;
    }
    public void setQueueBuyers(int queueBuyers){
        this.queueBuyers=queueBuyers;
    }
    public void service(Customer customer){
        long start=System.currentTimeMillis();
        long time= customer.getPurchases()*serviceTime;
        while(System.currentTimeMillis()-start<time){}
        queueBuyers= queueBuyers-1;
        profit=profit+customer.getAmountMoney();
        customer.setStatus("exit");
    }
}
