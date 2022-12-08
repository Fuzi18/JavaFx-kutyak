package hu.petrik.adatbazis_javafx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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


    public void initialize(){
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agecol.setCellValueFactory(new PropertyValueFactory<>("age"));
        breedcol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageinput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0));
        try{
            db = new DogDB();
            readDogs();
        }catch (SQLException e){
            Platform.runLater(() -> {
                sqlAlert(e);
                Platform.exit();
            });
        }
    }

    private void sqlAlert(SQLException e) {
        alert(Alert.AlertType.ERROR, "Hiba történt", e.getMessage());
    }

    private void readDogs() throws SQLException {
        List<Dog> dogs = db.readDogs();
        dogtable.getItems().clear();
        dogtable.getItems().addAll(dogs);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }


    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name= nameinput.getText().trim();
        int age = ageinput.getValue();
        String breed= breedinput.getText().trim();
        if (name.isEmpty()){
            alert(Alert.AlertType.WARNING, "Név megadása kötelező", "");
            return;
        }
        if (breed.isEmpty()){
            alert(Alert.AlertType.WARNING, "Fajta megadása kötelező", "");
            return;
        }
        Dog dog = new Dog(name, age, breed);
        try{
            if (db.createDog(dog)){
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
            }else{
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
        }catch (SQLException e){
            sqlAlert(e);
        }

    }

    private void resetForm() {
        nameinput.setText("");
        ageinput.getValueFactory().setValue(0);
        breedinput.setText("");
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectIndex = dogtable.getSelectionModel().getSelectedIndex();
        if (selectIndex == -1){
            alert(Alert.AlertType.WARNING, "Törléshez válasszon ki kutyát", "");
            return;
        }
        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION, "Biztos hogy törölni akarod fasz?" , "");
        if (optionalButtonType.isEmpty() || (!optionalButtonType.get().equals(ButtonType.OK) && !optionalButtonType.get().equals(ButtonType.YES))) {
            return;
        }
        Dog selected = dogtable.getSelectionModel().getSelectedItem();
        try {
            if (db.deleteDogs(selected.getId())){
                alert(Alert.AlertType.WARNING, "Sikeres törlés tesomsz", "");
            }else{
                alert(Alert.AlertType.WARNING, "Siketelen törlés történt kuvasz", "");
            }
            readDogs();
        }catch (SQLException e){
            sqlAlert(e);

        }
    }

    @FXML
    public void cancelClick(ActionEvent actionEvent) {
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
    }
}