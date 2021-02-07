package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {
    public TextField userNameField;
    public PasswordField passwordField;

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
}
