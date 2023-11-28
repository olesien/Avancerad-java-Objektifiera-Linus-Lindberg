package edu.object.java23object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Controller {

    CsvReadWriter csvFileReader = new CsvReadWriter();
    JsonReadWrite json = new JsonReadWrite();

    ObservableList<Order> data = FXCollections.observableArrayList(
    );

    Path currentPath = new File("").toPath(); //Make a dummy path

    String[] columns = {"OrderDate", "Region", "Rep1", "Rep2", "Item", "Units", "UnitCost", "Total"};

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
    private Button setFileBtn;

    Stage stage;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onAddDataClick() {
        System.out.println("Adding data");
        data.add( new Order("2/6/2019", "West", "Test2", "Test3", "Pillow", "10", "100", "99.1"));
        tableView.getItems().setAll(getData());
        String pathString = currentPath.toString();
        try {
            if (pathString.contains(".csv")) {
                csvFileReader.saveCSV(currentPath, columns, data);
            } else if (pathString.contains(".json")) {
                json.saveJSON(currentPath, columns, data);
            } else {
                System.out.println("Can not save because file not found");
            }
        } catch (Exception err) {
            System.out.println(err);
        }


    }

    @FXML
    protected void onSetTile() {
        //init file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src"));
        FileChooser.ExtensionFilter csvF = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter jsonF = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().addAll(csvF, jsonF);
        fileChooser.setTitle("Open CSV/JSON file");
        File file = fileChooser.showOpenDialog(stage.getScene().getWindow());
        if (file != null) {
            //Handle file add event

            setFileBtn.setText(file.getPath());
            Path path = file.toPath();
            String fileName = file.getPath();
            if (fileName.contains(".csv")) {
                System.out.println("We have a CSV file");
                // we have a csv
                try {
                    data = csvFileReader.parseLines(csvFileReader.readAllLines(path));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (fileName.contains(".json")) {
                System.out.println("We have a JSON file");
                data = json.read(path);
                //We have a json
            } else {
                System.out.println("Invalid File");
            }
            this.currentPath = path;
            tableView.getItems().setAll(getData()); //Refresh
        }

    }

    public void init () {
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

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private List<Order> getData(){
        return data.stream().toList();
    }
}
