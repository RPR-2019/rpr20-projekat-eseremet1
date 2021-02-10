package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class HomeProfessorController {

    @FXML
    ListView<Subject> listViewSubject;
    SubjectDAO subjectDAO;
    ProfessorDAO professorDAO;
    private ObservableList<Subject> subjectCollection;
    private ObservableList<Professor> professorCollection;
    private Professor activeProfessor;

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

    }

    public void homeworkAction(ActionEvent actionEvent) {

    }

    public void reviewAction(ActionEvent actionEvent) {

    }

    public void logoutAction(ActionEvent actionEvent) {

    }
}
