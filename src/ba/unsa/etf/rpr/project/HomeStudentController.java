package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeStudentController {
    public ListView<Subject> listViewSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Subject> subjectCollection;
    private Student activeStudent;
    public Button profilBtn;
    public Menu languageMenu;
    public Menu helpMenu;
    public MenuItem bosnianMenu;
    public MenuItem englishMenu;
    public MenuItem aboutMenu;
    public Button reviewBtn;
    public Button logoutBtn;

    public HomeStudentController(Student student) {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        subjectCollection = FXCollections.observableArrayList(materialManagementDAO.subjects());
        activeStudent = student;

    }

    @FXML
    public void initialize() {
        listViewSubject.setItems(subjectCollection);
        translateTooltips();
    }


    public void reviewAction(ActionEvent actionEvent) throws IOException {
        if(listViewSubject.getSelectionModel().getSelectedItem()!=null) {
            Stage stageClose = (Stage) profilBtn.getScene().getWindow();
            stageClose.close();
            Stage stage = new Stage();
            Parent root = null;
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reviewStudent.fxml"), bundle);
            ReviewStudentController reviewStudentController = new ReviewStudentController(listViewSubject.getSelectionModel().getSelectedItem(), activeStudent);
            loader.setController(reviewStudentController);
            root = loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle(listViewSubject.getSelectionModel().getSelectedItem().getName());
            } else {
                stage.setTitle(listViewSubject.getSelectionModel().getSelectedItem().getName());
            }            stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
            stage.setMinHeight(300); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.setResizable(true);

            stage.show();
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(bundle.getLocale().toString().equals("bs")) {
                alert.setTitle("Obavijest");
                alert.setHeaderText(null);
                alert.setContentText("Morate izabrati određeni predmet!");
            } else {
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("You must select a specific subject!");
            }

            alert.showAndWait();
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
        Stage stageClose = (Stage) reviewBtn.getScene().getWindow();
        stageClose.close();
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) profilBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"), bundle);
        ProfileController profileController =new ProfileController(activeStudent);
        loader.setController(profileController);
        root=loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Profil");
        } else {
            stage.setTitle("Profile");
        }
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setResizable(true);


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
        languageMenu.setText(resourceBundle.getString("language"));
        helpMenu.setText(resourceBundle.getString("_help"));
        aboutMenu.setText(resourceBundle.getString("_about"));
        bosnianMenu.setText(resourceBundle.getString("bosnian"));
        englishMenu.setText(resourceBundle.getString("english"));

    }

    private void translateTooltips() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Tooltip toolTip1 = new Tooltip();
        Tooltip toolTip2 = new Tooltip();
        Tooltip toolTip3 = new Tooltip();

        if(bundle.getLocale().toString().equals("bs")) {
            toolTip1.setText("Prikaži materijale za odabrani predmet");
            toolTip2.setText("Želite se odjaviti?");
            toolTip3.setText("Pogledaj profil");
        } else {
            toolTip1.setText("Display material for the selected subject");
            toolTip2.setText("You want to log out?");
            toolTip3.setText("View profile");
        }


        reviewBtn.setTooltip(toolTip1);
        logoutBtn.setTooltip(toolTip2);
        profilBtn.setTooltip(toolTip3);

    }
}
