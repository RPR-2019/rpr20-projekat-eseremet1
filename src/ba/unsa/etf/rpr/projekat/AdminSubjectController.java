package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class AdminSubjectController {
    public TableView<Subject> tableViewSubjects;
    public TextField filterField;
    private SubjectDAO subjectDAO;
    private ObservableList<Subject> collection;
    public TableColumn colSubjectId;
    public TableColumn colSubjectName;
    public TableColumn colSubjectEnrolled;


    public AdminSubjectController() {
        subjectDAO = SubjectDAO.getInstance();
        collection = FXCollections.observableArrayList(subjectDAO.subjects());
    }

    @FXML
    public void initialize() {
        tableViewSubjects.setItems(collection);
        colSubjectId.setCellValueFactory(new PropertyValueFactory("id"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory("name"));
        colSubjectEnrolled.setCellValueFactory(new PropertyValueFactory("enrolled"));


        FilteredList<Subject> filteredList = new FilteredList<>(collection, tmp->true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(subject -> {
                if(newValue==null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(subject.getName().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                }  else return false;
            });
        });
        SortedList<Subject> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewSubjects.comparatorProperty());
        tableViewSubjects.setItems(sortedList);

    }
    public void addSubjectAction(ActionEvent actionEvent) {

    }

    public void editSubjectAction(ActionEvent actionEvent) {
    }

    public void deleteSubjectAction(ActionEvent actionEvent) {
        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();
        if (subject== null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje predmeta "+subject.getName()+" ");
        alert.setContentText("Da li ste sigurni da želite obrisati predmet" +subject.getName()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (((Optional) result).get() == ButtonType.OK){
            subjectDAO.deleteSubject(subject.getName());
            collection.setAll(subjectDAO.subjects());
        }
    }

    public void showReportAction(ActionEvent actionEvent) {
    }
}
