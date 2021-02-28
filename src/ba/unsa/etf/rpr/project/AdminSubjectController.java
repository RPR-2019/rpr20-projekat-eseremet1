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

public class AdminSubjectController {
    public TableView<Subject> tableViewSubjects;
    public TextField filterField;
    private ObservableList<Subject> collection;
    public TableColumn colSubjectID;
    public TableColumn colSubjectName;
    public MaterialManagementDAO materialManagementDAO;
    public Menu languageMenu;
    public Menu helpMenu;
    public MenuItem bosnianMenu;
    public MenuItem englishMenu;
    public MenuItem aboutMenu;
    public Label addLabel;
    public Label changeLabel;
    public Label removeLabel;
    public Label reportLabel;
    public Button addSubjectBtn;
    public Button editSubjectBtn;
    public Button deleteSubjectBtn;
    public Button reportBtn;
    public Button backBtn;
    public Button logoutBtn;

    public AdminSubjectController() {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        collection = FXCollections.observableArrayList(materialManagementDAO.subjects());
    }

    @FXML
    public void initialize() {
        tableViewSubjects.setItems(collection);
        colSubjectID.setCellValueFactory(new PropertyValueFactory("id"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory("name"));

        FilteredList<Subject> filteredList = new FilteredList<>(collection, tmp->true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(subject -> {
                if(newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(subject.getName().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                }  else return false;
            });
        });
        SortedList<Subject> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewSubjects.comparatorProperty());
        tableViewSubjects.setItems(sortedList);
        translateTooltips();

    }
    public void addSubjectAction(ActionEvent actionEvent) {
        Parent root = null;
        Stage stage = new Stage();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"), bundle);
            NewSubjectController subjectController = new NewSubjectController(null, materialManagementDAO.professors());
            loader.setController(subjectController);
            root=loader.load();

            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Dodavanje predmeta");
            } else {
                stage.setTitle("Add subject");
            }
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Subject subject = subjectController.getSubject();
                if (subject != null) {
                    materialManagementDAO.addSubject(subject);
                    collection.setAll(materialManagementDAO.subjects());
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
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"), bundle);
            NewSubjectController subjectController = new NewSubjectController(subject, materialManagementDAO.professors());
            loader.setController(subjectController);
            root=loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Izmjena predmeta");
            } else {
                stage.setTitle("Edit subject");
            }
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Subject newSubject = subjectController.getSubject();
                if (subject != null) {
                    materialManagementDAO.changeSubject(subject);
                    collection.setAll(materialManagementDAO.subjects());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubjectAction(ActionEvent actionEvent) {
        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();
        if (subject == null) return;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(bundle.getLocale().toString().equals("bs")) {
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje predmeta " + subject.getName());
            alert.setContentText("Da li ste sigurni da želite obrisati predmet " + subject.getName() +"?");
        } else {
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("Delete subject " + subject.getName());
            alert.setContentText("Are you sure you want to delete the subject " + subject.getName() +"?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (((Optional) result).get() == ButtonType.OK){
            materialManagementDAO.deleteSubject(subject.getName());
            collection.setAll(materialManagementDAO.subjects());
        }
    }

    public void showReportAction(ActionEvent actionEvent) {
        try {
            new PrintReport().showReportSubjects(materialManagementDAO.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewSubjects.getScene().getWindow();
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
        Stage stageClose = (Stage) tableViewSubjects.getScene().getWindow();
        stageClose.close();
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
        colSubjectName.setText(resourceBundle.getString("name"));
        colSubjectID.setText(resourceBundle.getString("ID"));
        filterField.setText(resourceBundle.getString("search"));
        languageMenu.setText(resourceBundle.getString("language"));
        helpMenu.setText(resourceBundle.getString("_help"));
        aboutMenu.setText(resourceBundle.getString("_about"));
        bosnianMenu.setText(resourceBundle.getString("bosnian"));
        englishMenu.setText(resourceBundle.getString("english"));
        addLabel.setText(resourceBundle.getString("addSubject"));
        removeLabel.setText(resourceBundle.getString("removeSubject"));
        reportLabel.setText(resourceBundle.getString("report"));
        changeLabel.setText(resourceBundle.getString("changeSubject"));
        tableViewSubjects.setItems(collection);

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
            toolTip1.setText("Dodaj novi predmet");
            toolTip2.setText("Uredi izabrani predmet");
            toolTip3.setText("Obriši izabrani predmet");
            toolTip4.setText("Pogledaj izvještaj svih predmeta");
            toolTip5.setText("Pretraži predmete po nazivu");
            toolTip6.setText("Povratak na početnu stranicu");
            toolTip7.setText("Želite se odjaviti?");
        } else {
            toolTip1.setText("Add a new subject");
            toolTip2.setText("Modify an existing subject");
            toolTip3.setText("Delete this subject");
            toolTip4.setText("Report of all subjects");
            toolTip5.setText("Search by name");
            toolTip6.setText("Return to home page");
            toolTip7.setText("You want to log out?");
        }

        addSubjectBtn.setTooltip(toolTip1);
        editSubjectBtn.setTooltip(toolTip2);
        deleteSubjectBtn.setTooltip(toolTip3);
        reportBtn.setTooltip(toolTip4);
        filterField.setTooltip(toolTip5);
        backBtn.setTooltip(toolTip6);
        logoutBtn.setTooltip(toolTip7);
    }
}
