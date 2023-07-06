package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

abstract class MovingModel {        //абстрактный класс для движущихся моделей
    protected Circle model;         //модель в виде круга
    protected int movementSpeed;    //скорость движения
    protected String status;        //статус для определения траектории движения
    public MovingModel(int coeffSpeed, int appearX, int appearY, Color color){
        movementSpeed=(int)(Math.random()*coeffSpeed)+2;
        model=new Circle();
        model.setCenterX(appearX);
        model.setCenterY(appearY);
        model.setRadius(15);
        model.setFill(Color.WHITE);
        model.setStrokeWidth(5);
        model.setStroke(color);
    }
    public Circle getModel(){
        return  model;
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
    public void movementY(int y){  //движение по Y
        if (y < model.getCenterY()) {
            model.setCenterY(model.getCenterY() - 1);
        }
        if (y > model.getCenterY()) {
            model.setCenterY(model.getCenterY() + 1);
        }
    }
    public void movementX(int x){   //движение по X
        if (x < model.getCenterX()) {
            model.setCenterX(model.getCenterX() - 1);
        }
        if (x > model.getCenterX()) {
            model.setCenterX(model.getCenterX() + 1);
        }
    }
    public abstract void update(GraphicsContext gc);    //обновление канвы для перерисовки модели
}

