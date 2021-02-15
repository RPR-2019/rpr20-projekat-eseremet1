package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminSubjectController {
    public TableView<Subject> tableViewSubjects;
    public TextField filterField;
    private SubjectDAO subjectDAO;
    private ObservableList<Subject> collection;
    public TableColumn colSubjectID;
    public TableColumn colSubjectName;
    public ProfessorDAO professorDAO;


    public AdminSubjectController() {
        subjectDAO = SubjectDAO.getInstance();
        collection = FXCollections.observableArrayList(subjectDAO.subjects());
        professorDAO = ProfessorDAO.getInstance();
    }

    @FXML
    public void initialize() {
        tableViewSubjects.setItems(collection);
        colSubjectID.setCellValueFactory(new PropertyValueFactory("id"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory("name"));

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
        Parent root = null;
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"));
            AddSubjectController subjectController=new AddSubjectController(null, professorDAO.professors());
            professorDAO.close();
            loader.setController(subjectController);
            root=loader.load();
            stage.setTitle("Dodavanje profesora");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Subject subject = subjectController.getSubject();
                if (subject != null) {
                    subjectDAO.addSubject(subject);
                    collection.setAll(subjectDAO.subjects());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void editSubjectAction(ActionEvent actionEvent) {
        Subject subject= tableViewSubjects.getSelectionModel().getSelectedItem();
        if (subject == null) return;
        Parent root = null;
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"));
            AddSubjectController subjectController=new AddSubjectController(subject, professorDAO.professors());
            loader.setController(subjectController);
            root=loader.load();
            stage.setTitle("Izmjena predmeta");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Subject newSubject = subjectController.getSubject();
                if (subject != null) {
                    professorDAO.close();
                    subjectDAO=SubjectDAO.getInstance();
                    subjectDAO.changeSubject(subject);
                    collection.setAll(subjectDAO.subjects());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewSubjects.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/fxml/homeAdmin.fxml"));
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


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

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewSubjects.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/fxml/logIn.fxml"));
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }
}
