package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class AddProfessorController {
    public TextField nameField;
    public TextField surnameField;
    public TextField usernameField;
    public PasswordField passwordField;
    public ChoiceBox<Subject> choiceSubject;
    private ObservableList<Subject> subjects;
    private MaterialManagementDAO materialManagementDAO;
    private Professor professor;
    public Button addBtn;

    public AddProfessorController(Professor professor, ArrayList<Subject> subjects) {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        this.professor = professor;
        this.subjects= FXCollections.observableArrayList(subjects);
    }

    @FXML
    public void initialize() {
        choiceSubject.setItems(subjects);
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

    }

    public void generateAction(ActionEvent actionEvent) {
        String newPassword="";
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        while(true) {
            StringBuilder salt = new StringBuilder();
            Random random = new Random();
            //generise neko od 8 cifara
            while (salt.length() < 7) { //zbog uslova
                int index = (int) (random.nextFloat() * possibleCharacters.length());
                salt.append(possibleCharacters.charAt(index));
            }
            newPassword = salt.toString();


            boolean small=false, uppercase = false, number = false;
            //a sadrži barem jedno veliko slovo, jedno malo slovo i jednu cifru

            for(int i=0; i<newPassword.length(); i++) {
                if(newPassword.charAt(i)>= 'A' && newPassword.charAt(i)<= 'Z') {
                    uppercase=true;
                }
                else if(newPassword.charAt(i)>= 'a' && newPassword.charAt(i)<= 'z') {
                    small=true;
                }
                else if(newPassword.charAt(i)>= '0' && newPassword.charAt(i)<= '9') {
                    number=true;
                }
                else continue;
            }

            if(uppercase && small && number) break;
        }
        String specijalni="!#$%&/()=?*~";
        Random random = new Random();
        int index=(int) (random.nextFloat() * specijalni.length());

        newPassword+=possibleCharacters.charAt(index);


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
