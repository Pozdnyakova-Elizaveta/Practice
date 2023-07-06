package org.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
    static ArrayList<Consultant> co;
    static ArrayList<Cashier> ca;
    static ArrayList<Shelf> s;
    static ArrayList<Customer> c;
    private Timer timerConsultant;
    private Timer timerCustomer;
    private boolean isPaused = false;
    private int clickCount = 0;
    private int speed;

    public Modeling (int numberCashier, int numberCustomer, int numberShelf, int numberConsultant) {
        speed = 1;
        this.numberCashier = numberCashier;
        this.numberShelf = numberShelf;
        this.numberConsultant = numberConsultant;
        this.numberCustomer = numberCustomer;
        co = new ArrayList<Consultant>();
        ca = new ArrayList<Cashier>();
        s = new ArrayList<Shelf>();
        c = new ArrayList<Customer>();
        stage = new Stage();
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
    }

        public void change() {
            Statistics statistics = new Statistics();
            Pane root = new Pane();
            Canvas canvas = new Canvas(1200, 700);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            canvas.setOnMousePressed(e -> {
                if (isPaused) {
                    boolean object=false;
                    double mouseX = e.getX();
                    double mouseY = e.getY();
                    for (int i = 0; i != c.size(); i++) {
                        if (c.get(i).getModel().contains(mouseX, mouseY)) {
                            object=true;
                            String text = new String("Количество денег - " + c.get(i).getAmountMoney() + "\nОбщая сумма товаров - " + c.get(i).getTotalSpend() + "\nСписок товаров");
                            for (int j = 0; j != c.get(i).getSizeProductList(); j++) {
                                text = text + "\n" + c.get(i).getProductList(j);
                            }
                            gc.setFill(Color.BLACK);
                            gc.fillText(text, c.get(i).getModel().getCenterX() + 20, c.get(i).getModel().getCenterY() - 20);
                        }
                    }
                    for (int i = 0; i != s.size(); i++) {
                        if (s.get(i).getModel().contains(mouseX, mouseY)) {
                            object=true;
                            String text = new String("Цены товаров:");
                            for (int j = 0; j != s.get(i).getSizePrice(); j++) {
                                text = text + " " + s.get(i).getPrice(j) + ",";
                                if (j == 2) text = text + "\n";
                            }
                            gc.setFill(Color.BLACK);
                            gc.fillText(text, s.get(i).getModel().getX() - 20, s.get(i).getModel().getY() - 20);
                        }
                    }
                    for (int i = 0; i != numberCashier; i++) {
                        if (ca.get(i).getModel().contains(mouseX, mouseY)) {
                            object=true;
                            String text = new String("Прибыль: " + ca.get(i).getProfit() + "\nКоличество пройденных покупателей: " + ca.get(i).getNumberBuyers());
                            gc.setFill(Color.BLACK);
                            gc.fillText(text, ca.get(i).getModel().getCenterX() - 20, ca.get(i).getModel().getCenterY() - 60);
                        }
                    }
                    if (!object) canvasUpdate(gc);
                }
            });
            Button speedButton = new Button("Завершить");
            speedButton.setLayoutX(1050);
            speedButton.setLayoutY(10);
            speedButton.setPrefSize(100, 25);
            root.getChildren().add(speedButton);
            Button pauseButton = new Button("Пауза");
            pauseButton.setLayoutX(950);
            pauseButton.setLayoutY(10);
            pauseButton.setPrefSize(50, 25);
            root.getChildren().add(pauseButton);
            root.setOnMouseClicked(e -> {
                if (e.getX()>=950 && e.getX()<=1000 && e.getY()>=10 && e.getY()<=35){
                    if (!isPaused) isPaused=true;
                    else isPaused=false;
                }
                if (e.getX()>=1050 && e.getX()<=1150 && e.getY()>=10 && e.getY()<=35){
                    c.clear();
                    stage.hide();
                    int[] profit = new int[numberCashier];
                    for (int i = 0; i != numberCashier; i++) profit[i] = ca.get(i).getProfit();
                    statistics.display(profit);
                }
            });
            int i;
            for (i = 0; i != numberConsultant; i++) {
                co.add(new Consultant());
            }
            for (i = 0; i != numberCashier; i++) {
                ca.add(new Cashier());
                root.getChildren().add(ca.get(i).getModel());
            }
            for (i = 0; i != numberShelf; i++) {
                s.add(new Shelf());
                root.getChildren().add(s.get(i).getModel());
                root.getChildren().add(s.get(i).getText());
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    if (count != numberCustomer && !isPaused) {
                        c.add(new Customer());
                        cicleCustomer(c.get(c.size() - 1));
                        count++;
                    }
                    if (count == numberCustomer)
                        if (c.size() == 0) {
                            Platform.runLater(() -> {
                                stage.hide();
                                int[] profit = new int[numberCashier];
                                for (int i = 0; i != numberCashier; i++) profit[i] = ca.get(i).getProfit();
                                statistics.display(profit);
                            });
                            timer.cancel();
                        }
                }
            }, 0, 3000);
            for (i = 0; i != numberConsultant; i++) cicleConsultant(co.get(i));
            root.getChildren().add(canvas);
            wallPainting(root);
            Scene scene = new Scene(root);
            Rectangle cashRegister = new Rectangle(65, 100, 100 * Cashier.quantity, 40);
            cashRegister.setFill(Color.BROWN);
            cashRegister.setStrokeWidth(2);
            cashRegister.setStroke(Color.BLACK);
            root.getChildren().add(cashRegister);
            stage.setScene(scene);
            stage.show();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Timer time = new Timer();
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (c.size() == 0) time.cancel();
                            if (!isPaused) canvasUpdate(gc);
                        }
                    }, 0, 30);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            stage.setOnCloseRequest(e -> {
                Platform.exit(); // Закрытие Javafx
                System.exit(0); // Завершение программы
            });
        }

        public void wallPainting(Pane root) {
            Line wallLeft = new Line(0.0, 50.0, 700.0, 50.0);
            Line wallRight = new Line(800.0, 50.0, 1200.0, 50.0);
            wallLeft.setStrokeWidth(2);
            wallRight.setStrokeWidth(2);
            Polyline wallStockRight = new Polyline();
            wallStockRight.getPoints().addAll(new Double[]{
                    175.0, 500.0,
                    250.0, 500.0,
                    250.0, 700.0});
            wallStockRight.setFill(Color.WHITESMOKE);
            wallStockRight.setStrokeWidth(2);
            wallStockRight.setStroke(Color.BLACK);
            Line wallStockLeft = new Line(0.0, 500.0, 75.0, 500.0);
            wallStockLeft.setStrokeWidth(2);
            Polyline shelfStock=new Polyline();
            shelfStock.getPoints().addAll(new Double[]{20.0,550.0,70.0,550.0,70.0,600.0,180.0, 600.0,180.0, 550.0,230.0,550.0,230.0,650.0,20.0,650.0,20.0,550.0});
            shelfStock.setFill(Color.BROWN);
            shelfStock.setStrokeWidth(2);
            shelfStock.setStroke(Color.BLACK);
            root.getChildren().addAll(wallLeft, wallRight, wallStockRight, wallStockLeft, shelfStock);
        }

        public void canvasUpdate(GraphicsContext gc) {
            int i;
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            for (i = 0; i != co.size(); i++) co.get(i).updateConsultant(gc);
            for (i = 0; i != c.size(); i++) c.get(i).updateCustomer(gc);
        }

        public void cicleCustomer(Customer cu) {
        timerCustomer=new Timer();
            TimerTask taskCustomer = new TimerTask() {
                Color color;
                boolean dvih = false;

                @Override
                public void run() {
                    if (!isPaused) {
                        switch (cu.getStatus()) {
                            case "entry": {
                                if (cu.getModel().getCenterY() != 100) cu.movmentYY(100);
                                if (cu.getModel().getCenterY() == 100) {
                                    boolean fl = false;
                                    for (int i = 0; i != numberCashier; i++) {
                                        if (ca.get(i).getQueueBuyers() < cu.getValidQueue()) fl = true;
                                    }
                                    if (fl == false) {
                                        color = Color.RED;
                                        cu.setStatus("exit");
                                        Statistics.exitCustomer += 1;
                                    }
                                    if (fl == true) {
                                        cu.setStatus("product");
                                    }
                                }
                                break;
                            }
                            case "product": {
                                cu.cycleProduct(s);
                                if (cu.getStatus() == "exit") color = Color.BLUE;
                            }
                            break;
                            case "to checkout": {
                                int min_ch = ca.get(numberCashier - 1).getQueueBuyers();
                                int num = numberCashier - 1;
                                for (int i = numberCashier - 1; i >= 0; i--) {
                                    if (ca.get(i).getQueueBuyers() < min_ch) {
                                        num = i;
                                        min_ch = ca.get(i).getQueueBuyers();
                                    }
                                }
                                cu.movementCheckout(ca.get(num));
                                cu.setNumCheckout(num);
                                break;
                            }
                            case "queue": {
                                if (cu.getNumQueue() == 0) {
                                    ca.get(cu.getNumCheckout()).service(cu);
                                    color = Color.GREEN;
                                    if (ca.get(cu.getNumCheckout()).getQueueBuyers() != 0) dvih = true;
                                }
                                break;
                            }
                            case "exit": {
                                if (color == Color.GREEN) {
                                    if (cu.getModel().getCenterX() != Customer.appearX && cu.getModel().getCenterX() != ca.get(cu.getNumCheckout()).getModel().getCenterX() + 30 && (cu.getModel().getCenterY() == Cashier.appearY + 90))
                                        cu.movmentXX((int) ca.get(cu.getNumCheckout()).getModel().getCenterX() + 50);
                                }
                                cu.colorChange(color);
                                if (cu.getModel().getCenterY() != Customer.appearY && cu.getModel().getCenterX() != Customer.appearX)
                                    cu.movmentYY(Shelf.secondLine - 90);
                                if (cu.getModel().getCenterY() == Shelf.secondLine - 90 && cu.getModel().getCenterX() != Customer.appearX)
                                    cu.movmentXX(Customer.appearX);
                                if (cu.getModel().getCenterY() != Customer.appearY && cu.getModel().getCenterX() == Customer.appearX)
                                    cu.movmentYY(Customer.appearY);
                                if (cu.getModel().getCenterY() == Customer.appearY) {
                                    c.remove(cu);
                                }
                                break;
                            }
                        }
                        if (dvih) {
                            for (int i = 0; i != c.size(); i++) {
                                if (c.get(i).getStatus() == "queue" && c.get(i).getModel().getCenterX() == ca.get(cu.getNumCheckout()).getModel().getCenterX() && c.get(i).getModel().getCenterY() != Cashier.appearY + 90 + 35 * (c.get(i).getNumQueue() - 1))
                                    if (c.get(i).getModel().getCenterY() >= Cashier.appearY + 90)
                                        c.get(i).movmentYY(Cashier.appearY + 90 + 35 * (c.get(i).getNumQueue() - 1));
                                if (c.get(i).getStatus() == "queue" && c.get(i).getModel().getCenterX() == ca.get(cu.getNumCheckout()).getModel().getCenterX() && c.get(i).getModel().getCenterY() == Cashier.appearY + 90 + 35 * (c.get(i).getNumQueue() - 1)) {
                                    c.get(i).setNumQueue(c.get(i).getNumQueue() - 1);
                                    if (c.get(i).getNumQueue() == 0) dvih = false;
                                }
                            }
                        }
                        if (c.size() == 0) timerCustomer.cancel();
                    }
                }
            };
            timerCustomer.schedule(taskCustomer,0,(int)cu.getMovementSpeed()/speed);
        }

        public void cicleConsultant(Consultant con) {
        timerConsultant=new Timer();
            int i;
            TimerTask taskConsultant= new TimerTask() {
                @Override
                public void run() {
                    if (!isPaused) {
                        search(con);
                        switch (con.getStatus()) {
                            case "wait": {
                                break;
                            }
                            case "on stock":
                                con.onStock();
                                break;
                            case "help": {
                                int i = 0;
                                while (i != c.size() && c.get(i).getFilling() != (co.indexOf(con) + 1)) i++;
                                if (i != c.size()) {
                                    con.help(c.get(i));
                                    if (c.get(i).getNeedHelp() == false) {
                                        c.get(i).setFilling(0);
                                        con.setStatus("place");
                                    }
                                } else con.setStatus("place");
                                break;
                            }
                            case "to shelf": {
                                int i = 0;
                                while (i != numberShelf && s.get(i).getFilling() != (co.indexOf(con) + 1)) i++;
                                if (i != numberShelf) con.toShelf(s.get(i));
                                if (i == numberShelf || s.get(i).getNumberGoods() != 0) {
                                    if (i != numberShelf && s.get(i).getNumberGoods() != 0) s.get(i).setFilling(0);
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
                }
            };
            timerConsultant.schedule(taskConsultant,0,(int)(con.getMovementSpeed()/speed));

        }

        public void search(Consultant con) {
            int i = 0;
            while (i != numberShelf && (con.getStatus() == "wait" || con.getStatus() == "place")) {
                if (s.get(i).getNumberGoods() == 0 && s.get(i).getFilling() == 0) {
                    con.setStatus("on stock");
                    s.get(i).setFilling(co.indexOf(con) + 1);
                }
                i++;
            }
            i = 0;
            while (i != c.size() && (con.getStatus() == "wait" || con.getStatus() == "place")) {
                if (c.get(i).getNeedHelp() == true && c.get(i).getFilling() == 0) {
                    con.setStatus("help");
                    c.get(i).setFilling(co.indexOf(con) + 1);
                }
                i++;
            }
        }
}