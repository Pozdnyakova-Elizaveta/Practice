package org.example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Modeling {
    private Stage stage;
    private int numberCashier;
    private int numberCustomer;
    private int numberShelf;
    private int numberConsultant;
    public Modeling(int numberCashier, int numberCustomer,int numberShelf, int numberConsultant){
        this.numberCashier=numberCashier;
        this.numberShelf=numberShelf;
        this.numberConsultant=numberConsultant;
        this.numberCustomer=numberCustomer;
        stage=new Stage();
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
    }
    public void change(){
        Customer c =new Customer();
        Line wallLeft=new Line(0.0,50.0,700.0,50.0);
        Line wallRight=new Line(800.0,50.0,1200.0,50.0);
        wallLeft.setStrokeWidth(2);
        wallRight.setStrokeWidth(2);
        Polyline wallStockRight=new Polyline();
        wallStockRight.getPoints().addAll(new Double[]{
                175.0, 450.0,
                250.0, 450.0,
                250.0, 700.0 });
        wallStockRight.setFill(Color.WHITE);
        wallStockRight.setStrokeWidth(2);
        wallStockRight.setStroke(Color.BLACK);
        Line wallStockLeft=new Line(0.0,450.0,75.0,450.0);
        wallStockLeft.setStrokeWidth(2);
        Group group = new Group(c.getModel(), wallLeft,wallRight,wallStockRight,wallStockLeft);
        //Consultant[] co=new Consultant[5];
        //Cashier[] ca= new Cashier[5];
        Shelf[] s= new Shelf[8];
        for (int i=0;i!=8;i++) {
            //co[i]=new Consultant();
            //ca[i]=new Cashier();
            s[i]=new Shelf();
            //group.getChildren().add(co[i].getModel());
            //group.getChildren().add(ca[i].getModel());
            group.getChildren().add(s[i].getModel());

        }
        Rectangle cashRegister = new Rectangle(65,100,100*Cashier.quantity,40);
        cashRegister.setFill(Color.BROWN);
        cashRegister.setStrokeWidth(2);
        cashRegister.setStroke(Color.BLACK);
        group.getChildren().add(cashRegister);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }
}
