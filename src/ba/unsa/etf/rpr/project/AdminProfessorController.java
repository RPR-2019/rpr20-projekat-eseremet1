package ba.unsa.etf.rpr.project;

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
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminProfessorController {

    public TableView<Professor> tableViewProfessors;
    public TextField filterField;
    private ObservableList<Professor> collection;
    public TableColumn colProfessorId;
    public TableColumn colProfessorName;
    public TableColumn colProfessorSurname;
    public TableColumn colProfessorUsername;
    public TableColumn colProfessorPassword;
    public TableColumn colProfessorEmail;
    public TableColumn colProfessorSubject;
    public TableColumn colProfessorPicture;
    private MaterialManagementDAO materialManagementDAO;
    public Menu languageMenu;
    public Menu helpMenu;
    public MenuItem bosnianMenu;
    public MenuItem englishMenu;
    public MenuItem aboutMenu;
    public Label addLabel;
    public Label changeLabel;
    public Label removeLabel;
    public Label reportLabel;



    public AdminProfessorController() {
        materialManagementDAO= MaterialManagementDAO.getInstance();
        collection = FXCollections.observableArrayList(materialManagementDAO.professors());
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
        colProfessorPicture.setCellValueFactory(new PropertyValueFactory("picture"));
        colProfessorSubject.setCellValueFactory(new PropertyValueFactory("subject"));



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
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProfessor.fxml"), bundle);
            AddProfessorController professorContoller=new AddProfessorController(null, materialManagementDAO.subjects());
            loader.setController(professorContoller);
            root=loader.load();
            stage.setTitle("Dodavanje profesora");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Professor professor = professorContoller.getProfessor();
                if (professor != null) {
                    materialManagementDAO.addProfessor(professor);
                    collection.setAll(materialManagementDAO.professors());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editProfessorAction(ActionEvent actionEvent) {
        Professor professor= tableViewProfessors.getSelectionModel().getSelectedItem();
        if (professor == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProfessor.fxml"), bundle);
            AddProfessorController professorContoller=new AddProfessorController(professor, materialManagementDAO.subjects());
            loader.setController(professorContoller);
            root=loader.load();
            stage.setTitle("Izmjena profesora");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Professor newProfessor = professorContoller.getProfessor();
                if (newProfessor != null) {
                    materialManagementDAO.changeProfessor(newProfessor);
                    collection.setAll(materialManagementDAO.professors());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProfessorAction(ActionEvent actionEvent) {
        Professor professor = tableViewProfessors.getSelectionModel().getSelectedItem();
        if (professor== null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje profesora "+professor.getName()+" "+professor.getSurname());
        alert.setContentText("Da li ste sigurni da želite obrisati profesora " +professor.getName()+" "+professor.getSurname()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (((Optional) result).get() == ButtonType.OK){
            materialManagementDAO.deleteProfessor(professor.getUsername());
            collection.setAll(materialManagementDAO.professors());
        }
    }

    public void showReportAction(ActionEvent actionEvent) {
        try {
            new PrintReport().showReportProfessors(materialManagementDAO.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewProfessors.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/logIn.fxml" ), bundle);
        root = loader.load();
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewProfessors.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/homeAdmin.fxml" ), bundle);
        root = loader.load();
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

    public void aboutAction(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/about.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("About");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public void bosnianAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        translate();
    }


    public void englishAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        translate();
    }

    private void translate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        colProfessorName.setText(resourceBundle.getString("name"));
        colProfessorId.setText(resourceBundle.getString("ID"));
        colProfessorUsername.setText(resourceBundle.getString("username"));
        colProfessorEmail.setText(resourceBundle.getString("email"));
        colProfessorPassword.setText(resourceBundle.getString("password"));
        colProfessorSurname.setText(resourceBundle.getString("surname"));
        colProfessorSubject.setText(resourceBundle.getString("subject"));
        colProfessorPicture.setText(resourceBundle.getString("picture"));
        filterField.setText(resourceBundle.getString("search"));
        languageMenu.setText(resourceBundle.getString("language"));
        helpMenu.setText(resourceBundle.getString("_help"));
        aboutMenu.setText(resourceBundle.getString("_about"));
        bosnianMenu.setText(resourceBundle.getString("bosnian"));
        englishMenu.setText(resourceBundle.getString("english"));
        addLabel.setText(resourceBundle.getString("addProfessor"));
        removeLabel.setText(resourceBundle.getString("removeProfessor"));
        reportLabel.setText(resourceBundle.getString("report"));
        changeLabel.setText(resourceBundle.getString("changeProfessor"));

    }

}
