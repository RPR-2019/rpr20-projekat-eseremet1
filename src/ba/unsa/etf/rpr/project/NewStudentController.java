package ba.unsa.etf.rpr.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class NewStudentController implements NewPassword {
    public TextField nameField;
    public TextField surnameField;
    public TextField usernameField;
    public PasswordField passwordField;
    private MaterialManagementDAO materialManagementDAO;
    private Student student;
    public Button addBtn;

    public NewStudentController(Student student) {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        this.student = student;
    }

    @FXML
    public void initialize() {
        if(student!=null) {
            nameField.setText(student.getName());
            surnameField.setText(student.getSurname());
            passwordField.setText(student.getPassword());
            usernameField.setText(student.getUsername());
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
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        if(bundle.getLocale().toString().equals("bs")) {
            toolTip1.setText("Morate generisati lozinku!");
        } else {
            toolTip1.setText("You must generate a password!");
        }
        passwordField.setTooltip(toolTip1);
    }

    public void generateAction(ActionEvent actionEvent) {
        String newPassword = generatePassword();

        passwordField.setText(newPassword);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(bundle.getLocale().toString().equals("bs")) {
            alert.setTitle("Upozorenje!");
            alert.setHeaderText("Vaša lozinka glasi: ");
        } else {
            alert.setTitle("Warning");
            alert.setHeaderText("Your password is: ");
        }
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

        if(student==null) {
            for (Student oldStudent : materialManagementDAO.students()) {
                if (oldStudent.getUsername().equals(usernameField.getText())) {
                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    if(bundle.getLocale().toString().equals("bs")) {
                        alert.setTitle("Upozorenje");
                        alert.setHeaderText(null);
                        alert.setContentText("Username već postoji!");
                    } else {
                        alert.setTitle("Upozorenje");
                        alert.setHeaderText(null);
                        alert.setContentText("Username already exists!");
                    }

                    alert.showAndWait();

                    sveOk = false;
                    break;

                }

            }

            for (Professor oldProffesor : materialManagementDAO.professors()) {
                if (oldProffesor.getUsername().equals(usernameField.getText())) {
                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    if(bundle.getLocale().toString().equals("bs")) {
                        alert.setTitle("Upozorenje");
                        alert.setHeaderText(null);
                        alert.setContentText("Username već postoji!");
                    } else {
                        alert.setTitle("Upozorenje");
                        alert.setHeaderText(null);
                        alert.setContentText("Username already exists!");
                    }

                    alert.showAndWait();

                    sveOk = false;
                    break;

                }

            }


        }

        if (!sveOk) return;

        if (student == null) student = new Student();
        student.setName(nameField.getText());
        student.setSurname(surnameField.getText());
        student.setUsername(usernameField.getText());
        student.setPassword(passwordField.getText());
        student.setEmail(usernameField.getText()+"@etf.unsa.ba");
        student.setIndex(materialManagementDAO.getIndex());
        if(student.getPicture()==null || student.getPicture()=="")
        student.setPicture("");

        Stage stageClose = (Stage) nameField.getScene().getWindow();
        stageClose.close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
