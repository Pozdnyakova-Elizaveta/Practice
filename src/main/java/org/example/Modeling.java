package org.example;

import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Modeling {
    private Stage stage;
    private int numberCashier;
    private int numberCustomer;
    private int numberShelf;
    private int numberConsultant;
    private ArrayList<Consultant> co;
    private ArrayList<Cashier> ca;
    private ArrayList<Shelf> s;
    private ArrayList<Customer> c;
    public Modeling(int numberCashier, int numberCustomer,int numberShelf, int numberConsultant){
        this.numberCashier=numberCashier;
        this.numberShelf=numberShelf;
        this.numberConsultant=numberConsultant;
        this.numberCustomer=numberCustomer;
        co=new ArrayList<Consultant>();
        ca=new ArrayList<Cashier>();
        s= new ArrayList<Shelf>();
        c = new ArrayList<Customer>();
        stage=new Stage();
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
    }
    public void change(){
        Pane root = new Pane();
        Canvas canvas = new Canvas(1200, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int i;
        for (i=0;i!=numberConsultant;i++){
            co.add(new Consultant());
        }
        for (i=0;i!=numberCashier;i++){
            ca.add(new Cashier());
            root.getChildren().add(ca.get(i).getModel());
        }
        for (i=0;i!=numberShelf;i++) {
            s.add(new Shelf());
            root.getChildren().add(s.get(i).getModel());
        }
        for (i=0;i!=numberCustomer;i++) {
            c.add(new Customer());
        }
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvasUpdate(gc);
                if (c.size()==0) timer.cancel();
            }
        }, 0,10);
        c.get(0).movementY(400,gc);
        co.get(0).movementX(100,gc);
        root.getChildren().add(canvas);
        wallPainting(root);
        Scene scene = new Scene(root);
        Rectangle cashRegister = new Rectangle(65,100,100*Cashier.quantity,40);
        cashRegister.setFill(Color.BROWN);
        cashRegister.setStrokeWidth(2);
        cashRegister.setStroke(Color.BLACK);
        root.getChildren().add(cashRegister);
        stage.setScene(scene);
        stage.show();
    }
    public void wallPainting(Pane root){
        Line wallLeft=new Line(0.0,50.0,700.0,50.0);
        Line wallRight=new Line(800.0,50.0,1200.0,50.0);
        wallLeft.setStrokeWidth(2);
        wallRight.setStrokeWidth(2);
        Polyline wallStockRight=new Polyline();
        wallStockRight.getPoints().addAll(new Double[]{
                175.0, 450.0,
                250.0, 450.0,
                250.0, 700.0 });
        wallStockRight.setFill(Color.WHITESMOKE);
        wallStockRight.setStrokeWidth(2);
        wallStockRight.setStroke(Color.BLACK);
        Line wallStockLeft=new Line(0.0,450.0,75.0,450.0);
        wallStockLeft.setStrokeWidth(2);
        root.getChildren().addAll(wallLeft,wallRight,wallStockRight,wallStockLeft);
    }
    public void canvasUpdate(GraphicsContext gc){
        int i;
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (i=0;i!=co.size();i++) {
            gc.setStroke(co.get(i).getModel().getStroke());
            gc.setLineWidth(co.get(i).getModel().getStrokeWidth());
            gc.setFill(co.get(i).getModel().getFill()); // установка цвета заливки
            gc.strokeOval(co.get(i).getModel().getCenterX() - co.get(i).getModel().getRadius(), co.get(i).getModel().getCenterY() - co.get(i).getModel().getRadius(), co.get(i).getModel().getRadius() * 2, co.get(i).getModel().getRadius() * 2); // рисование круга на Canvas
            gc.fillOval(co.get(i).getModel().getCenterX() -co.get(i).getModel().getRadius(), co.get(i).getModel().getCenterY() - co.get(i).getModel().getRadius(), co.get(i).getModel().getRadius() * 2, co.get(i).getModel().getRadius() * 2);
        }
        for (i=0;i!=c.size();i++){
            gc.setStroke(c.get(i).getModel().getStroke());
            gc.setLineWidth(c.get(i).getModel().getStrokeWidth());
            gc.setFill(c.get(i).getModel().getFill()); // установка цвета заливки
            gc.strokeOval(c.get(i).getModel().getCenterX() - c.get(i).getModel().getRadius(), c.get(i).getModel().getCenterY() - c.get(i).getModel().getRadius(), c.get(i).getModel().getRadius() * 2, c.get(i).getModel().getRadius() * 2); // рисование круга на Canvas
            gc.fillOval(c.get(i).getModel().getCenterX() -c.get(i).getModel().getRadius(), c.get(i).getModel().getCenterY() - c.get(i).getModel().getRadius(), c.get(i).getModel().getRadius() * 2, c.get(i).getModel().getRadius() * 2);
        }

    }
}
