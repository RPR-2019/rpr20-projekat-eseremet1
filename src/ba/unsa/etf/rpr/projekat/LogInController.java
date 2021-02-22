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
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;

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

        Tooltip toolTip = new Tooltip();
        toolTip.setText("Password field");
        passwordField.setTooltip(toolTip);
        Tooltip toolTip1 = new Tooltip();
        toolTip1.setText("Username field");
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
        } else if (materialManagementDAO.searchProfessor(userNameField.getText()) != null) {
            if (materialManagementDAO.searchProfessor(userNameField.getText()).getPassword().equals(passwordField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Obavijest");
                alert.setHeaderText(null);
                alert.setContentText("Uspješno ste prijavljeni kao profesor!");

                alert.showAndWait();
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeProfessor.fxml"));
                HomeProfessorController professorContoller=new HomeProfessorController(materialManagementDAO.searchProfessor(userNameField.getText()));
                loader.setController(professorContoller);
                root=loader.load();
                stage.setTitle("Početna stranica za profesora");
                stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
                stage.setMinHeight(500); //da se ne može više smanjivati
                stage.setMinWidth(200);
                stage.show();
                Stage stageClose = (Stage) userNameField.getScene().getWindow();
                stageClose.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Pogrešna lozinka. Pokušajte ponovo!");

                alert.showAndWait();
            }
        } else if (materialManagementDAO.searchStudent(userNameField.getText()) != null) {
            if (materialManagementDAO.searchStudent(userNameField.getText()).getPassword().equals(passwordField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Obavijest");
                alert.setHeaderText(null);
                alert.setContentText("Uspješno ste prijavljeni kao student!");

                alert.showAndWait();
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeStudent.fxml"));
                HomeStudentController studentController=new HomeStudentController(materialManagementDAO.searchStudent(userNameField.getText()));
                loader.setController(studentController);
                root=loader.load();
                stage.setTitle("Početna stranica za studenta");
                stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
                stage.setMinHeight(500); //da se ne može više smanjivati
                stage.setMinWidth(200);
                stage.show();
                Stage stageClose = (Stage) userNameField.getScene().getWindow();
                stageClose.close();

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
