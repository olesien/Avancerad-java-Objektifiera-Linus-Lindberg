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
import java.nio.file.FileSystems;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    CsvReadWriter csvFileReader = new CsvReadWriter();

    ObservableList<Order> data = FXCollections.observableArrayList(
    );

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, String> orderDate;

    @FXML
    private TableColumn<Order, String> region;

    @FXML
    private TableColumn<Order, String> rep1;

    @FXML
    private TableColumn<Order, String> rep2;

    @FXML
    private TableColumn<Order, String> item;

    @FXML
    private TableColumn<Order, String> units;

    @FXML
    private TableColumn<Order, String> unitCost;

    @FXML
    private TableColumn<Order, String> total;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onAddDataClick() {
        System.out.println("Adding data");
        //data.add( new Order("Linus", "Lindberg", "linus-lindberg@outlook.com"));
        tableView.getItems().setAll(getData());
        String[] columns = {"firstName", "lastName", "email"};
        try {
            csvFileReader.saveCSV(columns, data);
        } catch (Exception err) {
            System.out.println(err);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderDate.setCellValueFactory(
                new PropertyValueFactory<Order,String>("orderDate")
        );
        region.setCellValueFactory(
                new PropertyValueFactory<Order,String>("region")
        );
        rep1.setCellValueFactory(
                new PropertyValueFactory<Order,String>("rep1")
        );

        rep2.setCellValueFactory(
                new PropertyValueFactory<Order,String>("rep2")
        );
        item.setCellValueFactory(
                new PropertyValueFactory<Order,String>("item")
        );
        units.setCellValueFactory(
                new PropertyValueFactory<Order,String>("units")
        );

        unitCost.setCellValueFactory(
                new PropertyValueFactory<Order,String>("unitCost")
        );
        total.setCellValueFactory(
                new PropertyValueFactory<Order,String>("total")
        );


        //Get data
        try {
            data = csvFileReader.parseLines(csvFileReader.readAllLines(FileSystems.getDefault().getPath("src", "data.csv")));
        } catch (Exception err) {
            System.out.println(err);
        }
        tableView.getItems().setAll(getData());

        JsonReadWrite json = new JsonReadWrite();
        System.out.println(json.read());

    }

    private List<Order> getData(){
        return data.stream().toList();
    }
}
