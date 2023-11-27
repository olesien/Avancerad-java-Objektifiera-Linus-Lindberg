package edu.object.java23object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {

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
    private TableView tableView;

    @FXML
    private TableColumn firstName;

    @FXML
    private TableColumn lastName;

    @FXML
    private TableColumn email;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onAddDataClick() {
        firstName.setCellValueFactory(
                new PropertyValueFactory<Person,String>("firstName")
        );
        lastName.setCellValueFactory(
                new PropertyValueFactory<Person,String>("lastName")
        );
        email.setCellValueFactory(
                new PropertyValueFactory<Person,String>("email")
        );
        System.out.println("Adding data");

    }

    /*HelloController() {
        System.out.println("INIT");
    }*/
}