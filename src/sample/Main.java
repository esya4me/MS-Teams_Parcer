package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Application For MS Teams");
        Button button = new Button("Select File");

        Button button2 = new Button("Select File2");

       // button.setOnAction(e -> { File selectedFile = new File(fileChooser.showOpenDialog(primaryStage).);
       //     System.out.println(selectedFile);});
        TextField textField = new TextField();
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new
                        FileChooser.ExtensionFilter("CSV Files", "*.csv");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show open file dialog
                File file = fileChooser.showOpenDialog(null);

                String selectedFile = file.getPath();
                ReadCsvFromFile readCsvFromFile = new ReadCsvFromFile();
                readCsvFromFile.finalGrade(selectedFile);

                //ТЕСТ ДЛЯ LISTVIEW
                ObservableList<String> langs = FXCollections.observableArrayList(readCsvFromFile.finalGrade(selectedFile));
                ListView<String> langsListView = new ListView<String>(langs);

                FlowPane root = new FlowPane(langsListView);
                Scene scene1 = new Scene(root, 960, 600);
                primaryStage.setScene(scene1);
                primaryStage.show();

            }
        });


        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new
                        FileChooser.ExtensionFilter("CSV Files", "*.csv");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show open file dialog
                File file = fileChooser.showOpenDialog(null);

                String selectedFile = file.getPath();
                ReadCsvFromFile readCsvFromFile = new ReadCsvFromFile();
                readCsvFromFile.finalGrade(selectedFile);

                FinalGrade grade = new FinalGrade();
                //ТЕСТ ДЛЯ TABLEVIEW
                ObservableList<FinalGrade> listOfGrades = FXCollections.observableArrayList(
                       grade.getArrayOfFinalGrades(readCsvFromFile.finalGradeToArrayList(selectedFile))
                );

                // определяем таблицу и устанавливаем данные
                TableView<FinalGrade> table = new TableView<FinalGrade>(listOfGrades);
                table.setPrefWidth(450);
                table.setPrefHeight(400);

                // столбец для вывода имени
                TableColumn<FinalGrade, String> nameColumn = new TableColumn<FinalGrade, String>("Name");
                // определяем фабрику для столбца с привязкой к свойству name
                nameColumn.setCellValueFactory(new PropertyValueFactory<FinalGrade, String>("name"));
                // добавляем столбец
                table.getColumns().add(nameColumn);

                // столбец для вывода фамилии
                TableColumn<FinalGrade, String> lastNameColumn = new TableColumn<FinalGrade, String>("Last Name");
                // определяем фабрику для столбца с привязкой к свойству lastNameColumn
                lastNameColumn.setCellValueFactory(new PropertyValueFactory<FinalGrade, String>("lastName"));
                // добавляем столбец
                table.getColumns().add(lastNameColumn);

                // столбец для вывода итоговой оценки
                TableColumn<FinalGrade, String> gradeColumn = new TableColumn<FinalGrade, String>("Final Grade");
                // определяем фабрику для столбца с привязкой к свойству lastNameColumn
                gradeColumn.setCellValueFactory(new PropertyValueFactory<FinalGrade, String>("finalGrade"));
                // добавляем столбец
                table.getColumns().add(gradeColumn);

                FlowPane root = new FlowPane(table);
                Scene scene1 = new Scene(root, 960, 600);
                primaryStage.setScene(scene1);
                primaryStage.show();

            }
        });



        VBox vBox = new VBox(button, button2);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
