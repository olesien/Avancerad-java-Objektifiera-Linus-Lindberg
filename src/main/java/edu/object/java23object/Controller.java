package edu.object.java23object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    CsvReadWriter csvFileReader = new CsvReadWriter();
    JsonReadWrite json = new JsonReadWrite();

    ObservableList<Order> data = FXCollections.observableArrayList(
    );

    Path currentPath = new File("").toPath(); //Make a dummy path

    String[] columns = {"OrderDate", "Region", "Rep1", "Rep2", "Item", "Units", "UnitCost", "Total"};

    @FXML
    private TableView<Order> tableView;

    private ArrayList<TableColumn<Record, Boolean>> cols = new ArrayList<TableColumn<Record, Boolean>>();

    @FXML
    private TableColumn<Record, Boolean> remove;

    @FXML
    private Button setFileBtn;

    Stage stage;

    @FXML
    protected void onAddDataClick() {
        try {
            //Make a new screen!

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addorder.fxml"));
            Parent parent = loader.load();//Load the fxml

            AddOrderController controller = loader.getController(); //Get controller ref before scene is made
            Scene scene = new Scene(parent, 620, 540); //Based on the loaded fxml, set the scene
            Stage stage = new Stage(); //Create the new window
            stage.setScene(scene);
            controller.init(this, stage); //Initialize the controller code, this is to load the things that are supposed to happen after start
            stage.setTitle("Form");
            stage.show();
        } catch (IOException e) {
            System.out.println("Something went REALLy wrong");
        }
    }

    public void orderSubmit(Order newOrder) {
        data.add(newOrder);
        refresh();
        save();
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
            refresh();
        }

    }

    public void refresh() {
        tableView.getColumns().removeAll();
        for (String column : columns) {
            TableColumn newCol = new TableColumn(column);
            newCol.setCellValueFactory(
                    new PropertyValueFactory<Record,String>(column)
            );
            tableView.getColumns().add(newCol);
        }
        //Add remove one
        TableColumn remove = new TableColumn("Remove");
        remove.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

                    @Override
                    public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                        return new ButtonCell();
                    }

                });
        tableView.getColumns().add(remove);

        tableView.getItems().setAll(getData()); //Refresh
    }

    public void save() {
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

    public void init () {
        refresh();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private List<Order> getData(){
        return data.stream().toList();
    }

    //Define the button cell. Solution credit: https://gist.github.com/abhinayagarwal/9735744 to delete row by cell
    private class ButtonCell extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("Delete");

        ButtonCell(){

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    // get Selected Item
                    System.out.println("Deleting");
                    int selectedIndex = getTableRow().getIndex();
                    if (selectedIndex > -1) {
                        Order currentOrder = tableView.getItems().get(selectedIndex);
                        //remove selected item from the table list
                        data.remove(currentOrder);
                        refresh();
                        save();
                    }

                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (empty || getTableRow() == null || getTableRow().getIndex() >= tableView.getItems().size()) {
                setGraphic(null);
            } else {
                setGraphic(cellButton);
            }
        }
    }
}
