package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {
    public Label nameField;
    public Label surnameField;
    public Label usernameField;
    public Label passwordField;
    public Label emailField;
    private Professor professor;
    public ProfileController(Professor activeProfessor) {
        professor = activeProfessor;
    }

    @FXML
    public void initialize() {
        nameField.setText(professor.getName());
        surnameField.setText(professor.getSurname());
        usernameField.setText(professor.getUsername());
        passwordField.setText(professor.getPassword());
        emailField.setText(professor.getEmail());

    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) nameField.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changePassword.fxml"));
        PasswordController passwordController =new PasswordController(professor);
        loader.setController(passwordController);
        root=loader.load();
        stage.setTitle("Promijenite lozinku!");
        stage.setScene(new Scene(root, 1200, 500)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

}
