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


public class HomeProfessorController {

    @FXML
    private ListView<Subject> listViewSubject;
    private SubjectDAO subjectDAO;
    private ProfessorDAO professorDAO;
    private ObservableList<Subject> subjectCollection;
    private ObservableList<Professor> professorCollection;
    private Professor activeProfessor;
    public Button addDocumentBtn;
    public Button pdfBtn;
    public Button profilBtn;

    public HomeProfessorController(Professor professor) {
        subjectDAO = SubjectDAO.getInstance();
        subjectCollection = FXCollections.observableArrayList(subjectDAO.subjects());
        subjectDAO.close();
        professorDAO = ProfessorDAO.getInstance();
        professorCollection = FXCollections.observableArrayList(professorDAO.professors());
        activeProfessor = professor;
    }

    @FXML
    public void initialize() {
        //pronalazak svih predmeta koje profesor može uređivati
        ArrayList<Subject> activeSubjects = subjectCollection.stream().filter(subject -> subject.getId() == activeProfessor.getSubject().getId()).collect(Collectors.toCollection(ArrayList::new));
        ObservableList<Subject> professorsSubjects = FXCollections.observableArrayList(activeSubjects);
        listViewSubject.setItems(professorsSubjects);
    }
    public void addDocumentAction(ActionEvent actionEvent) throws IOException {
        if(listViewSubject.getSelectionModel().getSelectedItem()==null) {
            try {
                throw new IllegalAction("You must select one item");
            } catch (IllegalAction illegalAction) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Morate izabrati predmet za koji želite dodati materijal!");

                alert.showAndWait();
            }
        } else {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseDocType.fxml"));
            DocTypeController docTypeController=new DocTypeController(listViewSubject.getSelectionModel().getSelectedItem(), activeProfessor);
            loader.setController(docTypeController);
            root=loader.load();
            stage.setTitle("Izbor vrste željenog materijala");
            stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
            stage.setMinHeight(300); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.setResizable(false);

            stage.show();
        }
    }

    public void homeworkAction(ActionEvent actionEvent) {

    }

    public void reviewAction(ActionEvent actionEvent) {

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
        Stage stageClose = (Stage) addDocumentBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"));
        ProfileController profileController =new ProfileController(activeProfessor);
        loader.setController(profileController);
        root=loader.load();
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setResizable(true);


        stage.show();
    }
}
