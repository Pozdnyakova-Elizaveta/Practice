package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Customer extends  MovingModel{
    static final int appearX=750;
    static final int appearY=25;
    private int amountMoney;
    private int totalSpend;
    private boolean needHelp;
    private int numCheckout;
    private int numQueue;
    private int filling;
    private int purchases;
    private ArrayList<String> productList;
    private int validQueue;
    public Customer(){
        super(25,appearX,appearY,Color.BLACK);
        totalSpend=0;
        filling=0;
        needHelp=false;
        validQueue=(int)(Math.random()*3)+3;
        status="entry";
        amountMoney=(int)(Math.random()*200)+50;
        purchases=0;
        int sizeProductList=(int)(Math.random()*5+1);
        productList= new ArrayList<String>();
        for (int i=0;i!=sizeProductList;i++) productList.add(Shelf.TYPE[(int)(Math.random()*8)]);
    }
    public int getSizeProductList(){
        return productList.size();
    }
    public String getProductList(int i){
        return productList.get(i);
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
    public int getTotalSpend(){
        return totalSpend;
    }
    public void movementCheckout(Cashier c){
        if (model.getCenterY()!=Shelf.secondLine-40 && model.getCenterX()!=c.getModel().getCenterX()) {
            movementYY(Shelf.secondLine - 40);
        }
        if (model.getCenterY()==Shelf.secondLine-40 && model.getCenterX()!=c.getModel().getCenterX()) movementXX((int)c.getModel().getCenterX());
        if (model.getCenterX()==c.getModel().getCenterX()) {
            movementYY((int)c.getModel().getCenterY()+90+35*(c.getQueueBuyers()));
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
            if (i!=shelf.size() && shelf.get(i).getTypeShelf().equals(product)) {
                movementShelf(shelf.get(i), fl);
            }
            if (i==shelf.size()){
                colorChange(Color.CHOCOLATE);
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 1000) {}
                colorChange(Color.WHITE);
                productList.remove(0);
            }
            }

    }
    public void movementShelf(Shelf shelf,boolean fl){
        if (model.getCenterX()!=shelf.getModel().getX()+70){
            if (model.getCenterY()!=Shelf.secondLine-120 && model.getCenterX()<=shelf.getModel().getX()) movementYY(Shelf.secondLine-120);
            if (model.getCenterY()!=Shelf.secondLine-60 && model.getCenterX()>shelf.getModel().getX()) movementYY(Shelf.secondLine-60);
            if ((model.getCenterY()==Shelf.secondLine-120 && model.getCenterX()<=shelf.getModel().getX() ) || (model.getCenterY()==Shelf.secondLine-60 && model.getCenterX()>shelf.getModel().getX())) movementXX((int)shelf.getModel().getX()+70);
        }
        if (model.getCenterX()==shelf.getModel().getX()+70) movementYY((int)shelf.getModel().getY()+movementSpeed*5);
        if (model.getCenterX()==shelf.getModel().getX()+70 && model.getCenterY()==shelf.getModel().getY()+movementSpeed*5){
            if (shelf.getNumberGoods()==0){
                long start = System.currentTimeMillis();
                colorChange(Color.INDIGO);
                while (System.currentTimeMillis() - start < 4000) {}
                if (shelf.getNumberGoods() == 0) productList.remove(0);
                colorChange(Color.WHITE);
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
                if (needHelp==false && shelf.getNumberGoods()!=0 && shelf.getSizePrice()!=0) {
                    int min=shelf.getPrice(0);
                    int ind=0;
                    for (int i=0;i!=shelf.getSizePrice();i++){
                        if (shelf.getPrice(i)<min) {
                            min = shelf.getPrice(i);
                            ind=i;
                        }

                    }
                    if (min<amountMoney) {
                            long start = System.currentTimeMillis();
                            while (System.currentTimeMillis() - start < 500) {
                            }
                            shelf.setNumberGoods(shelf.getNumberGoods() - 1);
                            totalSpend = totalSpend + min;
                            amountMoney = amountMoney - min;
                            shelf.updateText();
                            purchases = purchases + 1;
                            if (shelf.getSizePrice() != 0 && shelf.getSizePrice() != ind) shelf.removePrice(ind);
                            if (productList.size() != 0 && productList.get(0).equals(shelf.getTypeShelf())) productList.remove(0);
                        }
                    else {
                            colorChange(Color.YELLOW);
                            Statistics.highPriceCustomer += 1;
                            long start = System.currentTimeMillis();
                            while (System.currentTimeMillis() - start < 500) {
                            }
                            if (productList.size() != 0 && productList.get(0).equals(shelf.getTypeShelf())) productList.remove(0);
                            colorChange(Color.WHITE);
                    }
                }
                if (needHelp==true) {
                    needHelp = false;
                    filling=0;
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

}
