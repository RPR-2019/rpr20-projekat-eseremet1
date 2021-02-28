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

        Tooltip toolTip1 = new Tooltip();
        toolTip1.setText("Add a new subject");
        addSubjectBtn.setTooltip(toolTip1);
        Tooltip toolTip2 = new Tooltip();
        toolTip2.setText("Modify an existing subject");
        editSubjectBtn.setTooltip(toolTip2);
        Tooltip toolTip3 = new Tooltip();
        toolTip3.setText("Delete this subject");
        deleteSubjectBtn.setTooltip(toolTip3);
        Tooltip toolTip4 = new Tooltip();
        toolTip4.setText("Report of all subjects");
        reportBtn.setTooltip(toolTip4);
        Tooltip toolTip5 = new Tooltip();
        toolTip5.setText("Search by name");
        filterField.setTooltip(toolTip5);
        Tooltip toolTip6 = new Tooltip();
        toolTip6.setText("Return to home page");
        backBtn.setTooltip(toolTip6);
        Tooltip toolTip7 = new Tooltip();
        toolTip7.setText("You want to log out?");
        logoutBtn.setTooltip(toolTip7);


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
            stage.setTitle("Dodavanje profesora");
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
            stage.setTitle("Izmjena predmeta");
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje predmeta "+subject.getName()+" ");
        alert.setContentText("Da li ste sigurni da želite obrisati predmet" +subject.getName()+"?");

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

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewSubjects.getScene().getWindow();
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

    }
}
