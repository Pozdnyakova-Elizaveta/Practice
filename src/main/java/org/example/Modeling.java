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

public class Modeling { //класс окна, где происходит моделирование
    private Stage stage;   //графическое окно
    static int numberCashier;   //выбранное количество кассиров
    static int numberCustomer;  //выбранное количество покупателей
    static int numberShelf;     //выбранное количество стеллажей
    static int numberConsultant;    //выбранное количество консультантов
    private ArrayList<Consultant> consultantList;    //массив с объектами-консультантами
    private ArrayList<Cashier> cashierList;       //массив с объектами-кассирами
    private ArrayList<Shelf> shelfList;          //массив с объектами-стеллажами
    private ArrayList<Customer> customerList;       //массив с объектами - покупателями
    private boolean isPaused = false;   //флаг для паузы

    public Modeling (int numberCashier, int numberCustomer, int numberShelf, int numberConsultant) {
        this.numberCashier = numberCashier;
        this.numberShelf = numberShelf;
        this.numberConsultant = numberConsultant;
        this.numberCustomer = numberCustomer;
        consultantList = new ArrayList<Consultant>();
        cashierList = new ArrayList<Cashier>();
        shelfList = new ArrayList<Shelf>();
        customerList = new ArrayList<Customer>();
        stage = new Stage();
        stage.setTitle("Практика");
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
    }
        public void modelingProcess() {  //основная функция моделирования
            Statistics statistics = new Statistics();   //создания объекта следующего окна для сбора статистики
            Pane root = new Pane(); //панель компоновки для добавления элементов
            Canvas canvas = new Canvas(1200, 700);  //полотно для отрисовки
            GraphicsContext gc = canvas.getGraphicsContext2D(); //объект для выполнения отрисовки на полотне через буфер
            canvas.setOnMousePressed(e -> {     //при нажатии мышью
                if (isPaused) {     //если моделирование на паузе
                    boolean object=false;   //проверка, было ли совершено нажатие на объект
                    double mouseX = e.getX();   //координаты нажатия мыши
                    double mouseY = e.getY();
                    for (int i = 0; i != customerList.size(); i++) {
                        if (customerList.get(i).getModel().contains(mouseX, mouseY)) { //если нажатие на покупателя - вывод информации о покупателе
                            object=true;
                            String text = new String("Количество денег - " + customerList.get(i).getAmountMoney() +
                                    "\nОбщая сумма товаров - " + customerList.get(i).getTotalSpend() + "\nСписок товаров");
                            for (int j = 0; j != customerList.get(i).getSizeProductList(); j++) {
                                text = text + "\n" + customerList.get(i).getProductList(j);
                            }
                            gc.setFill(Color.BLACK);
                            gc.fillText(text, customerList.get(i).getModel().getCenterX() + 20, customerList.get(i).getModel().getCenterY() - 20);
                        }
                    }
                    for (int i = 0; i != shelfList.size(); i++) {
                        if (shelfList.get(i).getModel().contains(mouseX, mouseY)) { //если нажатие на стеллаж - вывод информации о стеллаже
                            object=true;
                            String text = new String("Цены товаров:");
                            for (int j = 0; j != shelfList.get(i).getSizePrice(); j++) {
                                text = text + " " + shelfList.get(i).getPrice(j) + ",";
                                if (j == 2) text = text + "\n";
                            }
                            gc.setFill(Color.BLACK);
                            gc.fillText(text, shelfList.get(i).getModel().getX() - 20, shelfList.get(i).getModel().getY() - 20);
                        }
                    }
                    for (int i = 0; i != numberCashier; i++) {          //если нажатие на кассира - вывод информации о кассире
                        if (cashierList.get(i).getModel().contains(mouseX, mouseY)) {
                            object=true;
                            String text = new String("Прибыль: " + cashierList.get(i).getProfit() + "\nКоличество пройденных покупателей: "
                                    + cashierList.get(i).getNumberBuyers());
                            gc.setFill(Color.BLACK);
                            gc.fillText(text, cashierList.get(i).getModel().getCenterX() - 20, cashierList.get(i).getModel().getCenterY() - 60);
                        }
                    }
                    //если нажатие было совершено ни на один из этих объектов - очищение канвы от выведенной информации
                    if (!object) canvasUpdate(gc);
                }
            });
            Button speedButton = new Button("Завершить");   //создание кнопки завершения моделирования
            speedButton.setLayoutX(1050);
            speedButton.setLayoutY(10);
            speedButton.setPrefSize(100, 25);
            root.getChildren().add(speedButton);
            Button pauseButton = new Button("Пауза");   //создания кнопки паузы
            pauseButton.setLayoutX(950);
            pauseButton.setLayoutY(10);
            pauseButton.setPrefSize(50, 25);
            root.getChildren().add(pauseButton);
            root.setOnMouseClicked(e -> {
                if (e.getX()>=950 && e.getX()<=1000 && e.getY()>=10 && e.getY()<=35){
                    //применение паузы к процессу моделирования
                    if (!isPaused) isPaused=true;
                    else isPaused=false;
                }
                if (e.getX()>=1050 && e.getX()<=1150 && e.getY()>=10 && e.getY()<=35){
                    //завершение моделирования и переход к окну статистики
                    customerList.clear();
                    stage.hide();
                    int[] profit = new int[numberCashier];
                    //передача информации о прибыли кассиров
                    for (int i = 0; i != numberCashier; i++) profit[i] = cashierList.get(i).getProfit();
                    statistics.display(profit);
                }
            });
            int i;
            for (i = 0; i != numberConsultant; i++) {   //создание объектов-консультантов
                consultantList.add(new Consultant());
            }
            for (i = 0; i != numberCashier; i++) {  //создание объектов-кассиров
                cashierList.add(new Cashier());
                root.getChildren().add(cashierList.get(i).getModel());
            }
            for (i = 0; i != numberShelf; i++) {    //создание объектов-стеллажей
                shelfList.add(new Shelf());
                root.getChildren().add(shelfList.get(i).getModel());
                root.getChildren().add(shelfList.get(i).getText());
            }
            Timer timer = new Timer();  //запуск таймера
            timer.schedule(new TimerTask() {    //выполнение каждые 3 секунды
                int count = 0;

                @Override
                public void run() {
                    if (count != numberCustomer && !isPaused) { //если не созданы все покупатели и моделирование не на паузе
                        customerList.add(new Customer());  //добавление покупателя
                        Statistics.totalBuyer++;
                        cicleCustomer(customerList.get(customerList.size() - 1));
                        count++;
                    }
                    if (count == numberCustomer)    //если все покупатели созданы
                        if (customerList.size() == 0) {    //и массив с ними пустой, т.е. все вышли из магазина
                            Platform.runLater(() -> {   //завершение моделирования и переход к статистике
                                stage.hide();
                                int[] profit = new int[numberCashier];
                                //передача информации о прибыли кассиров
                                for (int i = 0; i != numberCashier; i++) profit[i] = cashierList.get(i).getProfit();
                                statistics.display(profit);
                            });
                            timer.cancel();
                        }
                }
            }, 0, 3000);
            for (i = 0; i != numberConsultant; i++) cicleConsultant(consultantList.get(i)); //запуск цмкла работы консультантов
            root.getChildren().add(canvas);
            wallPainting(root); //создание стен, шкафа на складе, кассовой стойки
            Scene scene = new Scene(root);  //создание сцены с корневым узлом root
            stage.setScene(scene);  //установка сцены для окна
            stage.show();   //отображение окна
            Runnable runnable = new Runnable() {    //создание потока для перерисовки канвы
                @Override
                public void run() {
                    Timer time = new Timer();
                    time.schedule(new TimerTask() { //обновление канвы каждые 30мс, пока моделирование не закончится
                        @Override
                        public void run() {
                            if (customerList.size() == 0) time.cancel();
                            if (!isPaused) canvasUpdate(gc);
                        }
                    }, 0, 30);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start(); //запуск потока
            stage.setOnCloseRequest(e -> {  //закрытие программы при нажатии на крестик
                Platform.exit();
                System.exit(0);
            });
        }

        public void wallPainting(Pane root) {   //создание окружающих элементов
            Line wallLeft = new Line(0.0, 50.0, 700.0, 50.0);   //создание левой стены магазина
            Line wallRight = new Line(800.0, 50.0, 1200.0, 50.0);   //создание правой стены магазина
            wallLeft.setStrokeWidth(2);
            wallRight.setStrokeWidth(2);
            Polyline wallStockRight = new Polyline();   //создание правой стены склада
            wallStockRight.getPoints().addAll(new Double[]{
                    175.0, 500.0,
                    250.0, 500.0,
                    250.0, 700.0});
            wallStockRight.setFill(Color.WHITESMOKE);
            wallStockRight.setStrokeWidth(2);
            wallStockRight.setStroke(Color.BLACK);
            Line wallStockLeft = new Line(0.0, 500.0, 75.0, 500.0); //создание левой стены склада
            wallStockLeft.setStrokeWidth(2);
            Polyline shelfStock=new Polyline(); //создание шкафа на складе
            shelfStock.getPoints().addAll(new Double[]{
                    20.0,550.0,70.0,550.0,
                    70.0,600.0,180.0,600.0,
                    180.0, 550.0,230.0,550.0,
                    230.0,650.0,20.0,650.0,
                    20.0,550.0});
            shelfStock.setFill(Color.BROWN);
            shelfStock.setStrokeWidth(2);
            shelfStock.setStroke(Color.BLACK);
            //создание кассовой стойки
            Rectangle cashRegister = new Rectangle(65, 100, 100 * Cashier.quantity, 40);
            cashRegister.setFill(Color.BROWN);
            cashRegister.setStrokeWidth(2);
            cashRegister.setStroke(Color.BLACK);
            root.getChildren().add(cashRegister);
            root.getChildren().addAll(wallLeft, wallRight, wallStockRight, wallStockLeft, shelfStock);
        }

        public void canvasUpdate(GraphicsContext gc) {  //обновление канвы
            int i;
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());  //очистка канвы
            for (i = 0; i != consultantList.size(); i++) consultantList.get(i).update(gc);  //отрисовка консультантов
            for (i = 0; i != customerList.size(); i++) customerList.get(i).update(gc);      //отрисовка покупателей
        }

        public void cicleCustomer(Customer cu) {    //цикл действий покупателя
        Timer timerCustomer=new Timer();
            TimerTask taskCustomer = new TimerTask() { //выполняется согласно скорости покупателя
                Color color;
                boolean movement = false;

                @Override
                public void run() {
                    if (!isPaused) {
                        switch (cu.getStatus()) {   //выполнение действий по статусу покупателя
                            case "entry": { //статус "вход"
                                if (cu.getModel().getCenterY() != 100) cu.movementY(100);  //покупатель заходит
                                if (cu.getModel().getCenterY() == 100) {
                                    boolean queueSize = false;  //флаг для оценки размера очередей
                                    for (int i = 0; i != numberCashier; i++) {
                                        //если есть очередь размером меньше предельного для покупателя - меняем флаг
                                        if (cashierList.get(i).getQueueBuyers() < cu.getValidQueue()) queueSize = true;
                                    }
                                    if (queueSize == false) {   //если все очереди длинные для покупателя
                                        color = Color.RED;  //меняем цвет на красный
                                        cu.setStatus("exit");   //статус - выход
                                        Statistics.exitCustomer += 1;
                                    }
                                    if (queueSize == true) {    //если есть допустимые очереди - статус поиск товаров
                                        cu.setStatus("product search");
                                    }
                                }
                                break;
                            }
                            case "product search": {    //статус поиска товара
                                cu.cycleProduct(shelfList); //запуск функции поиска товаров
                                //если покупатель не взял ни одного товара - меняем цвет
                                if (cu.getStatus() == "exit") color = Color.BLUE;
                            }
                            break;
                            case "to checkout": {   //статус "на кассу"
                                int min_ch = cashierList.get(numberCashier - 1).getQueueBuyers();
                                int num = numberCashier - 1;
                                //поиск кассы с минимальным количеством людей
                                for (int i = numberCashier - 1; i >= 0; i--) {
                                    if (cashierList.get(i).getQueueBuyers() < min_ch) {
                                        num = i;
                                        min_ch = cashierList.get(i).getQueueBuyers();
                                    }
                                }
                                cu.movementCheckout(cashierList.get(num));  //функция движения к кассе
                                cu.setNumCheckout(num); //запись номера выбранной кассы
                                break;
                            }
                            case "queue": { //статус "очередь"
                                if (cu.getNumQueue() == 0) {   //если покупатель первый в очереди
                                    cashierList.get(cu.getNumCheckout()).service(cu);    //запуск функции обслуживания
                                    color = Color.GREEN;
                                    //изменение флага смещения покупателей в очереди
                                    if (cashierList.get(cu.getNumCheckout()).getQueueBuyers() != 0) movement = true;
                                }
                                break;
                            }
                            case "exit": {  //статус "выход"
                                cu.colorChange(color); //изменение цвета покупателя
                                //движение к выходу
                                if (cu.getModel().getCenterY() != Customer.appearY && cu.getModel().getCenterX() != Customer.appearX)
                                    cu.movementY(Shelf.secondLine - 90);
                                if (cu.getModel().getCenterY() == Shelf.secondLine - 90 && cu.getModel().getCenterX() != Customer.appearX)
                                    cu.movementX(Customer.appearX);
                                if (cu.getModel().getCenterY() != Customer.appearY && cu.getModel().getCenterX() == Customer.appearX)
                                    cu.movementY(Customer.appearY);
                                //если покупатель дошел до выхода - удаление из массива
                                if (cu.getModel().getCenterY() == Customer.appearY) {
                                    customerList.remove(cu);
                                }
                                break;
                            }
                        }
                        if (movement) { //если покупателя обслужили - нужно сместить в очереди остальных
                            for (int i = 0; i != customerList.size(); i++) {
                                //если у покупателя статус очереди и он находится на нужной кассе
                                if (customerList.get(i).getStatus() == "queue" &&
                                        customerList.get(i).getModel().getCenterX() == cashierList.get(cu.getNumCheckout()).getModel().getCenterX()
                                        && customerList.get(i).getModel().getCenterY() != Cashier.appearY + 90 + 35 * (customerList.get(i).getNumQueue() - 1))
                                    if (customerList.get(i).getModel().getCenterY() >= Cashier.appearY + 90)
                                        //он двигается по Y
                                        customerList.get(i).movementY(Cashier.appearY + 90 + 35 * (customerList.get(i).getNumQueue() - 1));
                                //если теперь он находится на нужном месте
                                if (customerList.get(i).getStatus() == "queue" &&
                                        customerList.get(i).getModel().getCenterX() == cashierList.get(cu.getNumCheckout()).getModel().getCenterX()
                                        && customerList.get(i).getModel().getCenterY() == Cashier.appearY + 90 + 35 * (customerList.get(i).getNumQueue() - 1)) {
                                    //меняем его номер в очереди
                                    customerList.get(i).setNumQueue(customerList.get(i).getNumQueue() - 1);
                                    //если появился новый первый покупатель в очереди - остановка движения
                                    if (customerList.get(i).getNumQueue() == 0) movement = false;
                                }
                            }
                        }
                        if (customerList.size() == 0) timerCustomer.cancel();
                    }
                }
            };
            timerCustomer.schedule(taskCustomer,0,cu.getMovementSpeed());
        }

        public void cicleConsultant(Consultant con) {   //цикл работы консультанта
        Timer timerConsultant=new Timer();
            int i;
            TimerTask taskConsultant= new TimerTask() { //выполняется согласно скорости консультанта
                @Override
                public void run() {
                    if (!isPaused) {    //если моделирование не на паузе
                        search(con);    //поиск действий консультанту
                        switch (con.getStatus()) {  //выполнение действий по статусу консультанта
                            case "on stock":    //статус "на склад"
                                con.onStock();  //запуск функции движения на склад
                                break;
                            case "help": { //статус "помощь"
                                int i = 0;
                                //поиск покупателя, за которым по номеру закрепили кассира
                                while (i != customerList.size() && customerList.get(i).getFilling() != (consultantList.indexOf(con) + 1)) i++;
                                //если такой покупатель найден
                                if (i != customerList.size()) {
                                    con.help(customerList.get(i)); //запуск функции помощи покупателю
                                    //если консультант помог покупателю или покупатель его не дождался
                                    if (customerList.get(i).getNeedHelp() == false) {
                                        customerList.get(i).setFilling(0); //обнуление номера консультанта
                                        con.setStatus("place appear"); //возвращение на место появления
                                    }
                                }
                                else con.setStatus("place appear"); //сли не найден - возвращение на место появления
                                break;
                            }
                            case "to shelf": {  //статус "к стеллажу"
                                int i = 0;
                                //поиск стеллажа, за которым по номеру закрепили кассира
                                while (i != numberShelf && shelfList.get(i).getFilling() != (consultantList.indexOf(con) + 1)) i++;
                                //если такой найден и он пустой - запуск функции движения к шкафу
                                if (i != numberShelf) con.toShelf(shelfList.get(i));
                                //если шкаф не найден или он не пустой
                                if (i == numberShelf || shelfList.get(i).getNumberGoods() != 0) {
                                    //обнуление номера консультанта
                                    if (i != numberShelf && shelfList.get(i).getNumberGoods() != 0) shelfList.get(i).setFilling(0);
                                    con.setStatus("place appear"); //возвращение на место появления
                                }
                                break;
                            }
                            case "place appear": {  //статус "место появления"
                                //запуск функции на возвращение в место появления консультанта
                                con.placeAppear(consultantList.indexOf(con));
                                break;
                            }
                        }
                    }
                }
            };
            timerConsultant.schedule(taskConsultant,0,con.getMovementSpeed());

        }

        public void search(Consultant con) {    //поиск работы консультанта
            int i = 0;
            //цикл для стеллажей
            while (i != numberShelf && (con.getStatus() == "wait" || con.getStatus() == "place")) {
                //если есть пустой стеллаж и им не занимается другой консультант
                if (shelfList.get(i).getNumberGoods() == 0 && shelfList.get(i).getFilling() == 0) {
                    con.setStatus("on stock");  //статус "на склад"
                    shelfList.get(i).setFilling(consultantList.indexOf(con) + 1);   //сохранение номера консультанта
                }
                i++;
            }
            i = 0;
            //цикл для покупателя
            while (i != customerList.size() && (con.getStatus() == "wait" || con.getStatus() == "place")) {
                //если покупателю нужна помощь и им не занимается другой консультант
                if (customerList.get(i).getNeedHelp() == true && customerList.get(i).getFilling() == 0) {
                    con.setStatus("help");  //статус "помощь"
                    customerList.get(i).setFilling(consultantList.indexOf(con) + 1);   //сохранение номера консультанта
                }
                i++;
            }
        }
}