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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminProfessorController {

    public TableView<Professor> tableViewProfessors;
    public TextField filterField;
    private ProfessorDAO professorDAO;
    private ObservableList<Professor> collection;
    public TableColumn colProfessorId;
    public TableColumn colProfessorName;
    public TableColumn colProfessorSurname;
    public TableColumn colProfessorUsername;
    public TableColumn colProfessorPassword;
    public TableColumn colProfessorEmail;


    public AdminProfessorController() {
        professorDAO = ProfessorDAO.getInstance();
        collection = FXCollections.observableArrayList(professorDAO.professors());
    }

    @FXML
    public void initialize() {
        tableViewProfessors.setItems(collection);
        colProfessorId.setCellValueFactory(new PropertyValueFactory("id"));
        colProfessorName.setCellValueFactory(new PropertyValueFactory("name"));
        colProfessorSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        colProfessorUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colProfessorPassword.setCellValueFactory(new PropertyValueFactory("password"));
        colProfessorEmail.setCellValueFactory(new PropertyValueFactory("email"));


        FilteredList<Professor> filteredList = new FilteredList<>(collection, tmp->true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(professor -> {
                if(newValue==null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(professor.getName().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                } else if(professor.getSurname().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                } else if(professor.getUsername().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                } else if(professor.getEmail().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                }  else return false;
            });
        });
        SortedList<Professor> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewProfessors.comparatorProperty());
        tableViewProfessors.setItems(sortedList);

    }

    public void addProfessorAction(ActionEvent actionEvent) {
        Parent root = null;
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProfessor.fxml"));
            AddProfessorController professorContoller=new AddProfessorController();
            loader.setController(professorContoller);
            root=loader.load();
            stage.setTitle("Dodavanje profesora");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            /*stage.setOnHiding(event -> {
                Professor professor = professorContoller.getProfesor();
                if (professor != null) {
                    professorDAO.addProfessor(professor);
                    collection.setAll(professorDAO.professors());
                }
            });*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editProfessorAction(ActionEvent actionEvent) {
    }

    public void deleteProfessorAction(ActionEvent actionEvent) {
    }

    public void showReportAction(ActionEvent actionEvent) {
    }
}
