package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    public TextField userNameField;
    public PasswordField passwordField;
    public ProfessorDAO professor;

    @FXML
    public void initialize() {
        userNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (userNameField.getText().trim().isEmpty()) {
                    userNameField.getStyleClass().removeAll("poljeJeIspravno");
                    userNameField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    userNameField.getStyleClass().removeAll("poljeNijeIspravno");
                    userNameField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });

        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (passwordField.getText().trim().isEmpty()) {
                    passwordField.getStyleClass().removeAll("poljeJeIspravno");
                    passwordField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    passwordField.getStyleClass().removeAll("poljeNijeIspravno");
                    passwordField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });
    }

    public void logInAction(ActionEvent actionEvent) throws IOException {
        if (userNameField.getText().trim().isEmpty()) {
            userNameField.getStyleClass().addAll("poljeNijeIspravno");
        }
        if (passwordField.getText().trim().isEmpty()) {
            passwordField.getStyleClass().addAll("poljeNijeIspravno");
        }

        if (userNameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Obavijest");
            alert.setHeaderText(null);
            alert.setContentText("Uspješno ste prijavljeni kao administrator!");
            alert.showAndWait();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/homeAdmin.fxml"));
            stage.setTitle("Upravljanje korisnicima");
            stage.setScene(new Scene(root, 1200, 700));
            stage.setResizable(false);
            stage.show();
            Stage stageClose = (Stage) userNameField.getScene().getWindow();
            stageClose.close();
        } else if (professor.searchProfessor(userNameField.getText()) != null) {
            if (professor.searchProfessor(userNameField.getText()).getPassword().equals(passwordField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Obavijest");
                alert.setHeaderText(null);
                alert.setContentText("Uspješno ste prijavljeni kao profesor");

                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Pogrešna lozinka. Pokušajte ponovo!");

                alert.showAndWait();
            }
        }
    }
}
