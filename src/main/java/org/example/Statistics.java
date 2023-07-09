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
    private Stage stage;    //графическое окно
    static private int exitCustomer;    //количество покупателей, не совершивших покупку
    static private int notWaitingCustomer;  //количество покупателей, не дождавшихся помощи консультанта/выкладки товара
    static private int highPriceCustomer;   //количество покупателей, не купивших товар из-за высокой цены
    static private int totalBuyer;          //общее число покупателей
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
    public static void addExitCustomer(){
        exitCustomer++;
    }
    public static void addNotWaitingCustomer(){
        notWaitingCustomer++;
    }
    public static void addHighPriceCustomer(){
        highPriceCustomer++;
    }
    public static void addTotalBuyer(){
        totalBuyer++;
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
        Text inf=new Text("Всего людей пришло: "+ totalBuyer+"\n"+"Из них ушло без покупок: "+exitCustomer+"\n"+
                "Количество случаев, когда покупатель не дождался помощи консультанта/товара не было: "+
                notWaitingCustomer+"\n"+"Количество случаев, когда покупателю не хватило денег на товар: "+
                highPriceCustomer+"\n\n"+"Работа касс:");
        Text cashier=new Text();
        for (int i=0; i!=Modeling.getNumberCashier();i++){
            cashier.setText(cashier.getText()+"Касса "+(i+1)+": "+ profit[i]+" руб\n");
        }
        cashier.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        cashier.setLayoutY(380);
        cashier.setLayoutX(60);
        inf.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        inf.setLayoutY(220);
        inf.setLayoutX(60);
        Pane root = new Pane(); //панель компоновки для добавления элементов
        Scene scene = new Scene(root);  //создание сцены с корневым узлом root
        root.getChildren().addAll(title,inf,cashier);
        stage.setScene(scene);  //установка сцены для окна
        stage.show();   //отображение окна
    }
}
