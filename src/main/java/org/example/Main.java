package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
        numberCustomer.setLayoutX(200);
        numberCustomer.setLayoutY(160);
        numberCashier.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberCashier.setLayoutX(200);
        numberCashier.setLayoutY(200);
        numberShelf.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberShelf.setLayoutX(200);
        numberShelf.setLayoutY(240);
        numberConsultant.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        numberConsultant.setLayoutX(200);
        numberConsultant.setLayoutY(280);
        title.setLayoutY(70);
        title.setLayoutX(220);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        Button start = new Button("Запуск");
        start.setLayoutX(500);
        start.setLayoutY(560);
        start.setPrefSize(200,50);
        Slider choiceCustomer=new Slider(1.0,100.0,5.0);
        choiceCustomer.setLayoutX(800);
        choiceCustomer.setLayoutY(140);
        choiceCustomer.setShowTickLabels(true);
        Label label = new Label(String.valueOf((int)(choiceCustomer.getValue())));
        label.setLayoutX(1000);
        label.setLayoutY(140);
        choiceCustomer.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.setText(String.valueOf(newValue.intValue()));
        });
        ObservableList<Integer> choiceConsultant = FXCollections.observableArrayList(0,1,2,3,4,5,6);
        ObservableList<Integer> choiceCashier = FXCollections.observableArrayList(1,2,3);
        ObservableList<Integer> choiceShelf = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
        ChoiceBox<Integer> choiceBoxConsultant = new ChoiceBox<Integer>(choiceConsultant);
        choiceBoxConsultant.setLayoutX(800);
        choiceBoxConsultant.setLayoutY(260);
        choiceBoxConsultant.setValue(0);
        ChoiceBox<Integer> choiceBoxCashier = new ChoiceBox<Integer>(choiceCashier);
        choiceBoxCashier.setLayoutX(800);
        choiceBoxCashier.setLayoutY(180);
        choiceBoxCashier.setValue(1);
        ChoiceBox<Integer> choiceBoxShelf = new ChoiceBox<Integer>(choiceShelf);
        choiceBoxShelf.setLayoutX(800);
        choiceBoxShelf.setLayoutY(220);
        choiceBoxShelf.setValue(1);
        Text info=new Text("Реакции покупателей:\n1.Красный - покупатель ушел из-за длинных очередей\n2.Синий - покупатель ушел, т.к. не было нужных товаров/не помог консультант\n3.Зеленый - покупатель ушел с покупками\n4.Оранжевый - необходимого товара нет в магазине\n5.Фиолетовый - необходимого товара нет на полке\n6.Желтый - покупателю не хватает денег на товар");
        info.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        info.setLayoutX(200);
        info.setLayoutY(340);
        Group group = new Group(title, numberCustomer, numberCashier, numberConsultant, numberShelf, start, choiceBoxCashier, choiceBoxConsultant,choiceBoxShelf,choiceCustomer,label, info);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setOnCloseRequest(e -> {
            Platform.exit(); // Закрытие Javafx
            System.exit(0); // Завершение программы
        });
        start.setOnAction(e -> {
            stage.hide();
            Modeling modeling=new Modeling(choiceBoxCashier.getValue(),(int)choiceCustomer.getValue(),choiceBoxShelf.getValue(),choiceBoxConsultant.getValue());
            modeling.change();
        });
        stage.show();
    }
}