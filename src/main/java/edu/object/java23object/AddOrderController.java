package edu.object.java23object;
import javafx.fxml.FXML;
public class AddOrderController {
    Controller controller;
    void init(Controller controller) {
        this.controller = controller;
    }

    @FXML
    protected  void onSubmit() {
       controller.orderSubmit( new Order("2/6/2019", "West", "Test2", "Test3", "Pillow", "10", "100", "99.1"));
    }
}
