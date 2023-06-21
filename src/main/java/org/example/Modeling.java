package org.example;

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
        Stage stage=new Stage();
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
        stage.show();
    }
}
