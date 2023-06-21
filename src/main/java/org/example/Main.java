package org.example;

import javafx.application.Application;
import javafx.scene.Group;
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
        title.setLayoutY(70);
        title.setLayoutX(220);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        Group group = new Group(title);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.show();
    }
}