package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cashier {
    static private int quantity;        //количество кассиров
    private int profit;         //прибыль
    static final private int APPEAR_Y=75;    //расположение по Y
    private int queueBuyers;        //очередь к кассиру
    private int serviceTime;        //время обслуживания покупателя
    private int numberBuyers;       //число обслуженных покупателей
    private Circle model;           //модель кассира
    public Cashier(){
        numberBuyers=0;
        queueBuyers=0;
        serviceTime=(int)(Math.random()*2000)+3000;
        model=new Circle();
        model.setCenterX(100+quantity*100);
        model.setCenterY(APPEAR_Y);
        model.setRadius(15);
        model.setFill(Color.WHITE);
        model.setStrokeWidth(2);
        model.setStroke(Color.BLACK);
        quantity++;
    }
    static public int getAppearY(){
        return  APPEAR_Y;
    }
    static public int getQuantity(){
        return quantity;
    }
    public Circle getModel(){
        return  model;
    }
    public int getNumberBuyers(){
        return numberBuyers;
    }
    public int getQueueBuyers(){
        return queueBuyers;
    }
    public void setQueueBuyers(int queueBuyers){
        this.queueBuyers=queueBuyers;
    }
    public int getProfit(){
        return profit;
    }
    public void service(Customer customer){     //обслуживание покупателя
        long start=System.currentTimeMillis();
        long time= customer.getPurchases()*serviceTime;
        //задержка по времени в зависимости от скорости кассира и количества товара
        while(System.currentTimeMillis()-start<time){}
        queueBuyers= queueBuyers-1; //уменьшение очереди к кассиру
        profit=profit+customer.getTotalSpend();     //увеличение прибыли
        numberBuyers++;             //увеличение числа обслуженных покупателей
        customer.setStatus("exit");                 //покупатель идет на выход
    }
}
