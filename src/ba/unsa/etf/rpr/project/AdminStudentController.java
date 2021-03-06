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

public class AdminStudentController {
    public TableView<Student> tableViewStudents;
    public TextField filterField;
    private ObservableList<Student> collection;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colStudentSurname;
    public TableColumn colStudentUsername;
    public TableColumn colStudentPassword;
    public TableColumn colStudentEmail;
    public TableColumn colStudentIndex;
    public TableColumn colStudentPicture;
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
    public AdminStudentController() {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        collection = FXCollections.observableArrayList(materialManagementDAO.students());
    }

    @FXML
    public void initialize() {
        tableViewStudents.setItems(collection);
        colStudentId.setCellValueFactory(new PropertyValueFactory("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory("name"));
        colStudentSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        colStudentUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colStudentPassword.setCellValueFactory(new PropertyValueFactory("password"));
        colStudentEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colStudentPicture.setCellValueFactory(new PropertyValueFactory("picture"));
        colStudentIndex.setCellValueFactory(new PropertyValueFactory("index"));



        FilteredList<Student> filteredList = new FilteredList<>(collection, tmp->true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(student -> {
                if(newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if(student.getName().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                } else if(student.getSurname().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                } else if(student.getUsername().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                } else if(student.getEmail().toLowerCase().indexOf(lowerCaseFilter)!= -1) {
                    return true;
                }  else return false;
            });
        });
        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewStudents.comparatorProperty());
        tableViewStudents.setItems(sortedList);
        translateTooltips();
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
        Stage stageClose = (Stage) tableViewStudents.getScene().getWindow();
        stageClose.close();
    }

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) tableViewStudents.getScene().getWindow();
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

    public void addStudentAction(ActionEvent actionEvent) {
        Parent root = null;
        Stage stage = new Stage();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addStudent.fxml"), bundle);
            NewStudentController studentController = new NewStudentController(null);
            loader.setController(studentController);
            root=loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Dodavanje studenta");
            } else {
                stage.setTitle("Add student");
            }
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                Student student = studentController.getStudent();
                if (student != null) {
                    materialManagementDAO.addStudent(student);
                    collection.setAll(materialManagementDAO.students());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void editStudentAction(ActionEvent actionEvent) {
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        if (student == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addStudent.fxml"), bundle);
            NewStudentController studentController = new NewStudentController(student);
            loader.setController(studentController);
            root=loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle("Izmjena studenta");
            } else {
                stage.setTitle("Edit student");
            }
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> {
                Student newStudent = studentController.getStudent();
                if (newStudent != null) {
                    materialManagementDAO.changeStudent(newStudent);
                    collection.setAll(materialManagementDAO.students());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentAction(ActionEvent actionEvent) {
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        if (student == null) return;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(bundle.getLocale().toString().equals("bs")) {
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje studenta " + student.getName() + " " + student.getSurname());
            alert.setContentText("Da li ste sigurni da želite obrisati studenta " + student.getName() + " " + student.getSurname()+"?");
        } else {
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("Delete student  " + student.getName() + " " + student.getSurname());
            alert.setContentText("Are you sure you want to delete the student " + student.getName() + " " + student.getSurname()+"?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (((Optional) result).get() == ButtonType.OK){
            materialManagementDAO.deleteStudent(student.getUsername());
            collection.setAll(materialManagementDAO.students());
        }
    }

    public void showReportAction(ActionEvent actionEvent) {
        try {
            new PrintReport().showReportStudents(materialManagementDAO.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void bosnianAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        translate();
        translateTooltips();
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
            toolTip1.setText("Dodaj novog studenta");
            toolTip2.setText("Uredi izabranog studenta");
            toolTip3.setText("Obriši izabranog studenta");
            toolTip4.setText("Pogledaj izvještaj svih studenata");
            toolTip5.setText("Pretraži studente po imenu, prezimenu, korisničkom imenu ili emailu");
            toolTip6.setText("Povratak na početnu stranicu");
            toolTip7.setText("Želite se odjaviti?");
        } else {
            toolTip1.setText("Add a new student");
            toolTip2.setText("Modify an existing student");
            toolTip3.setText("Delete this student");
            toolTip4.setText("Report of all students");
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

    public void englishAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        translate();
        translateTooltips();
    }

    private void translate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        colStudentName.setText(resourceBundle.getString("name"));
        colStudentId.setText(resourceBundle.getString("ID"));
        colStudentUsername.setText(resourceBundle.getString("username"));
        colStudentEmail.setText(resourceBundle.getString("email"));
        colStudentPassword.setText(resourceBundle.getString("password"));
        colStudentSurname.setText(resourceBundle.getString("surname"));
        colStudentIndex.setText(resourceBundle.getString("index"));
        colStudentPicture.setText(resourceBundle.getString("picture"));
        filterField.setText(resourceBundle.getString("search"));
        languageMenu.setText(resourceBundle.getString("language"));
        helpMenu.setText(resourceBundle.getString("_help"));
        aboutMenu.setText(resourceBundle.getString("_about"));
        bosnianMenu.setText(resourceBundle.getString("bosnian"));
        englishMenu.setText(resourceBundle.getString("english"));
        addLabel.setText(resourceBundle.getString("addStudent"));
        removeLabel.setText(resourceBundle.getString("removeStudent"));
        reportLabel.setText(resourceBundle.getString("report"));
        changeLabel.setText(resourceBundle.getString("changeStudent"));
        tableViewStudents.setItems(collection);

    }
}
