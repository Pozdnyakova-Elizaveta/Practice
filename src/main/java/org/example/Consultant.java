package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Consultant extends MovingModel {   //класс консультанта
    static private int quantity;        //количество консультантов
    static final private int APPEAR_Y=75;    //появление консультанта по Y
    public Consultant(){
        super(10,850+quantity*50,APPEAR_Y,Color.DARKRED);
        status="wait";
        quantity++;
    }

    public void update(GraphicsContext gc){     //обновление канвы для перерисовки консультанта
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(),
                model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(),
                model.getRadius() * 2, model.getRadius() * 2);
    }
   public void onStock(){                  //движение по траектории к складу
        if (model.getCenterX()==120){
            movementY(550);
        }
        if (model.getCenterX()==120 && model.getCenterY()==550) {   //если консультант на складе
            long start=System.currentTimeMillis();
            while (System.currentTimeMillis()-start<1000){} //задержка по времени
            status = "to shelf";    //статус "к стеллажу"
        }
        if (model.getCenterY()!=Shelf.getSecondLine()-30 && model.getCenterX()!=120) {
            movementY(Shelf.getSecondLine() - 30);
       }
       if (model.getCenterY() == Shelf.getSecondLine() - 30) movementX(120);
   }
   public void toShelf(Shelf shelf){        //движение по траектории к стеллажу
       if (model.getCenterY()!=Shelf.getFirstLine()-30 && model.getCenterX()!=shelf.getModel().getX()+70) {
           movementY(Shelf.getFirstLine()-30);
       }
       if (model.getCenterY()==Shelf.getFirstLine()-30) movementX((int)shelf.getModel().getX()+70);
       if (model.getCenterX()==shelf.getModel().getX()+70) movementY((int)shelf.getModel().getY()+80);
       if (model.getCenterX()==shelf.getModel().getX()+70 && model.getCenterY()==shelf.getModel().getY()+80) {
           //когда консультант у стеллажа
           long start=System.currentTimeMillis();
           while (System.currentTimeMillis()-start<1000){}  //задержка по времени
           shelf.setNumberGoods(10);                        //добавление товаров и формирование цен
           for (int i=0;i!=shelf.getNumberGoods();i++){
               shelf.setPrice((int)(Math.random()*71)+30);
           }
           shelf.updateText();
           shelf.setFilling(0);
           status = "place appear"; //статус "место появления"
       }
   }
   public void help(Customer c){    //движение по траектории к покупателю, нуждающемуся в помощи
       if (model.getCenterY()!=Shelf.getSecondLine()-30&& model.getCenterX()!=c.getModel().getCenterX()+40) {
           movementY(Shelf.getSecondLine() - 30);
       }
       if (model.getCenterY()==Shelf.getSecondLine()-30 && model.getCenterX()!=c.getModel().getCenterX()+40)
           movementX((int)c.getModel().getCenterX()+40);
       if (model.getCenterX()==c.getModel().getCenterX()+40) movementY((int)c.getModel().getCenterY());
       if (model.getCenterY()==c.getModel().getCenterY() && model.getCenterX()==c.getModel().getCenterX()+40){
           //когда консультант подошел к покупателю
           long start=System.currentTimeMillis();
           while (System.currentTimeMillis()-start<1000){}  //задержка по времени
           c.setNeedHelp(false);        //изменение флага помощи
           c.setFilling(0);
           status="place appear";   //статус "место появления"
       }
   }
   public void placeAppear(int index){    //возвращение консультанта к месту ожидания работы
       if (model.getCenterY()==APPEAR_Y && model.getCenterX()==850+index*50) {
           status = "wait"; //статус "ожидание"
       }
       if (model.getCenterX()==850+index*50) movementY(APPEAR_Y);
       if (model.getCenterY()!=Shelf.getSecondLine()-30&& model.getCenterX()!=850+index*50) {
           movementY(Shelf.getSecondLine() - 30);
       }
       if (model.getCenterY() == Shelf.getSecondLine() - 30) movementX(850+index*50);
   }
}
