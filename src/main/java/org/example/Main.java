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

public class Main extends Application { //основной класс приложения
    private Slider choiceCustomer;
    private Button start;
    private ChoiceBox<Integer> choiceBoxConsultant;
    private ChoiceBox<Integer> choiceBoxCashier;
    private ChoiceBox<Integer> choiceBoxShelf;

    public static void main(String[] args) {
        Application.launch(args);
    } //запуск приложения
    @Override
    public void start(Stage stage) {        //определение графического интерфейса
        Group group = new Group();          //контейнер для отображения элементов окна
        stage.setResizable(false);
        createText(group);
        createChoiceElement(group);
        Scene scene = new Scene(group); //создание сцены с корневым узлом group
        stage.setScene(scene);  //установка сцены для окна
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setOnCloseRequest(e -> {  //при нажатии на крестик - полное закрытие программы
            Platform.exit();
            System.exit(0);
        });
        start.setOnAction(e -> {//при нажатии на кнопку "Запуск" - переход к новому окну с передачей данных с элементов выбора
            stage.hide();
            Modeling modeling=new Modeling(choiceBoxCashier.getValue(),(int)choiceCustomer.getValue(),
                    choiceBoxShelf.getValue(),choiceBoxConsultant.getValue());
            modeling.modelingProcess();
        });
        stage.show();   //отображение окна
    }
    public void createText(Group group){        //создание и вывод надписей
        Text title=new Text("Имитационная модель магазина");
        Text numberCashier=new Text("Выберите число кассиров");
        Text numberCustomer=new Text("Выберите число покупателей");
        Text numberShelf=new Text("Выберите количество стеллажей с товарами");
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
        Text info=new Text("Реакции покупателей:\n1.Красный - покупатель ушел из-за длинных очередей\n" +
                "2.Синий - покупатель ушел, т.к. не было нужных товаров/не помог консультант\n" +
                "3.Зеленый - покупатель ушел с покупками\n4.Оранжевый - необходимого товара нет в магазине\n" +
                "5.Фиолетовый - необходимого товара нет на полке\n6.Желтый - покупателю не хватает денег на товар");
        info.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        info.setLayoutX(200);
        info.setLayoutY(340);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        group.getChildren().addAll(title, numberCustomer,numberShelf,numberCashier,numberConsultant, info);
    }
    public void createChoiceElement(Group group){   //создание элементов выбора
        start = new Button("Запуск");              //нопка запуска модели
        start.setLayoutX(500);
        start.setLayoutY(560);
        start.setPrefSize(200,50);
        choiceCustomer=new Slider(1.0,100.0,5.0);   //выбор числа покупателей
        choiceCustomer.setLayoutX(800);
        choiceCustomer.setLayoutY(140);
        choiceCustomer.setShowTickLabels(true);
        Label numberCustomer = new Label(String.valueOf((int)(choiceCustomer.getValue()))); //вывод значения Slider
        numberCustomer.setLayoutX(1000);
        numberCustomer.setLayoutY(140);
        choiceCustomer.valueProperty().addListener((observable, oldValue, newValue) -> {
            numberCustomer.setText(String.valueOf(newValue.intValue()));
        });
        ObservableList<Integer> choiceConsultant = FXCollections.observableArrayList(0,1,2,3,4,5,6);
        ObservableList<Integer> choiceCashier = FXCollections.observableArrayList(1,2,3);
        ObservableList<Integer> choiceShelf = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
        choiceBoxConsultant = new ChoiceBox<Integer>(choiceConsultant);     //выбор числа консультантов
        choiceBoxConsultant.setLayoutX(800);
        choiceBoxConsultant.setLayoutY(260);
        choiceBoxConsultant.setValue(0);
        choiceBoxCashier = new ChoiceBox<Integer>(choiceCashier);           //выбор числа кассиров
        choiceBoxCashier.setLayoutX(800);
        choiceBoxCashier.setLayoutY(180);
        choiceBoxCashier.setValue(1);
        choiceBoxShelf = new ChoiceBox<Integer>(choiceShelf);               //выбор числа стеллажей
        choiceBoxShelf.setLayoutX(800);
        choiceBoxShelf.setLayoutY(220);
        choiceBoxShelf.setValue(1);
        group.getChildren().addAll(start, choiceBoxCashier,choiceBoxShelf,choiceBoxConsultant,choiceCustomer,numberCustomer);
    }
}