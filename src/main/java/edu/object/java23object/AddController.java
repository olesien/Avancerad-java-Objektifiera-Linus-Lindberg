package edu.object.java23object;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddController {
    Controller controller;

    @FXML
    private VBox FormInputs;

    ObservableList<String> formData = FXCollections.observableArrayList();

    Stage stage;
    void init(Controller controller, Stage stage, ArrayList<String> cols) {
        for (int i = 0; i < cols.size(); i++) {
            String col = cols.get(i);
            Label newLabel = new Label(col);
            newLabel.prefHeight(23.0);
            newLabel.prefWidth(74.0);
            newLabel.setPadding(new Insets(2, 2, 2,2));
            TextField newField = new TextField("");
            int finalI = i;
            newField.textProperty().addListener((observable, oldValue, newValue) -> {
                formData.set(finalI, newValue);
            });
            FormInputs.getChildren().addAll(newLabel, newField);
            formData.add("");
        }
        this.controller = controller;
        this.stage = stage;
    }

    @FXML
    protected  void onSubmit() {
        controller.submitForm(formData); //Submit form
        formData = FXCollections.observableArrayList(); //Clear array
        stage.close();

    }
}
