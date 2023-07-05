package org.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
        Statistics statistics=new Statistics();
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
                    cicleCustomer(c.get(c.size() - 1));
                    count++;
                }
                if (count==numberCustomer)
                    if (c.size()==0) {
                        Platform.runLater(() -> {
                                    stage.hide();
                                    int[] profit=new int [numberCashier];
                                    for (int i=0; i!=numberCashier;i++) profit[i]=ca.get(i).getProfit();
                                    statistics.display(profit);
                                });
                        timer.cancel();
                    }
            }
        }, 0, 3000);
        for (i=0; i!=numberConsultant;i++) cicleConsultant(co.get(i));
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
                        if (c.size()==0) time.cancel();
                        canvasUpdate(gc);
                    }
                }, 0,30);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        stage.setOnCloseRequest(e -> {
            Platform.exit(); // Закрытие Javafx
            System.exit(0); // Завершение программы
        });
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
    public void cicleCustomer(Customer cu){
        Timer t=new Timer();
        t.schedule(new TimerTask() {
            Color color;
            boolean dvih=false;
            @Override
            public void  run(){
                switch (cu.getStatus()){
                    case "entry": {
                        if (cu.getModel().getCenterY()!=100) cu.movmentYY(100);
                        if (cu.getModel().getCenterY()==100){
                            boolean fl=false;
                            for (int i = 0; i != numberCashier; i++) {
                                if (ca.get(i).getQueueBuyers() < cu.getValidQueue()) fl = true;
                            }
                            if (fl==false){
                                color=Color.RED;
                                cu.setStatus("exit");
                                Statistics.exitCustomer+=1;
                            }
                            if (fl==true) cu.setStatus("product");
                        }
                        break;
                    }
                    case "product": {
                        cu.cycleProduct(s);
                        if (cu.getStatus()=="exit") color=Color.BLUE;
                    }
                    break;
                    case "to checkout":{
                        int min_ch=ca.get(numberCashier-1).getQueueBuyers();
                        int num=numberCashier-1;
                        for (int i = numberCashier-1; i >=0; i--) {
                            if (ca.get(i).getQueueBuyers() < min_ch) {
                                num = i;
                                min_ch = ca.get(i).getQueueBuyers();
                            }
                        }
                        cu.movementCheckout(ca.get(num));
                        cu.setNumCheckout(num);
                        break;
                    }
                    case "queue":{
                        if (cu.getNumQueue()==0) {
                            ca.get(cu.getNumCheckout()).service(cu);
                            color = Color.GREEN;
                            if (ca.get(cu.getNumCheckout()).getQueueBuyers()!=0) dvih=true;
                        }
                        break;
                    }
                    case "exit":{
                        if (color==Color.GREEN){
                            if (cu.getModel().getCenterX()!=Customer.appearX && cu.getModel().getCenterX()!=ca.get(cu.getNumCheckout()).getModel().getCenterX()+30 && (cu.getModel().getCenterY()==Cashier.appearY+90)) cu.movmentXX((int)ca.get(cu.getNumCheckout()).getModel().getCenterX()+30);
                        }
                        cu.colorChange(color);
                        if (cu.getModel().getCenterY()!=Customer.appearY && cu.getModel().getCenterX()!=Customer.appearX) cu.movmentYY(Shelf.secondLine-60);
                        if (cu.getModel().getCenterY()==Shelf.secondLine-60 && cu.getModel().getCenterX()!=Customer.appearX) cu.movmentXX(Customer.appearX);
                        if (cu.getModel().getCenterY()!=Customer.appearY && cu.getModel().getCenterX()==Customer.appearX) cu.movmentYY(Customer.appearY);
                        if (cu.getModel().getCenterY()==Customer.appearY) {
                            c.remove(cu);
                        }
                        break;
                    }
                }
                if (dvih){
                    for (int i=0;i!=c.size();i++) {
                        if (c.get(i).getStatus() == "queue" && c.get(i).getModel().getCenterX() == ca.get(cu.getNumCheckout()).getModel().getCenterX() && c.get(i).getModel().getCenterY() != Cashier.appearY + 90 + 35 * (c.get(i).getNumQueue() - 1))
                            if (c.get(i).getModel().getCenterY() >= Cashier.appearY + 90) c.get(i).movmentYY(Cashier.appearY + 90 + 35 * (c.get(i).getNumQueue() - 1));
                        if (c.get(i).getStatus() == "queue" && c.get(i).getModel().getCenterX() == ca.get(cu.getNumCheckout()).getModel().getCenterX() && c.get(i).getModel().getCenterY() == Cashier.appearY + 90 + 35 * (c.get(i).getNumQueue() - 1)) {
                            c.get(i).setNumQueue(c.get(i).getNumQueue() - 1);
                            if (c.get(i).getNumQueue()==0) dvih=false;
                        }
                    }
                }
                if (c.size()==0) t.cancel();
            }
        },0, cu.getMovementSpeed());
    }
    public void cicleConsultant(Consultant con){
        Timer t=new Timer();
        int i;
            t.schedule(new TimerTask() {
                @Override
                public void  run(){
                    search(con);
                    switch (con.getStatus()){
                        case "wait": {
                            break;
                        }
                        case "on stock":
                            con.onStock();
                            break;
                        case "help":{
                            int i=0;
                            while (i!=c.size() && c.get(i).getFilling()!=(co.indexOf(con)+1)) i++;
                            if (i!=c.size()) {
                                con.help(c.get(i));
                                if (c.get(i).getNeedHelp()==false) {
                                    c.get(i).setFilling(0);
                                    con.setStatus("place");
                                }
                            }
                            else con.setStatus("place");
                            break;
                        }
                        case "to shelf": {
                            int i=0;
                            while (i!=numberShelf && s.get(i).getFilling()!=(co.indexOf(con)+1)) i++;
                            if (i!=numberShelf) con.toShelf(s.get(i));
                            if (i==numberShelf || s.get(i).getNumberGoods()!=0){
                                if (i!=numberShelf && s.get(i).getNumberGoods()!=0) s.get(i).setFilling(0);
                                con.setStatus("place");
                            }
                            break;
                        }
                        case "place": {
                            con.place(co.indexOf(con));
                            break;
                        }
                    }
                }
            },0, con.getMovementSpeed());

    }
    public void search(Consultant con){
        int i=0;
        while (i!=numberShelf && (con.getStatus()=="wait" || con.getStatus()=="place")){
            if (s.get(i).getNumberGoods()==0 && s.get(i).getFilling()==0){
                con.setStatus("on stock");
                s.get(i).setFilling(co.indexOf(con)+1);
            }
            i++;
        }
        i=0;
        while (i!=c.size() && (con.getStatus()=="wait" || con.getStatus()=="place")) {
            if (c.get(i).getNeedHelp()==true && c.get(i).getFilling()==0){
                con.setStatus("help");
                c.get(i).setFilling(co.indexOf(con)+1);
            }
            i++;
        }
    }

}