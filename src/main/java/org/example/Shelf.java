package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Shelf {
    //Типы товаров
    static final String[] TYPE=new String[]{"Хлеб","Молоко","Бакалея","Овощи","Фрукты", "Сладости","Напитки","Мясо"};
    private String typeShelf;   //тип товаров на конкретном стеллаже
    private int numberGoods;    //количество товаров
    private int filling;        //номер консультанта, работающего со стеллажем
    private ArrayList<Integer> price;   //цены на товары
    static int quantity;                //число стеллажей
    static final int firstLine=470;     //значение Y для расположения первой линии стеллажей
    static final int secondLine=270;    //значение Y для расположения второй линии стеллажей
    private Rectangle model;            //модель стеллажа
    private Text text;                  //подпись типа товаров и их количества
    public Shelf(){
        filling=0;
        typeShelf=TYPE[quantity];
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
    public void setPrice(int i){
        price.add(i);
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
    public void setNumberGoods(int numberGoods){
        this.numberGoods=numberGoods;
    }
}
