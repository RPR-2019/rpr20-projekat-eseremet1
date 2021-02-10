package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    public void addDocumentAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/chooseDocType.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Izbor vrste željenog materijala");
        stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.setResizable(false);

        stage.show();
        Stage stage1 = (Stage) addDocumentBtn.getScene().getWindow();
        stage1.close();

    }

    public void homeworkAction(ActionEvent actionEvent) {

    }

    public void reviewAction(ActionEvent actionEvent) {

    }

    public void logoutAction(ActionEvent actionEvent) {

    }
}
