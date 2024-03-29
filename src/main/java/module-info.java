module org.example.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires junit;


    opens org.example.weather to javafx.fxml;
    exports org.example.weather;
}