
package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class Main extends Application {
    private final FileChooser fileChooser = new FileChooser();
    private final ReadCsvFromFile readCsvFromFile = new ReadCsvFromFile();
    private final Button toGetFinalGradeButton =
            new Button("Рассчитать итоговую оценку");
    private final Button toGetAttendanceButton = new Button("Получить список присутствующих");
    private final Button toSendEmailsButton = new Button("Отправить уведомления на E-mail");
    private final Button menuButton = new Button("НАЖМИТЕ ДЛЯ ВЫХОДА В МЕНЮ");

    /**
     * Функция получения списка расширений (.csv)
     */
    private void setExtensionFilter() {
        FileChooser.ExtensionFilter extFilter = new
                FileChooser.ExtensionFilter("CSV Files" , "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    /**
     * Функция создания кнопки для выхода в меню (Работает с любым узлом Scene)
     * @param object - принимает любой объект типа Node
     *               например, TableView, BorderPane, FlowPane
     *               соответственно - метод универсален
     * @return - возвращает объект типа BorderPain, находящийся справа внизу
     *          кнопка - (НАЖМИТЕ ДЛЯ ВЫХОДА В МЕНЮ)
     */
    private BorderPane toGetMenuButton(Node object) {
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

    /**
     * Функция получения сцены главного меню
     * @return - возвращает объект Scene
     *          (совокупность всех объектов на одном)
     */
    private Scene toGetMenuScene() {
        //Добавляем кнопки toGetFinalGradeButton, toGetAttendanceButton, toSendEmailsButton
        //и задаем всем размер с шириной = 300 и высотой = 50
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

    /**
     * Функция выдает предупреждение о том, что файл не выбран
     */
    private void toShowAlertMessageNullPointerException() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Выбор файла");
        alert.setHeaderText("Вы не выбрали файл!");
        alert.showAndWait();
    }

    /**
     * Функция выдает предупреждение о том, что выбран неподходящий файл
     * Например, вы выбрали файл с посещениями вместо файла с оценками
     *  Исключение возникает тогда, когда мы пытаемся обратиться к элементу массива
     *  по отрицательному или превышающему размер массива индексу.
     */
    private void toShowAlertMessageArrayIndexOutOfBoundsException() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Выбор файла");
        alert.setHeaderText("Выбран неподходящий файл!");
        alert.showAndWait();
    }

    /**
     * Функция добавляет столбец к таблице TableView<FinalGrade>
     * @param table - таблица, к которой будет добавлен столбец
     * @param columnName - имя столбца
     * @param nameOfProperty - имя свойства, по которому будет выбран столбец
     */
    private void toSetTableColumnFinalGrade(TableView<FinalGrade> table, String columnName, String nameOfProperty) {
        //Создаем столбец, в который будем записывать
        TableColumn<FinalGrade, String> nameOfColumn = new TableColumn<>(columnName);
        //Определяем фабрику для столбца с привязкой к свойству
        nameOfColumn.setCellValueFactory(new PropertyValueFactory<>(nameOfProperty));
        //Добавляем столбец в таблицу
        table.getColumns().add(nameOfColumn);
    }

    private void toSetTableColumnAttendance(TableView<Attendance> table, String columnName, String nameOfProperty) {
        //Создаем столбец, в который будем записывать
        TableColumn<Attendance, String> nameOfColumn = new TableColumn<>(columnName);
        //Определяем фабрику для столбца с привязкой к свойству
        nameOfColumn.setCellValueFactory(new PropertyValueFactory<>(nameOfProperty));
        //Добавляем столбец в таблицу
        table.getColumns().add(nameOfColumn);
    }

    @Override
    public void start(Stage primaryStage) throws NullPointerException{
        primaryStage.setTitle("Application For MS Teams");
//==============================КНОПКИ===============================================

        //Логика кнопки menuButton
        menuButton.setOnAction(event -> {
            primaryStage.setScene(toGetMenuScene());
            primaryStage.show();
        } );

        //Логика кнопки toGetFinalGradeButton
        toGetFinalGradeButton.setOnAction(event -> {
            try {
                //Задаем фильтр для поиска расширений
                setExtensionFilter();

                //Открываем окно выбора файлов
                File file = fileChooser.showOpenDialog(null);

                String selectedFile = file.getPath();

                FinalGrade grade = new FinalGrade();

                ObservableList<FinalGrade> listOfGrades = FXCollections.observableArrayList(
                        grade.getArrayOfFinalGrades(readCsvFromFile.finalGradeToArrayList(selectedFile))
                );
                // определяем таблицу и устанавливаем данные
                TableView<FinalGrade> table = new TableView<>(listOfGrades);
                table.setPrefWidth(450);
                table.setPrefHeight(400);

                toSetTableColumnFinalGrade(table,"Имя","name");
                toSetTableColumnFinalGrade(table,"Фамилия","lastName");
                toSetTableColumnFinalGrade(table,"Итоговая оценка","finalGrade");

                Scene scene1 = new Scene(toGetMenuButton(table), 960, 600);
                primaryStage.setScene(scene1);
                primaryStage.show();
            }
            //Отлавливаем ошибки, когда пользователь не выбрал файл
            catch (NullPointerException e){
                //Выдаем предупреждение из метода:
                toShowAlertMessageNullPointerException();
            }
            //Отлавливаем ошибки, связанные с русским названием файла
            catch (ArrayIndexOutOfBoundsException e){
                //Выдаем предупреждение из метода:
                toShowAlertMessageArrayIndexOutOfBoundsException();
            }
        });

        //Логика кнопки toGetAttendanceButton
        toGetAttendanceButton.setOnAction(event -> {
            try {
                setExtensionFilter();
                File file = fileChooser.showOpenDialog(null);
                String selectedFile = file.getPath();
                Attendance attendance = new Attendance();


                ObservableList<Attendance> listOfAttendance = FXCollections.observableArrayList(
                        attendance.getArrayOfAttendance(readCsvFromFile.toReadFullFileTest(selectedFile))
                );
                TableView<Attendance> attendanceTable = new TableView<>(listOfAttendance);
                attendanceTable.setPrefWidth(450);
                attendanceTable.setPrefHeight(400);

                toSetTableColumnAttendance(attendanceTable , "Полное имя" , "fullName");
                toSetTableColumnAttendance(attendanceTable , "Время присутствия" , "timeOfAction");

                Scene scene1 = new Scene(toGetMenuButton(attendanceTable) , 960 , 600);
                primaryStage.setScene(scene1);
                primaryStage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            //Отлавливаем ошибки, когда пользователь не выбрал файл
            catch (NullPointerException e){
                    //Выдаем предупреждение из метода:
                    toShowAlertMessageNullPointerException();
                }
                //Отлавливаем ошибки, связанные с неправильным файлом
            catch (IndexOutOfBoundsException e){
                    //Выдаем предупреждение из метода:
                    toShowAlertMessageArrayIndexOutOfBoundsException();
                }
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