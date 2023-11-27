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
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import com.opencsv.CSVReader;

public class HelloController implements Initializable {

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
            parseLines(readAllLines(FileSystems.getDefault().getPath("src", "data.csv")));
        } catch (Exception err) {
            System.out.println(err);
        }
        tableView.getItems().setAll(getData());

    }

    private List<Person> getData(){
        return data.stream().toList();
    }

    public void parseLines (List<String[]> lines) {
        ObservableList<Person> newData = FXCollections.observableArrayList();

        int firstNameIndex = 0;
        int lastNameIndex = 1;
        int emailIndex = 1;
        for (int rowI = 0; rowI < lines.size(); rowI++) {
            String[] line = lines.get(rowI);
            if (rowI == 0) {

                //Get columns and map them with their indexes.
                for (int colI = 0; colI < line.length; colI++) {
                    String value = line[colI];
                    switch (value) {
                        case "FirstName":
                            firstNameIndex = colI;
                        case "LastName":
                            lastNameIndex = colI;
                        case "Email":
                            emailIndex = colI;
                    }
                }
            } else {
                //Map rows
                String firstName = "";
                String lastName = "";
                String email = "";

                for (int colI = 0; colI < line.length; colI++) {
                    String value = line[colI];
                    if (firstNameIndex == colI) {
                        firstName = value;
                    }
                    if (lastNameIndex == colI) {
                        lastName = value;
                    }
                    if (emailIndex == colI) {
                        email = value;
                    }
                }
                newData.add(new Person(firstName, lastName, email));
            }

        }
        data = newData;
    }

    public List<String[]> readAllLines(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            List<String[]> lines =  csvReader.readAll();
            csvReader.close();
            return lines;
        }
    }
}
