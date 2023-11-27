package edu.object.java23object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
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
        tableView.getItems().setAll(getData());
    }

    private List<Person> getData(){
        return data.stream().toList();
    }
}