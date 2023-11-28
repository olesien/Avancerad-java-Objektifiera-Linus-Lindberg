module edu.object.java23object {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires org.json;


    opens edu.object.java23object to javafx.fxml;
    exports edu.object.java23object;
}