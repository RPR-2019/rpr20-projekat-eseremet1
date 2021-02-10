package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        professor.setPassword(newPasswordField.getText());
        professorDAO.changeProfessor(professor);
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
