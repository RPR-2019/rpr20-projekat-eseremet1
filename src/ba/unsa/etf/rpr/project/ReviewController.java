package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReviewController {
    public ListView listView;
    private Professor activeProfessor;
    private Subject activeSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Material> materialCollection;
    private ArrayList<String> collection = new ArrayList<>();

    public ReviewController(Subject selectedItem, Professor professor) {
        activeProfessor=professor;
        activeSubject=selectedItem;
        materialManagementDAO = MaterialManagementDAO.getInstance();
        materialCollection= FXCollections.observableArrayList(materialManagementDAO.materials());
        //pronalazak svih materijala za dati predmet koji su javni
        ArrayList<Material> materials = materialCollection.stream().filter(material -> material.getSubject().getId() == activeSubject.getId() && material.getType()==Visibility.PUBLIC).collect(Collectors.toCollection(ArrayList::new));
        for (int i=0; i<materials.size(); i++) {
            collection.add(materials.get(i).getName());
        }
    }
    @FXML
    public void initialize() throws IOException {
        ObservableList<String> result = FXCollections.observableArrayList(collection);
        listView.setItems(result);
        }

    public void showMaterialAction(ActionEvent actionEvent) {
        if(listView.getSelectionModel().getSelectedItem()!=null) {
            String nameFile = (String) listView.getSelectionModel().getSelectedItem();
            if(nameFile.contains("-QUIZ")) {
                nameFile=nameFile.substring(0,nameFile.length()-5);
            }
            File file = new File("materials", nameFile);
            Path absolutePath = Paths.get(file.getAbsolutePath());

            try {
                Desktop.getDesktop().open(absolutePath.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Obavijest");
            alert.setHeaderText(null);
            alert.setContentText("Morate izabrati određeni materijal");

            alert.showAndWait();
        }
    }

    public void backAction(ActionEvent actionEvent) throws IOException {
            Stage stageClose = (Stage) listView.getScene().getWindow();
            stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeProfessor.fxml"), bundle);
        HomeProfessorController professorContoller=new HomeProfessorController(materialManagementDAO.searchProfessor(activeProfessor.getUsername()));
        loader.setController(professorContoller);
        root=loader.load();
        stage.setTitle("Početna stranica za profesora");
        stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
        stage.setMinHeight(500); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.setResizable(true);
            stage.show();
    }

    public void deleteMaterialAction(ActionEvent actionEvent) {
        if(listView.getSelectionModel().getSelectedItem()!=null) {
            String nameFile = (String) listView.getSelectionModel().getSelectedItem();
            materialManagementDAO.deleteMaterial(nameFile);
            File file = new File("materials", nameFile);
            Path absolutePath = Paths.get(file.getAbsolutePath());
            absolutePath.toFile().delete();
            //spisak bez tog izbrisanog
            ArrayList<String> withoutFile = collection.stream().filter(name -> !name.equals(nameFile)).collect(Collectors.toCollection(ArrayList::new));
            ObservableList<String> result = FXCollections.observableArrayList(withoutFile);
            listView.setItems(result);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Obavijest");
            alert.setHeaderText(null);
            alert.setContentText("Morate izabrati određeni materijal");

            alert.showAndWait();
        }
    }

    public void showAllMaterialsAction(ActionEvent actionEvent) throws IOException {

        Stage stageClose = (Stage) listView.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/privateSubjects.fxml"), bundle);
        PrivateSubjectsController reviewController = new PrivateSubjectsController(activeSubject, activeProfessor);
        loader.setController(reviewController);
        root = loader.load();
        stage.setTitle("Izbor vrste željenog materijala");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.setResizable(true);

        stage.show();

    }

    public void checkAction(ActionEvent actionEvent) {


    }
}