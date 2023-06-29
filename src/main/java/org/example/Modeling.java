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
import javafx.scene.shape.Circle;
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
    static int numberCashier;
    static int numberCustomer;
    static int numberShelf;
    static int numberConsultant;
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
            root.getChildren().add(s.get(i).getText());
        }
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            int count=0;
            @Override
            public void run() {
                if (count!=numberCustomer) {
                    c.add(new Customer());
                    cycle(c.get(c.size() - 1),gc);
                    count++;
                }
                if (count==numberCustomer)
                    if (c.size()==0) timer.cancel();
            }
        }, 0, 2000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cicleConsultant();
            }
        },0,100);
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Timer time=new Timer();
                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        canvasUpdate(gc);
                        if (c.size()==0) time.cancel();
                    }
                }, 0,10);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
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
            for (i = 0; i != co.size(); i++) co.get(i).updateConsultant(gc);
            for (i = 0; i != c.size(); i++) c.get(i).updateCustomer(gc);
    }
    public void cycle(Customer cc, GraphicsContext gc){
        cc.movementY(100);
        Timer time=new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean fl = false;
                boolean fl1 = false;
                int min_ch=ca.get(0).getQueueBuyers(), num=0;
                for (int i = 0; i != numberCashier; i++) {
                    if (ca.get(i).getQueueBuyers() < 7) fl = true;
                }
                if (fl == false) {
                    if (cc.getModel().getCenterY()==100) {
                        cc.angry();
                        cc.movementY(Customer.appearY);
                    }
                    if (cc.getModel().getCenterY()==Customer.appearY) c.remove(cc);
                }
                if (fl==true && cc.getModel().getCenterY() == 100) {
                    for (int i = 0; i != numberCashier; i++) {
                        if (ca.get(i).getQueueBuyers() < min_ch) {
                            num = i;
                            min_ch = ca.get(i).getQueueBuyers();
                        }
                    }
                    cc.movementCheckout(ca.get(num));
                }
                if (cc.num==0 && cc.getModel().getCenterX()==ca.get(num).getModel().getCenterX() && cc.getModel().getCenterY()==Cashier.appearY+90) {
                    fl1=ca.get(num).service(cc);
                    for (int i=0;i!=c.size(); i++){
                        if (c.get(i).getModel().getCenterX()==ca.get(num).getModel().getCenterX()){
                            if (c.get(i).num!=0) c.get(i).num=c.get(i).num-1;
                            c.get(i).movementY((int)Cashier.appearY+90+35*num);
                        }
                    }
                }
            }
        }, 0,100);

    }
    public void cicleConsultant(){
        int i, j=0;
        boolean work=false;
        for (i=0;i!=numberShelf;i++) {
            if (s.get(i).getNumberGoods() == 0) {
                while (j != numberConsultant && work != true) {
                    if (co.get(j).getFree()) {
                        co.get(j).displayGoods(s.get(i));
                        work = true;
                    }
                    j++;
                }
            }
            work=false;
        }
    }

}
