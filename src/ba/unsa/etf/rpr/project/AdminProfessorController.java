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
import java.time.LocalDateTime;
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
    public Button addProffesorBtn;
    public Button editProffesorBtn;
    public Button deleteProffesorBtn;
    public Button reportBtn;
    public Button backBtn;
    public Button logoutBtn;

    public AdminProfessorController() {
        materialManagementDAO = MaterialManagementDAO.getInstance();
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
                if(newValue == null || newValue.isEmpty()) return true;
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
        translateTooltips();
    }

    public void addProfessorAction(ActionEvent actionEvent) {
        Parent root = null;
        Stage stage = new Stage();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProfessor.fxml"), bundle);
            NewProfessorController professorContoller = new NewProfessorController(null, materialManagementDAO.subjects());
            loader.setController(professorContoller);
            root = loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Dodavanje profesora");
            } else {
                stage.setTitle("Add professor");
            }
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
        Professor professor = tableViewProfessors.getSelectionModel().getSelectedItem();
        if (professor == null) return;
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProfessor.fxml"), bundle);
            NewProfessorController professorContoller = new NewProfessorController(professor, materialManagementDAO.subjects());
            loader.setController(professorContoller);
            root = loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Izmjena profesora");
            } else {
                stage.setTitle("Edit professor");
            }
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
        if (professor == null) return;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(bundle.getLocale().toString().equals("bs")) {
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje profesora "+professor.getName()+" "+professor.getSurname());
            alert.setContentText("Da li ste sigurni da želite obrisati profesora " +professor.getName()+" "+professor.getSurname()+"?");
        } else {
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("Delete professor  "+professor.getName()+" "+professor.getSurname());
            alert.setContentText("Are you sure you want to delete the professor " +professor.getName()+" "+professor.getSurname()+"?");
        }


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
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/logIn.fxml" ), bundle);
        Parent root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Prijava");
        } else {
            stage.setTitle("Login");
        }
        stage.setScene(new Scene(root, 1200, 700));
        stage.setResizable(false);
        stage.show();
        Stage stageClose = (Stage) tableViewProfessors.getScene().getWindow();
        stageClose.close();
    }

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewProfessors.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/homeAdmin.fxml" ), bundle);
        root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Početna - Admin");
        } else {
            stage.setTitle("Home - Admin");
        }
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);
        stage.show();
    }

    public void aboutAction(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/about.fxml" ), bundle);
        Parent root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            myStage.setTitle("O nama");
        } else {
            myStage.setTitle("About");
        }
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public void bosnianAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        translate();
        translateTooltips();
    }


    public void englishAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        translate();
        translateTooltips();
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
        tableViewProfessors.setItems(collection);
    }

    private void translateTooltips() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Tooltip toolTip1 = new Tooltip();
        Tooltip toolTip2 = new Tooltip();
        Tooltip toolTip3 = new Tooltip();
        Tooltip toolTip4 = new Tooltip();
        Tooltip toolTip5 = new Tooltip();
        Tooltip toolTip6 = new Tooltip();
        Tooltip toolTip7 = new Tooltip();


        if(bundle.getLocale().toString().equals("bs")) {
            toolTip1.setText("Dodaj novog profesora");
            toolTip2.setText("Uredi izabranog profesora");
            toolTip3.setText("Obriši izabranog profesora");
            toolTip4.setText("Pogledaj izvještaj svih profesora");
            toolTip5.setText("Pretraži profesore po imenu, prezimenu, korisničkom imenu ili emailu");
            toolTip6.setText("Povratak na početnu stranicu");
            toolTip7.setText("Želite se odjaviti?");
        } else {
            toolTip1.setText("Add a new professor");
            toolTip2.setText("Modify an existing professor");
            toolTip3.setText("Delete this professor");
            toolTip4.setText("Report of all professors");
            toolTip5.setText("Search by name, surname, username or email");
            toolTip6.setText("Return to home page");
            toolTip7.setText("You want to log out?");
        }

        addProffesorBtn.setTooltip(toolTip1);
        editProffesorBtn.setTooltip(toolTip2);
        deleteProffesorBtn.setTooltip(toolTip3);
        reportBtn.setTooltip(toolTip4);
        filterField.setTooltip(toolTip5);
        backBtn.setTooltip(toolTip6);
        logoutBtn.setTooltip(toolTip7);
    }

}
