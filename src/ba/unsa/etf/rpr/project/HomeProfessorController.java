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
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class HomeProfessorController {

    @FXML
    public ListView<Subject> listViewSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Subject> subjectCollection;
    private ObservableList<Professor> professorCollection;
    private Professor activeProfessor;
    public Button addDocumentBtn;
    public Button profilBtn;
    public Menu languageMenu;
    public Menu helpMenu;
    public MenuItem bosnianMenu;
    public MenuItem englishMenu;
    public MenuItem aboutMenu;
    public Button logoutBtn;
    public Button reviewBtn;
    public Button quizBtn;
    public Button homeworkBtn;


    public HomeProfessorController(Professor professor) {
        materialManagementDAO = MaterialManagementDAO.getInstance();
        subjectCollection = FXCollections.observableArrayList(materialManagementDAO.subjects());
        professorCollection = FXCollections.observableArrayList(materialManagementDAO.professors());
        activeProfessor = professor;
    }

    @FXML
    public void initialize() {
        //pronalazak svih predmeta koje profesor može uređivati
        ArrayList<Subject> activeSubjects = subjectCollection.stream().filter(subject -> subject.getId() == activeProfessor.getSubject().getId()).collect(Collectors.toCollection(ArrayList::new));
        ObservableList<Subject> professorsSubjects = FXCollections.observableArrayList(activeSubjects);
        for (int i=0; i<subjectCollection.size(); i++) {
            if(professorsSubjects.contains(subjectCollection.get(i))) {
                Subject subject = subjectCollection.get(i);
                subjectCollection.get(i).setName(subject+"*");
            }
        }
        listViewSubject.setItems(subjectCollection);
        translateTooltips();
    }
    public void addDocumentAction(ActionEvent actionEvent) throws IOException {
        if(listViewSubject.getSelectionModel().getSelectedItem()==null) {
            try {
                throw new IllegalAction("You must select one item");
            } catch (IllegalAction illegalAction) {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(bundle.getLocale().toString().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Morate izabrati predmet za koji želite dodati materijal!");
                } else {
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("You must select a specific subject!");
                }

                alert.showAndWait();
            }
        } else {
            if(!listViewSubject.getSelectionModel().getSelectedItem().getName().contains("*")) {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(bundle.getLocale().toString().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Materijale može dodavati samo profesor na predmetu!");
                } else {
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Materials can only be added by the subject teacher!");
                }


                alert.showAndWait();
            } else {
                Stage stage = new Stage();
                Parent root = null;
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseDocType.fxml"), bundle);
                DocTypeController docTypeController = new DocTypeController(listViewSubject.getSelectionModel().getSelectedItem(), activeProfessor);
                loader.setController(docTypeController);
                root = loader.load();
                if(bundle.getLocale().toString().equals("bs")) {
                    stage.setTitle("Izbor vrste željenog materijala");
                } else {
                    stage.setTitle("Choose type");
                }
                stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
                stage.setMinHeight(300); //da se ne može više smanjivati
                stage.setMinWidth(200);
                stage.setResizable(false);

                stage.show();
            }
        }
    }

    public void homeworkAction(ActionEvent actionEvent) {

    }

    public void quizAction(ActionEvent actionEvent) {
        if(listViewSubject.getSelectionModel().getSelectedItem()==null) {
            try {
                throw new IllegalAction("You must select one item");
            } catch (IllegalAction illegalAction) {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(bundle.getLocale().toString().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Morate izabrati predmet za koji želite dodati kviz!");
                } else {
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("You must select a specific subject!");
                }

                alert.showAndWait();
            }
        }
            else{
                if (!listViewSubject.getSelectionModel().getSelectedItem().getName().contains("*")) {
                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    if(bundle.getLocale().toString().equals("bs")) {
                        alert.setTitle("Upozorenje");
                        alert.setHeaderText(null);
                        alert.setContentText("Kviz može dodavati samo profesor na predmetu!");
                    } else {
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Quizzes can only be added by the subject teacher!");
                    }

                    alert.showAndWait();
                } else {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quiz.fxml"), bundle);
                        QuizController quizController = new QuizController(activeProfessor);
                        loader.setController(quizController);
                        root = loader.load();
                        if(bundle.getLocale().toString().equals("bs")) {
                            stage.setTitle("Izbor vrste željenog materijala");
                        } else {
                            stage.setTitle("Quiz");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
                    stage.setMinHeight(300); //da se ne može više smanjivati
                    stage.setMinWidth(200);
                    stage.show();
                }
            }
    }

    public void reviewAction(ActionEvent actionEvent) throws IOException {
        if(listViewSubject.getSelectionModel().getSelectedItem()!=null) {
            Stage stageClose = (Stage) addDocumentBtn.getScene().getWindow();
            stageClose.close();
            Stage stage = new Stage();
            Parent root = null;
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/review.fxml"), bundle);
            ReviewController reviewController = new ReviewController(listViewSubject.getSelectionModel().getSelectedItem(), activeProfessor);
            loader.setController(reviewController);
            root = loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle(listViewSubject.getSelectionModel().getSelectedItem().getName());
            } else {
                stage.setTitle(listViewSubject.getSelectionModel().getSelectedItem().getName());
            }
            stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
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
        Stage stageClose = (Stage) addDocumentBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"), bundle);
        ProfileController profileController =new ProfileController(activeProfessor);
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

    private void translateTooltips() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Tooltip toolTip1 = new Tooltip();
        Tooltip toolTip2 = new Tooltip();
        Tooltip toolTip3 = new Tooltip();
        Tooltip toolTip4 = new Tooltip();
        Tooltip toolTip5 = new Tooltip();
        Tooltip toolTip6 = new Tooltip();



        if(bundle.getLocale().toString().equals("bs")) {
            toolTip1.setText("Prikaži materijale za odabrani predmet");
            toolTip2.setText("Želite se odjaviti?");
            toolTip3.setText("Pogledaj profil");
            toolTip4.setText("Dodaj novi materijal");
            toolTip5.setText("Dodaj novi kviz");
            toolTip6.setText("Povratak na početnu stranicu");
            toolTip6.setText("Opcija *Dodavanje zadaće* će biti omogućena USKORO");
        } else {
            toolTip1.setText("Display material for the selected subject");
            toolTip2.setText("You want to log out?");
            toolTip3.setText("View profile");
            toolTip4.setText("Add new material");
            toolTip5.setText("Add new quiz");
            toolTip6.setText("The *Add homework* option will be enabled SOON!");
        }
        reviewBtn.setTooltip(toolTip1);
        logoutBtn.setTooltip(toolTip2);
        profilBtn.setTooltip(toolTip3);
        addDocumentBtn.setTooltip(toolTip4);
        quizBtn.setTooltip(toolTip5);
        homeworkBtn.setTooltip(toolTip6);
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

    public Parent getTheListUnderTesting() {
        return listViewSubject;
    }
}
