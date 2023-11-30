package edu.object.java23object;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Controller {

    CsvReadWriter csvFileReader = new CsvReadWriter();
    TableData data = new TableData();
    JsonReadWrite json = new JsonReadWrite();

    Path currentPath = new File("").toPath(); //Make a dummy path

    @FXML
    private TableView<ObservableList> tableView;

    @FXML
    private Button setFileBtn;

    @FXML
    private Button addDataBtn;

    Alert alert = new Alert(Alert.AlertType.NONE); //Alert for error

    Stage stage;

    @FXML
    protected void onAddDataClick() {
        try {
            //Make a new screen!

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add.fxml"));
            Parent parent = loader.load();//Load the fxml

            AddController controller = loader.getController(); //Get controller ref before scene is made
            Scene scene = new Scene(parent, 620, 540); //Based on the loaded fxml, set the scene
            Stage stage = new Stage(); //Create the new window
            stage.setScene(scene);
            controller.init(this, stage, data.getColumns()); //Initialize the controller code, this is to load the things that are supposed to happen after start
            stage.setTitle("Form");
            stage.show();
        } catch (IOException e) {
            System.out.println("Something went REALLy wrong");
        }
    }

    public void submitForm(ObservableList<String> formData) {
        data.addRow(formData);
        refresh();
        save();
    }
    @FXML
    protected void onSetTile() { //This is triggered when we click to choose a file
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

            setFileBtn.setText(file.getName());
            stage.setTitle("Inspecting " + file.getName());
            Path path = file.toPath();
            
            String[] arr = file.getName().split("\\.");
            String extension = arr[arr.length - 1];
            if (extension.contains("csv")) {
                System.out.println("We have a CSV file");
                // we have a csv
                try {
                    data = csvFileReader.parseLines(csvFileReader.readAllLines(path));
                    System.out.println(data.getRows());
                } catch (Exception err) {
                    //Display error
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong reading the CSV");
                    alert.show();
                    System.out.println(err);
                }
            } else if (extension.contains("json")) {
                System.out.println("We have a JSON file");
                try {
                    data = json.read(path);
                } catch (Exception err) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong reading the JSON");
                    alert.show();
                    System.out.println(err);
                }

            } else {
                System.out.println("Invalid File");
            }
            this.currentPath = path; //Set current path
            addDataBtn.setDisable(false); //Since we have data we can enable this
            refresh();
        }

    }

    public void refresh() {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        ArrayList<String> cols = data.getColumns();
            for(int i=0 ; i< cols.size(); i++) {
                final int j = i; //So that we can reuse
                TableColumn newCol = new TableColumn(cols.get(i));
                newCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                        new SimpleStringProperty(param.getValue().get(j).toString()));
                tableView.getColumns().add(newCol);
            }
            if (!data.getColumns().isEmpty() && !data.getRows().isEmpty()) { //If it is now empty we can remove
                TableColumn remove = new TableColumn("Remove");
                remove.setCellFactory(
                        (Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>) p -> new ButtonCell());
                tableView.getColumns().add(remove);
            }

        //Dynamic Array
        ObservableList<ObservableList> rowData = FXCollections.observableArrayList();
        data.getRows().forEach(row -> rowData.add(row));
        tableView.getItems().setAll(rowData); //Refresh
    }

    public void save() {
        String pathString = currentPath.toString();
        try {
            if (pathString.contains(".csv")) {
                csvFileReader.saveCSV(currentPath, data);
            } else if (pathString.contains(".json")) {
                json.saveJSON(currentPath, data);
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
                        ObservableList current = tableView.getItems().get(selectedIndex);
                        //remove selected item from the table list
                        data.removeByRow(current);
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
