package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;


public class Main extends Application {
    private final FileChooser fileChooser = new FileChooser();
    private final ReadCsvFromFile readCsvFromFile = new ReadCsvFromFile();
    private final Button toGetFinalGradeButton =
                                            new Button("Рассчитать итоговую оценку");
    private final Button toGetAttendanceButton = new Button("Получить список присутствующих");
    private final Button toSendEmailsButton = new Button("Отправить уведомления на E-mail");
    private final Button menuButton = new Button("НАЖМИТЕ ДЛЯ ВЫХОДА В МЕНЮ");

    private void setExtensionFilter() {
        FileChooser.ExtensionFilter extFilter = new
                FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    //Функция возвращает объект BorderPane и работает для любого узла Scene
    //Другими словами, мы можем отображать кнопку menuButton с любыми узлами сцены,
    //TableView, BorderPane, FlowPane и т.д., соответственно метод - универсален
    private BorderPane toGetMenuButton(Node object){
        //BorderPane для всех функций
        BorderPane root = new BorderPane();
        BorderPane bottomBorder = new BorderPane();
        root.setCenter(object);
        //Задаем кнопке menuButton правое расположение в объекте bottomBorder
        bottomBorder.setRight(menuButton);
        //Задаем bottomBorder расположение по низу в объекте root
        root.setBottom(bottomBorder);
        //место между элементами и окном borderpane
        root.setPadding(new Insets(20));
        return root;
    }

    private Scene toGetMenuScene() {
        for (Button button : Arrays.asList(toGetFinalGradeButton, toGetAttendanceButton, toSendEmailsButton)) {
            button.setPrefSize(300,50);
        }

        HBox buttonBox1 = new HBox(toGetFinalGradeButton);
        HBox buttonBox2 = new HBox(toGetAttendanceButton);
        HBox buttonBox3 = new HBox(toSendEmailsButton);

        buttonBox1.setPadding(new Insets(20));
        buttonBox2.setPadding(new Insets(20));
        buttonBox3.setPadding(new Insets(20));

        VBox vBox = new VBox(buttonBox1, buttonBox2, buttonBox3);
        //НАСТРОИТЬ ФОНОВУЮ КАРТИНКУ
        vBox.setStyle(
                "-fx-background-image: url(/img/bg_image.jpg); " +
                        "-fx-background-repeat: no-repeat;"
        );
        return new Scene(vBox, 960, 600);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Application For MS Teams");
//==============================КНОПКИ===============================================
        //Читаем картинку в поток с помощью InputStream и записываем в input
        InputStream inputImgForMenu =
                getClass().getResourceAsStream("/img/");
        Image image = new Image(inputImgForMenu);
        ImageView imageForMenuButton = new ImageView(image);

        //Назначаем на кнопку картинку
        menuButton.setGraphic(imageForMenuButton);
        //Логика кнопки menuButton
        menuButton.setOnAction(event -> {

            primaryStage.setScene(toGetMenuScene());
            primaryStage.show();
        } );

        //Логика кнопки toGetFinalGradeButton
        toGetFinalGradeButton.setOnAction(event -> {
            //Задаем фильтр для поиска расширений
            setExtensionFilter();

            //Открываем окно выбора файлов
            File file = fileChooser.showOpenDialog(null);

            String selectedFile = file.getPath();

            FinalGrade grade = new FinalGrade();
            //ТЕСТ ДЛЯ TABLEVIEW
            ObservableList<FinalGrade> listOfGrades = FXCollections.observableArrayList(
                   grade.getArrayOfFinalGrades(readCsvFromFile.finalGradeToArrayList(selectedFile))
            );
            // определяем таблицу и устанавливаем данные
            TableView<FinalGrade> table = new TableView<>(listOfGrades);
            table.setPrefWidth(450);
            table.setPrefHeight(400);

            // столбец для вывода имени
            TableColumn<FinalGrade, String> nameColumn = new TableColumn<>("Имя");
            // определяем фабрику для столбца с привязкой к свойству name
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            // добавляем столбец
            table.getColumns().add(nameColumn);

            // столбец для вывода фамилии
            TableColumn<FinalGrade, String> lastNameColumn = new TableColumn<>("Фамилия");
            // определяем фабрику для столбца с привязкой к свойству lastNameColumn
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            // добавляем столбец
            table.getColumns().add(lastNameColumn);

            // столбец для вывода итоговой оценки
            TableColumn<FinalGrade, String> gradeColumn = new TableColumn<>("Итоговая оценка");
            // определяем фабрику для столбца с привязкой к свойству lastNameColumn
            gradeColumn.setCellValueFactory(new PropertyValueFactory<>("finalGrade"));
            // добавляем столбец
            table.getColumns().add(gradeColumn);

            Scene scene1 = new Scene(toGetMenuButton(table), 960, 600);
            primaryStage.setScene(scene1);
            primaryStage.show();
        });

        //Логика кнопки toGetAttendanceButton
        toGetAttendanceButton.setOnAction(event -> {
            //TODO
        });

        //Логика кнопки toSendEmailsButton
        toSendEmailsButton.setOnAction(event -> {
            //TODO
        });

        primaryStage.setScene(toGetMenuScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
