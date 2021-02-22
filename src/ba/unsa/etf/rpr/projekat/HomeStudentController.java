package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeStudentController {
    public ListView<Subject> listViewSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Subject> subjectCollection;
    private Student activeStudent;
    public Button profilBtn;

    public HomeStudentController(Student student) {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        subjectCollection = FXCollections.observableArrayList(materialManagementDAO.subjects());
        activeStudent = student;

    }

    @FXML
    public void initialize() {
        listViewSubject.setItems(subjectCollection);
    }



    public void quizAction(ActionEvent actionEvent) {

    }

    public void reviewAction(ActionEvent actionEvent) throws IOException {

    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) profilBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/fxml/logIn.fxml"));
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) profilBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"));
        ProfileController profileController =new ProfileController(activeStudent);
        loader.setController(profileController);
        root=loader.load();
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setResizable(true);


        stage.show();
    }
    public void aboutAction(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        myStage.setTitle("About");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }
}
