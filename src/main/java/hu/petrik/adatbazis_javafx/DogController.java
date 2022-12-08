package hu.petrik.adatbazis_javafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class DogController {

    @FXML
    private TableColumn<Dog, Integer> agecol;
    @FXML
    private TableColumn<Dog, String> namecol;
    @FXML
    private TableColumn<Dog, String> breedcol;
    @FXML
    private TableView<Dog> dogtable;
    private DogDB db;
    @FXML
    private Button deleteButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Spinner<Integer> ageinput;
    @FXML
    private TextField breedinput;
    @FXML
    private TextField nameinput;
    @FXML
    private Button updateButton;

    @Deprecated
    private void initialize(){
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agecol.setCellValueFactory(new PropertyValueFactory<>("age"));
        breedcol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageinput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
        try{
            db = new DogDB();
            readDogs();
        }catch (SQLException e){
            Platform.runLater(() -> {
                alert(Alert.AlertType.ERROR, "Hiba történt", e.getMessage());
                Platform.exit();
            });
        }
    }

    private void readDogs() throws SQLException {
        List<Dog> dogs = db.readDogs();
        dogtable.getItems().clear();
        dogtable.getItems().addAll(dogs);
    }

    private void alert(Alert.AlertType alertType, String headerText, String contentText){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    @FXML
    public void submitClick(ActionEvent actionEvent) {
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
    }

    @FXML
    public void cancelClick(ActionEvent actionEvent) {
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
    }
}