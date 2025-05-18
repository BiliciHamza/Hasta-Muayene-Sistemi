module com.example.hastakayit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hastakayit to javafx.fxml;
    exports com.example.hastakayit;
}