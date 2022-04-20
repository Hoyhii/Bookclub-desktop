package com.example.bookclubdesktop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookclubController {
    @FXML
    private TableColumn<Member, Date> birthday;
    @FXML
    private TableColumn<Member, String> name;
    @FXML
    private TableColumn<Member, String> gender;
    @FXML
    private TableColumn<Member, Boolean> banned;
    @FXML
    private TableView<Member> members;
    private BookclubDB db;

    public void initialize(){
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday")); //getTitle()
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        banned.setCellValueFactory(new PropertyValueFactory<>("banned"));
        try {
            db = new BookclubDB();
            listaz();
        } catch (SQLException e) {
            Platform.runLater(() ->{
                hibaKiir(e);
                Stage stage = (Stage) members.getScene().getWindow();
                stage.close();
            });

        }
    }

    private void listaz() throws SQLException {
        List<Member> memberList = db.getMembers();
        members.getItems().clear();
        members.getItems().addAll(memberList);
    }

    private void hibaKiir(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.getClass().toString());
        alert.setHeaderText(e.getMessage());
        alert.setHeaderText(e.getMessage());
        alert.setContentText(e.getLocalizedMessage());
        alert.showAndWait();
    }


    @FXML
    public void tiltasClick(ActionEvent actionEvent) {
        if (members.getSelectionModel().getSelectedIndex() < 0){
            alert("Tiltások kezeléséhez először válasszon ki egy tagot!");
            return;
        }
        Member tilt = members.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos, hogy az adott tagot szeretné tiltani/feloldani?")){
            return;
        }
        try {
            if (db.bannable(tilt)){
                alert("Sikeres tiltás!");
            }else{
                alert("Ismeretlen hiba történt a tiltás során.");
            }
            listaz();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    private boolean confirm(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztos?");
        alert.setHeaderText(s);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Figyelem!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}