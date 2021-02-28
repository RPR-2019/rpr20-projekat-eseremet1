package ba.unsa.etf.rpr.project;

import javafx.application.Platform;
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
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class LogInController {
    public TextField userNameField;
    public PasswordField passwordField;
    private MaterialManagementDAO materialManagementDAO;

    public LogInController() {
        materialManagementDAO = MaterialManagementDAO.getInstance();


    }
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

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Tooltip toolTip = new Tooltip();
        Tooltip toolTip1 = new Tooltip();
        if(bundle.getLocale().toString().equals("bs")) {
            toolTip.setText("Lozinka");
            toolTip1.setText("Username");
        } else {
            toolTip.setText("Password field");
            toolTip1.setText("Username field");
        }
        passwordField.setTooltip(toolTip);
        userNameField.setTooltip(toolTip1);
    }

    public void logInAction(ActionEvent actionEvent) throws IOException {
        if (userNameField.getText().trim().isEmpty()) {
            userNameField.getStyleClass().addAll("poljeNijeIspravno");
        }
        if (passwordField.getText().trim().isEmpty()) {
            passwordField.getStyleClass().addAll("poljeNijeIspravno");
        }

        if (userNameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
            Stage stage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/homeAdmin.fxml" ), bundle);
            Parent root = loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Početna stranica");
            } else {
                stage.setTitle("Home");
            }
            stage.setScene(new Scene(root, 1200, 700));
            stage.setResizable(false);
            stage.show();
            Stage stageClose = (Stage) userNameField.getScene().getWindow();
            stageClose.close();
        } else if (materialManagementDAO.searchProfessor(userNameField.getText()) != null) {
            if (materialManagementDAO.searchProfessor(userNameField.getText()).getPassword().equals(passwordField.getText())) {
                Stage stage = new Stage();
                Parent root = null;
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeProfessor.fxml"), bundle);
                HomeProfessorController professorContoller=new HomeProfessorController(materialManagementDAO.searchProfessor(userNameField.getText()));
                loader.setController(professorContoller);
                root=loader.load();
                if(bundle.getLocale().toString().equals("bs")) {
                    stage.setTitle("Početna stranica");
                } else {
                    stage.setTitle("Home");
                }
                stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
                stage.setMinHeight(500); //da se ne može više smanjivati
                stage.setMinWidth(200);
                stage.show();
                Stage stageClose = (Stage) userNameField.getScene().getWindow();
                stageClose.close();

            } else {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Stage stage = (Stage) userNameField.getScene().getWindow();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(bundle.getLocale().toString().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Pogrešna lozinka. Pokušajte ponovo!");
                } else {
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong password. Please try again!");
                }
                alert.showAndWait();
            }
        } else if (materialManagementDAO.searchStudent(userNameField.getText()) != null) {
            if (materialManagementDAO.searchStudent(userNameField.getText()).getPassword().equals(passwordField.getText())) {
                Stage stage = new Stage();
                Parent root = null;
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeStudent.fxml"), bundle);
                HomeStudentController studentController=new HomeStudentController(materialManagementDAO.searchStudent(userNameField.getText()));
                loader.setController(studentController);
                root=loader.load();
                if(bundle.getLocale().toString().equals("bs")) {
                    stage.setTitle("Početna stranica");
                } else {
                    stage.setTitle("Home");
                }
                stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
                stage.setMinHeight(500); //da se ne može više smanjivati
                stage.setMinWidth(200);
                stage.show();
                Stage stageClose = (Stage) userNameField.getScene().getWindow();
                stageClose.close();

            } else {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Stage stage = (Stage) userNameField.getScene().getWindow();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(bundle.getLocale().toString().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Pogrešna lozinka. Pokušajte ponovo!");
                } else {
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong password. Please try again!");
                }
                alert.showAndWait();
            }
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stageClose = (Stage) userNameField.getScene().getWindow();
        stageClose.close();
    }
}
