package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Shelf {
    static String[] type=new String[]{"Хлеб","Молочные","Бакалея","Овощи","Фрукты"};
    private String typeShelf;
    private int numberGoods;
    private ArrayList<Integer> price;
    static int quantity;
    static final int firstLine=470;
    static final int secondLine=270;
    private Rectangle model;
    private Text text;
    public Shelf(){
        if (quantity<5) typeShelf=type[quantity];
        else typeShelf=type[(int)(Math.random()*4)];
        //numberGoods=(int)(Math.random()*10);
        numberGoods=0;
        //for (int i=0;i!=numberGoods;i++)
            //price.add((int)(Math.random()*19)+1);
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
