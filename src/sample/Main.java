package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Application For MS Teams");
        Button button = new Button("Select File");

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


        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
