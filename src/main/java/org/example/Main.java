package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.Scene;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        Text title=new Text("Имитационная модель магазина");
        Text numberCashier=new Text("Выберите число кассиров");
        Text numberCustomer=new Text("Выберите число покупателей");
        Text numberShelf=new Text("Выберите количество полок с товарами");
        Text numberConsultant=new Text("Выберите число консультантов");
        numberCustomer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberCustomer.setLayoutX(80);
        numberCustomer.setLayoutY(160);
        numberCashier.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberCashier.setLayoutX(80);
        numberCashier.setLayoutY(200);
        numberShelf.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberShelf.setLayoutX(80);
        numberShelf.setLayoutY(240);
        numberConsultant.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberConsultant.setLayoutX(80);
        numberConsultant.setLayoutY(280);
        title.setLayoutY(70);
        title.setLayoutX(220);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        Button start = new Button("Запуск");
        start.setLayoutX(500);
        start.setLayoutY(500);
        start.setPrefSize(200,50);
        start.setOnAction(e -> {
            stage.hide();
            Modeling modeling=new Modeling(1,1,1,1);
        });
        Group group = new Group(title, numberCustomer, numberCashier, numberConsultant, numberShelf, start);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();
    }
}