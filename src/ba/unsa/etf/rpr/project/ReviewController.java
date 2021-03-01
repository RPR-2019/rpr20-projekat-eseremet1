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
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReviewController {
    public ListView listView;
    private Professor activeProfessor;
    private Subject activeSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Material> materialCollection;
    private ArrayList<String> collection1 = new ArrayList<>();
    private ArrayList<String> collection2 = new ArrayList<>();
    private ArrayList<String> collection3 = new ArrayList<>();
    public RadioButton checkBtn;
    public RadioButton quizBtn;
    public RadioButton materialsBtn;
    private ArrayList<Material> allMaterials;
    private Set<Material> sortedMaterial;
    private List<Material> allQuizs;

    public ReviewController(Subject selectedItem, Professor professor) {
        activeProfessor=professor;
        activeSubject=selectedItem;
        materialManagementDAO = MaterialManagementDAO.getInstance();
        materialCollection= FXCollections.observableArrayList(materialManagementDAO.materials());
        //pronalazak svih materijala za dati predmet koji su javni
        ArrayList<Material> materials = materialCollection.stream().filter(material -> material.getSubject().getId() == activeSubject.getId() && material.getType()==Visibility.PUBLIC).collect(Collectors.toCollection(ArrayList::new));
        for (int i=0; i<materials.size(); i++) {
            collection1.add(materials.get(i).getName());
        }
        allMaterials = materials;
        sortedMaterial = sort();
        Iterator<Material> materialIterator = sortedMaterial.iterator();
        while (materialIterator.hasNext()) {
            Material material = materialIterator.next();
            collection2.add(material.getName());
        }
        Collections.reverse(collection2);
        allQuizs = getQuizs();

        for (int i=0; i<allQuizs.size(); i++) {
            collection3.add(allQuizs.get(i).getName());
        }


    }
    @FXML
    public void initialize() throws IOException {
        ObservableList<String> result1 = FXCollections.observableArrayList(collection1);
        materialsBtn.setSelected(true);
        listView.setItems(result1);

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
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(bundle.getLocale().toString().equals("bs")) {
                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Morate izabrati određeni materijal");
            } else {
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("You have to choose the material!");
            }
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
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Početna stranica");
        } else {
            stage.setTitle("Home");
        }
        stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
        stage.setMinHeight(500); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.setResizable(true);
            stage.show();
    }

    public void deleteMaterialAction(ActionEvent actionEvent) {
        if(listView.getSelectionModel().getSelectedItem()!=null) {
            if(activeProfessor.getSubject().getId() == activeSubject.getId()) {
                String nameFile = (String) listView.getSelectionModel().getSelectedItem();
                materialManagementDAO.deleteMaterial(nameFile);
                //spisak bez tog izbrisanog
                String finalNameFile = nameFile;
                ArrayList<String> withoutFile = collection1.stream().filter(name -> !name.equals(finalNameFile)).collect(Collectors.toCollection(ArrayList::new));
                ObservableList<String> result = FXCollections.observableArrayList(withoutFile);
                if(nameFile.contains("-QUIZ")) {
                    nameFile=nameFile.substring(0,nameFile.length()-5);
                }
                File file = new File("materials", nameFile);
                Path absolutePath = Paths.get(file.getAbsolutePath());
                absolutePath.toFile().delete();

                listView.setItems(result);
            } else {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(bundle.getLocale().toString().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText(null);
                    alert.setContentText("Materijale može brisati samo profesor na predmetu!");
                } else {
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Materials can only be deleted by the subject teacher!");
                }


                alert.showAndWait();
            }

        }
        else {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(bundle.getLocale().toString().equals("bs")) {
                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Morate izabrati određeni materijal");
            } else {
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("You have to choose the material!");
            }
            alert.showAndWait();
        }
    }

    public void showAllMaterialsAction(ActionEvent actionEvent) throws IOException {

        if(activeProfessor.getSubject().getId() == activeSubject.getId()) {
            Stage stageClose = (Stage) listView.getScene().getWindow();
            stageClose.close();
            Stage stage = new Stage();
            Parent root = null;
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/privateSubjects.fxml"), bundle);
            PrivateSubjectController reviewController = new PrivateSubjectController(activeSubject, activeProfessor);
            loader.setController(reviewController);
            root = loader.load();
            if(bundle.getLocale().toString().equals("bs")) {
                stage.setTitle(activeSubject.getName());
            } else {
                stage.setTitle(activeSubject.getName());
            }
            stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
            stage.setMinHeight(300); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.setResizable(true);

            stage.show();
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(bundle.getLocale().toString().equals("bs")) {
                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Ilegalna akcija!");
            } else {
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Illegal action!");
            }


            alert.showAndWait();
        }


    }

    public void checkAction(ActionEvent actionEvent) {
        ObservableList<String> result1 = FXCollections.observableArrayList(collection1);
        ObservableList<String> result2 = FXCollections.observableArrayList(collection2);
        if(checkBtn.isSelected()) {
            listView.setItems(result2);
        } else {
            listView.setItems(result1);
        }
    }

    public Set<Material> sort(){
        return new TreeSet<>(allMaterials);
    }

    public List<Material> getQuizs(){
        return filter(material -> material.getName().contains("-QUIZ"));
    }

    public List<Material> filter(Predicate<Material> criterion){
        return allMaterials.stream().filter(criterion).collect(Collectors.toList());
    }

    public void quizAction(ActionEvent actionEvent) {
        ObservableList<String> result1 = FXCollections.observableArrayList(collection1);
        ObservableList<String> result2 = FXCollections.observableArrayList(collection3);
        if(quizBtn.isSelected()) {
            listView.setItems(result2);
        } else {
            listView.setItems(result1);
        }
    }

    public void allMaterialsAction(ActionEvent actionEvent) {
        ObservableList<String> result1 = FXCollections.observableArrayList(collection1);
            listView.setItems(result1);

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
        Stage stageClose = (Stage) listView.getScene().getWindow();
        stageClose.close();
    }
}
