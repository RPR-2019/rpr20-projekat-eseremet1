package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class HomeProfessorController {

    @FXML
    private ListView<Subject> listViewSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Subject> subjectCollection;
    private ObservableList<Professor> professorCollection;
    private Professor activeProfessor;
    public Button addDocumentBtn;
    public Button pdfBtn;
    public Button profilBtn;

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
    }
    public void addDocumentAction(ActionEvent actionEvent) throws IOException {
        if(listViewSubject.getSelectionModel().getSelectedItem()==null) {
            try {
                throw new IllegalAction("You must select one item");
            } catch (IllegalAction illegalAction) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Morate izabrati predmet za koji želite dodati materijal!");

                alert.showAndWait();
            }
        } else {
            if(!listViewSubject.getSelectionModel().getSelectedItem().getName().contains("*")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Materijale može dodavati samo profesor na predmetu!");

                alert.showAndWait();
            } else {
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseDocType.fxml"));
                DocTypeController docTypeController = new DocTypeController(listViewSubject.getSelectionModel().getSelectedItem(), activeProfessor);
                loader.setController(docTypeController);
                root = loader.load();
                stage.setTitle("Izbor vrste željenog materijala");
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

    public void reviewAction(ActionEvent actionEvent) throws IOException {
        if(listViewSubject.getSelectionModel().getSelectedItem()!=null) {
            Stage stageClose = (Stage) addDocumentBtn.getScene().getWindow();
            stageClose.close();
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/review.fxml"));
            ReviewController reviewController = new ReviewController(listViewSubject.getSelectionModel().getSelectedItem(), activeProfessor);
            loader.setController(reviewController);
            root = loader.load();
            stage.setTitle("Izbor vrste željenog materijala");
            stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
            stage.setMinHeight(300); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.setResizable(true);

            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Obavijest");
            alert.setHeaderText(null);
            alert.setContentText("Morate izabrati određeni predmet");

            alert.showAndWait();
        }
    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) profilBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/fxml/logIn.fxml"));
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) addDocumentBtn.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myProfile.fxml"));
        ProfileController profileController =new ProfileController(activeProfessor);
        loader.setController(profileController);
        root=loader.load();
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setResizable(true);


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
}
