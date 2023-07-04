package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Shelf {
    static String[] type=new String[]{"Хлеб","Молочные","Бакалея","Овощи","Фрукты", "Сладости","Напитки","Мясо"};
    private String typeShelf;
    private int numberGoods;
    private int filling;
    private ArrayList<Integer> price;
    static int quantity;
    static final int firstLine=470;
    static final int secondLine=270;
    private Rectangle model;
    private Text text;
    public Shelf(){
        filling=0;
        typeShelf=type[quantity];
        numberGoods=(int)(Math.random()*10);
        price=new ArrayList<Integer>();
        for (int i=0;i!=numberGoods;i++){
            price.add((int)(Math.random()*71)+30);
        }
        text = new Text (typeShelf+" "+numberGoods+"/10");
        text.setRotate(-90);
        text.setFont(Font.font(14));
        model=new Rectangle();
        if (quantity<4) {
            model.setY(firstLine);
            model.setX(quantity*200+400);
            text.setX(model.getX()-20);
            text.setY(model.getY()+100);
        }
        else {
            model.setY(secondLine);
            model.setX((quantity-4)*200+400);
            text.setX(model.getX()-20);
            text.setY(model.getY()+100);
        }
        model.setWidth(50);
        model.setHeight(150);
        model.setFill(Color.ROSYBROWN);
        model.setStrokeWidth(2);
        model.setStroke(Color.BLACK);
        quantity++;
    }
    public int getPrice(int i){
        return price.get(i);
    }
    public void removePrice(int i){
        price.remove(i);
    }
    public int getSizePrice(){
        return price.size();
    }
    public void setPrice(int n){
        price.add(n);
    }
    public int getFilling(){
        return filling;
    }
    public void setFilling(int filling){
        this.filling=filling;
    }
    public String getTypeShelf(){
        return typeShelf;
    }
    public Rectangle getModel(){
        return  model;
    }
    public Text getText(){
        return  text;
    }
    public void updateText(){
        text.setText(typeShelf+" "+numberGoods+"/10");
    }
    public int getNumberGoods(){
        return numberGoods;
    }
    public void setNumberGoods(int n){
        this.numberGoods=n;
    }
}
