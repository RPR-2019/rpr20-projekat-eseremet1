package ba.unsa.etf.rpr.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NewProfessorController implements NewPassword {
    public TextField nameField;
    public TextField surnameField;
    public TextField usernameField;
    public PasswordField passwordField;
    public ChoiceBox<Subject> choiceSubject;
    private ObservableList<Subject> subjects;
    private MaterialManagementDAO materialManagementDAO;
    private Professor professor;
    public Button addBtn;

    public NewProfessorController(Professor professor, ArrayList<Subject> subjects) {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        this.professor = professor;
        this.subjects= FXCollections.observableArrayList(subjects);
    }

    @FXML
    public void initialize() {
        choiceSubject.setItems(subjects);
        choiceSubject.setValue(subjects.get(0));
        if(professor!=null) {
            nameField.setText(professor.getName());
            surnameField.setText(professor.getSurname());
            passwordField.setText(professor.getPassword());
            usernameField.setText(professor.getUsername());
            choiceSubject.getSelectionModel().select(professor.getSubject());
            addBtn.setText("Izmijeni");
        }
        surnameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (surnameField.getText().trim().isEmpty()) {
                    surnameField.getStyleClass().removeAll("poljeJeIspravno");
                    surnameField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    surnameField.getStyleClass().removeAll("poljeNijeIspravno");
                    surnameField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });
        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (nameField.getText().trim().isEmpty()) {
                    nameField.getStyleClass().removeAll("poljeJeIspravno");
                    nameField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    nameField.getStyleClass().removeAll("poljeNijeIspravno");
                    nameField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });
        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {

                if (usernameField.getText().trim().isEmpty()) {
                    usernameField.getStyleClass().removeAll("poljeJeIspravno");
                    usernameField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    usernameField.getStyleClass().removeAll("poljeNijeIspravno");
                    usernameField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });

        Tooltip toolTip1 = new Tooltip();
        toolTip1.setText("You must generate a password!");
        passwordField.setTooltip(toolTip1);

    }

    public void generateAction(ActionEvent actionEvent) {
        String newPassword=generatePassword();
        passwordField.setText(newPassword);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upozorenje!");
        alert.setHeaderText("Vaša lozinka glasi: ");
        alert.setContentText(newPassword);
        alert.showAndWait();
    }

    public void addAction(ActionEvent actionEvent) {
        boolean sveOk = true;
        if(nameField.getText().trim().isEmpty()) {
            nameField.getStyleClass().removeAll("poljeIspravno");
            nameField.getStyleClass().addAll("poljeNijeIspravno");
            sveOk=false;
        } else {
            nameField.getStyleClass().removeAll("poljeNijeIspravno");
            nameField.getStyleClass().add("poljeIspravno");
        }

        if(surnameField.getText().trim().isEmpty()) {
            surnameField.getStyleClass().removeAll("poljeIspravno");
            surnameField.getStyleClass().addAll("poljeNijeIspravno");
            sveOk=false;
        } else {
            surnameField.getStyleClass().removeAll("poljeNijeIspravno");
            surnameField.getStyleClass().add("poljeIspravno");
        }

        if(professor==null) {
            for (Professor oldProffesor : materialManagementDAO.professors()) {
                if (oldProffesor.getUsername().equals(usernameField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Username već postoji!");

                    alert.showAndWait();

                    sveOk = false;
                    break;

                }

            }

            for (Student oldStudent : materialManagementDAO.students()) {
                if (oldStudent.getUsername().equals(usernameField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Username već postoji!");

                    alert.showAndWait();

                    sveOk = false;
                    break;

                }

            }



        }

        if (!sveOk) return;

        if (professor== null) professor = new Professor();
        professor.setName(nameField.getText());
        professor.setSurname(surnameField.getText());
        professor.setUsername(usernameField.getText());
        professor.setPassword(passwordField.getText());
        professor.setEmail(usernameField.getText()+"@etf.unsa.ba");
        professor.setSubject(choiceSubject.getValue());
        if(professor.getPicture()==null || professor.getPicture()=="")
        professor.setPicture("");

        Stage stageClose = (Stage) nameField.getScene().getWindow();
        stageClose.close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
