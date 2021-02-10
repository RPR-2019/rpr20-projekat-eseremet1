package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

}
