package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
    private int filling;
    private int purchases;
    private ArrayList<String> productList;
    private String status;
    private int validQueue;
    private Circle model;
    public Customer(){
        filling=0;
        needHelp=false;
        validQueue=(int)(Math.random()*3)+3;
        status="entry";
        amountMoney=(int)(Math.random()*100)+10;
        purchases=0;
        movementSpeed=(int)(Math.random()*25)+2;
        int sizeProductList=(int)(Math.random()*5+1);
        productList= new ArrayList<String>();
        for (int i=0;i!=sizeProductList;i++) productList.add(Shelf.type[(int)(Math.random()*8)]);
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
    public boolean getNeedHelp(){
        return needHelp;
    }
    public int getFilling(){
        return filling;
    }
    public void setFilling(int filling){
        this.filling=filling;
    }
    public void setNeedHelp(boolean needHelp){
        this.needHelp=needHelp;
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
        if (model.getCenterY()!=Shelf.secondLine-60 && model.getCenterX()!=c.getModel().getCenterX()) {
            movmentYY(Shelf.secondLine - 60);
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
    public void cycleProduct(ArrayList<Shelf> shelf){
        boolean fl=false;
        if (productList.size()==0) {
            if (purchases==0) {
                status = "exit";
                Statistics.exitCustomer+=1;
            }
            else status="to checkout";
        }
        else {
            String product = productList.get(0);
            int i=0;
            while (i!=shelf.size() && !shelf.get(i).getTypeShelf().equals(product))i++;
            if (i==shelf.size()) productList.remove(0);
            else if (shelf.get(i).getTypeShelf().equals(product)) {
                movementShelf(shelf.get(i), fl);
                if (fl) {
                    productList.remove(0);
                }
            }
            }

    }
    public void movementShelf(Shelf shelf, boolean fl){
        if (model.getCenterX()!=shelf.getModel().getX()+70){
            if (model.getCenterY()!=Shelf.secondLine-60) movmentYY(Shelf.secondLine-60);
            if (model.getCenterY()==Shelf.secondLine-60) movmentXX((int)shelf.getModel().getX()+70);
        }
        if (model.getCenterX()==shelf.getModel().getX()+70) movmentYY((int)shelf.getModel().getY()+50);
        if (model.getCenterX()==shelf.getModel().getX()+70 && model.getCenterY()==shelf.getModel().getY()+50){
            if (shelf.getNumberGoods()==0){
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 2000) {}
                    if (shelf.getNumberGoods() == 0) fl = true;
            }
            if (shelf.getNumberGoods()>0){
                int random=(int)(Math.random()*3);
                if (random==0) {
                    needHelp = true;
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 5000) {}
                    if (needHelp==true) {
                        productList.remove(0);
                    }
                }
                if (needHelp==false && shelf.getNumberGoods()!=0) {
                    //int min;
                    //for (int i=0;i!=shelf.getNumberGoods();i++){

                    //}
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 500) {
                    }
                    shelf.setNumberGoods(shelf.getNumberGoods() - 1);
                    shelf.updateText();
                    purchases = purchases + 1;
                    if (productList.size()!=0) productList.remove(0);
                }
                if (needHelp==true) {
                    needHelp = false;
                    Statistics.notWaitingCustomer+=1;
                }
            }
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
        if (status=="product" && productList.size()!=0) {
            gc.setFill(Color.BLACK);
            if (needHelp==false)gc.fillText(Character.toString(productList.get(0).charAt(0)), model.getCenterX()-4, model.getCenterY()+2);
            else gc.fillText("?", model.getCenterX()-4, model.getCenterY()+2);
        }
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
