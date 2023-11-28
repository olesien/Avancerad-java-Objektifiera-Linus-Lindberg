package edu.object.java23object;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class AddOrderController {
    Controller controller;

    @FXML
    private TextField orderDate;

    @FXML
    private TextField region;

    @FXML
    private TextField rep1;

    @FXML
    private TextField rep2;

    @FXML
    private TextField item;

    @FXML
    private TextField units;

    @FXML
    private TextField unitCost;

    @FXML
    private TextField total;
    void init(Controller controller) {
        this.controller = controller;
    }

    @FXML
    protected  void onSubmit() {
        String orderDate = this.orderDate.getText();

        String region = this.region.getText();

        String rep1 = this.rep1.getText();

        String rep2 = this.rep2.getText();

        String item = this.item.getText();

        String units = this.units.getText();

        String unitCost = this.unitCost.getText();

        String total = this.total.getText();

        //Check so all are non-empty
        if (orderDate.length() > 0 && region.length() > 0 && rep1.length() > 0 && rep2.length() > 0 && item.length() > 0
                && units.length() > 0 && unitCost.length() > 0 && total.length() > 0)
       controller.orderSubmit( new Order(orderDate, region, rep1, rep2, item, units, unitCost, total));
    }
}
