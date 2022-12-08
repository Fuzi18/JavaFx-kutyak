module hu.petrik.adatbazis_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hu.petrik.adatbazis_javafx to javafx.fxml;
    exports hu.petrik.adatbazis_javafx;
}