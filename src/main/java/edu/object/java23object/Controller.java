package edu.object.java23object;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Reader;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import com.opencsv.CSVReader;

public class Controller implements Initializable {
    CsvFileReader csvFileReader = new CsvFileReader();

    ObservableList<Person> data = FXCollections.observableArrayList(
    );

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Person> tableView;

    @FXML
    private TableColumn<Person, String> firstName;

    @FXML
    private TableColumn<Person, String> lastName;

    @FXML
    private TableColumn<Person, String> email;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onAddDataClick() {
        System.out.println("Adding data");
        data.add( new Person("Linus", "Lindberg", "linus-lindberg@outlook.com"));
        tableView.getItems().setAll(getData());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstName.setCellValueFactory(
                new PropertyValueFactory<Person,String>("firstName")
        );
        lastName.setCellValueFactory(
                new PropertyValueFactory<Person,String>("lastName")
        );
        email.setCellValueFactory(
                new PropertyValueFactory<Person,String>("email")
        );


        //Get data
        try {
            data = csvFileReader.parseLines(csvFileReader.readAllLines(FileSystems.getDefault().getPath("src", "data.csv")));
        } catch (Exception err) {
            System.out.println(err);
        }
        tableView.getItems().setAll(getData());

    }

    private List<Person> getData(){
        return data.stream().toList();
    }
}
