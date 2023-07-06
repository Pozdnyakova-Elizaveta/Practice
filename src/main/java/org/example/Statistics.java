package org.example;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Statistics {       //класс окна статистики
    private Stage stage;
    static int exitCustomer;    //количество покупателей, не совершивших покупку
    static int notWaitingCustomer;  //количество покупателей, не дождавшихся помощи консультанта/выкладки товара
    static int highPriceCustomer;   //количество покупателей, не купивших товар из-за высокой цены
    public Statistics(){
        exitCustomer=0;
        notWaitingCustomer=0;
        highPriceCustomer=0;
        stage=new Stage();
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
    }
    public void display(int [] profit){     //функция вывода окна и его графических элементов
        stage.setOnCloseRequest(e -> {      //закрытие приложения при нажатии на крестик
            Platform.exit();
            System.exit(0);
        });
        Text title= new Text("Статистика запуска");     //вывод информации
        title.setLayoutY(70);
        title.setLayoutX(320);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        Text inf=new Text("Всего людей пришло: "+ Modeling.numberCustomer+"\n"+"Из них ушло без покупок: "+exitCustomer+"\n"+"Количество случаев, когда покупатель не дождался помощи консультанта/товара не было: "+notWaitingCustomer+"\n"+"Количество случаев, когда покупателю не хватило денег на товар: "+
                highPriceCustomer+"\n\n"+"Работа касс:");
        Text cashier=new Text();
        for (int i=0; i!=Modeling.numberCashier;i++){
            cashier.setText(cashier.getText()+"Касса "+(i+1)+": "+ profit[i]+" руб\n");
        }
        cashier.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        cashier.setLayoutY(380);
        cashier.setLayoutX(60);
        inf.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        inf.setLayoutY(220);
        inf.setLayoutX(60);
        Pane root = new Pane();
        Scene scene = new Scene(root);
        root.getChildren().addAll(title,inf,cashier);
        stage.setScene(scene);
        stage.show();
    }
}
