package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;


public class PasswordController {

    public PasswordField newPasswordField1;
    public PasswordField newPasswordField;
    private Professor professor;
    private ProfessorDAO professorDAO;

    public PasswordController(Professor professor) {
        professorDAO = ProfessorDAO.getInstance();
        this.professor=professor;
    }



    public void changeAction(ActionEvent actionEvent) throws IOException {
        boolean correct = true;
        if(!newPasswordField.getText().equals(newPasswordField1.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Not confirmed the same password!");
            alert.show();
            correct = false;
        } if(correct==true) {
            correct = passwordCheck(newPasswordField.getText());
            if(correct==true) {
                professor.setPassword(newPasswordField.getText());
                professorDAO.changeProfessor(professor);
                Stage stageClose = (Stage) newPasswordField.getScene().getWindow();
                stageClose.close();
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"));
                ProfileController profileController = new ProfileController(professor);
                loader.setController(profileController);
                root = loader.load();
                stage.setTitle("Moj profil");
                stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
                stage.setResizable(true);
                stage.show();
            }
        }
    }

    private boolean passwordCheck(String password) {
        boolean correct = true;
        if(password.length()<8) {
            correct=false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Password must be at least 8 characters long!");
            alert.show();
        } else {
            boolean small = false, uppercase = false, number = false;
            //a sadrži barem jedno veliko slovo, jedno malo slovo i jednu cifru

            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                    uppercase = true;
                } else if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
                    small = true;
                } else if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                    number = true;
                } else continue;
            }
            if (uppercase == false || small == false || number == false) {
                correct=false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("The password must have uppercase, lowercase and number!");
                alert.show();
            }
        }
        return correct;
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) newPasswordField.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"));
        ProfileController profileController =new ProfileController(professor);
        loader.setController(profileController);
        root=loader.load();
        stage.setTitle("Moj profil");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setResizable(true);
        stage.show();
    }
}