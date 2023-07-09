package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Customer extends  MovingModel{ //класс покупателя
    static final private int APPEAR_X=750;           //координаты появления и удаления покупателя
    static final private int APPEAR_Y=25;
    private int amountMoney;                //общее количество денег
    private int totalSpend;                 //потраченная сумма денег
    private boolean needHelp;               //флаг, нужна ли помощь консультанта
    private int numCheckout;                //номер выбранной кассы
    private int numQueue;                   //номер в очереди на кассу
    private int filling;                    //номер консультанта, работающего со покупателем
    private int purchases;                  //количество взятых товаров
    private ArrayList<String> productList;  //список товаров
    private int validQueue;                 //допустимый для покупателя размер очереди на кассу
    public Customer(){
        super(25,APPEAR_X,APPEAR_Y,Color.BLACK);
        totalSpend=0;
        filling=0;
        needHelp=false;
        validQueue=(int)(Math.random()*3)+3;
        status="entry";
        amountMoney=(int)(Math.random()*200)+50;
        purchases=0;
        int sizeProductList=(int)(Math.random()*5+1);
        productList= new ArrayList<String>();
        for (int i=0;i!=sizeProductList;i++) productList.add(Shelf.getType((int)(Math.random()*8)));
    }
    static public int getAppearY(){
        return APPEAR_Y;
    }
    static public int getAppearX(){
        return APPEAR_X;
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
    public void setNumCheckout(int numCheckout){
        this.numCheckout=numCheckout;
    }
    public int getNumQueue(){
        return numQueue;
    }
    public void setNumQueue(int numQueue){
        this.numQueue=numQueue;
    }
    public int getAmountMoney(){
        return amountMoney;
    }
    public int getTotalSpend(){
        return totalSpend;
    }
    public void movementCheckout(Cashier c){        //движение по траектории на кассу
        if (model.getCenterY()!=Shelf.getSecondLine()-40 && model.getCenterX()!=c.getModel().getCenterX()) {
            movementY(Shelf.getSecondLine() - 40);
        }
        if (model.getCenterY()==Shelf.getSecondLine()-40 && model.getCenterX()!=c.getModel().getCenterX())
            movementX((int)c.getModel().getCenterX());
        if (model.getCenterX()==c.getModel().getCenterX()) {
            movementY((int)c.getModel().getCenterY()+90+35*(c.getQueueBuyers()));
        }
        if (model.getCenterX()==c.getModel().getCenterX() &&
                model.getCenterY()==c.getModel().getCenterY()+90+35*(c.getQueueBuyers())){
            //когда покупатель дошел до очереди
            numQueue=c.getQueueBuyers();    //записываем, какой по счету он в очереди
            c.setQueueBuyers(c.getQueueBuyers()+1); //увеличиваем очередь у кассира
            status="queue"; //статус "очередь"
        }
    }
    public void cycleProduct(ArrayList<Shelf> shelf){   //цикл поиска товара
        if (productList.size()==0) {    //если список товаров - пустой
            if (purchases==0) {         //и никакой товар не был взят - покупатель уходит
                status = "exit";
                Statistics.addExitCustomer();
            }
            else status="to checkout";  //иначе - статус "на кассу"
        }
        else {  //если список товаров - не пустой
            String product = productList.get(0);    //записываем первый товар
            int i=0;
            while (i!=shelf.size() && !shelf.get(i).getTypeShelf().equals(product))i++; //ищем его среди стеллажей
            if (i!=shelf.size() && shelf.get(i).getTypeShelf().equals(product)) {
                //если для типа товара есть соответствующий стеллаж
                movementShelf(shelf.get(i));    //движение к стеллажу
            }
            if (i==shelf.size()){   //если стеллажа с таким товаром нет
                colorChange(Color.CHOCOLATE);   //изменение цвета покупателя
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 1000) {} //задержка по времени
                colorChange(Color.WHITE);
                productList.remove(0);  //удаление этого товара из списка
            }
        }

    }
    public void movementShelf(Shelf shelf){ //движение к стеллажу
        if (model.getCenterX()!=shelf.getModel().getX()+70){
            if (model.getCenterY()!=Shelf.getSecondLine()-120 && model.getCenterX()<=shelf.getModel().getX())
                movementY(Shelf.getSecondLine()-120);
            if (model.getCenterY()!=Shelf.getSecondLine()-60 && model.getCenterX()>shelf.getModel().getX())
                movementY(Shelf.getSecondLine()-60);
            if ((model.getCenterY()==Shelf.getSecondLine()-120 && model.getCenterX()<=shelf.getModel().getX() ) ||
                    (model.getCenterY()==Shelf.getSecondLine()-60 && model.getCenterX()>shelf.getModel().getX()))
                movementX((int)shelf.getModel().getX()+70);
        }
        if (model.getCenterX()==shelf.getModel().getX()+70) movementY((int)shelf.getModel().getY()+movementSpeed*5);
        if (model.getCenterX()==shelf.getModel().getX()+70 && model.getCenterY()==shelf.getModel().getY()+movementSpeed*5)
        { //если покупатель подошел к стеллажу
            if (shelf.getNumberGoods()==0){ // стеллаж пустой
                long start = System.currentTimeMillis();
                colorChange(Color.INDIGO);  //изменение цвета покупателя
                while (System.currentTimeMillis() - start < 4000) {}    //задержка по времени
                if (shelf.getNumberGoods() == 0) productList.remove(0); //если товар не принесли - удаление его из списка
                Statistics.addNotWaitingCustomer();
                colorChange(Color.WHITE);
            }
            if (shelf.getNumberGoods()>0){  //стеллаж не пустой
                int random=(int)(Math.random()*4);  //случайно определяется, нужна ли помощь
                if (random==0) {
                    needHelp = true;    //помощь нужна
                    long start = System.currentTimeMillis();
                    while (System.currentTimeMillis() - start < 5000) {}    //ожидание консультанта
                    if (needHelp==true) {   //если консультант не подошел - удаление товара из списка
                        productList.remove(0);
                    }
                }
                if (needHelp==false && shelf.getSizePrice()!=0) {  //если помощь не нужна - выбор товара
                    productSelection(shelf);
                }
                if (needHelp==true) { //сброс флага помощи
                    needHelp = false;
                    filling=0;
                    Statistics.addNotWaitingCustomer();
                }
            }
        }
    }
    public void productSelection(Shelf shelf){  //выбор товара
        int min=shelf.getPrice(0);
        int index=0;
        for (int i=0;i!=shelf.getSizePrice();i++){  //поиск минимального по цене товара
            if (shelf.getPrice(i)<min) {
                min = shelf.getPrice(i);
                index=i;
            }
        }
        if (min<=amountMoney) {  //если минимальная цена меньше количества денег у покупателя
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 500) {  //задержка по времени
            }
            shelf.setNumberGoods(shelf.getNumberGoods() - 1);  //на стеллаже умешьшается число товаров
            totalSpend = totalSpend + min;  //потраченная сумма покупателя увеличивается на цену товара
            amountMoney = amountMoney - min;    //не потраченная - уменьшается на цену
            shelf.updateText();
            purchases = purchases + 1;  //увеличение числа взятых товаров у покупателя
            //удаление товара со стеллажа и из списка
            if (shelf.getSizePrice() != 0 && shelf.getSizePrice() != index) shelf.removePrice(index);
            if (productList.size() != 0 && productList.get(0).equals(shelf.getTypeShelf())) productList.remove(0);
        }
        else {  //если минимальная цена больше количества денег
            colorChange(Color.YELLOW);  //изменение цвета покупателя
            Statistics.addHighPriceCustomer();
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 500) {  //задержка по времени
            }
            //удаление товара из списка
            if (productList.size() != 0 && productList.get(0).equals(shelf.getTypeShelf())) productList.remove(0);
            colorChange(Color.WHITE);
        }
    }
    public void colorChange(Color color){   //изменение цвета внутренней части круга модели
        model.setFill(color);
    }
    public void update(GraphicsContext gc){ //обновление канвы для перерисовки покупателя
        gc.setStroke(model.getStroke());
        gc.setLineWidth(model.getStrokeWidth());
        gc.setFill(model.getFill()); // установка цвета заливки
        gc.strokeOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(),
                model.getRadius() * 2, model.getRadius() * 2); // рисование круга на Canvas
        gc.fillOval(model.getCenterX() - model.getRadius(), model.getCenterY() - model.getRadius(),
                model.getRadius() * 2, model.getRadius() * 2);
        if (status=="product search" && productList.size()!=0) {
            //если статус - "поиск товара" - вывод первой буквы типа товара
            gc.setFill(Color.BLACK);
            if (needHelp==false)gc.fillText(Character.toString(productList.get(0).charAt(0)), model.getCenterX()-4,
                    model.getCenterY()+2);
                //если нужна помощь - вывод знака вопроса
            else gc.fillText("?", model.getCenterX()-4, model.getCenterY()+2);
        }
    }

}
