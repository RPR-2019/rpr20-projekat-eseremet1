package ba.unsa.etf.rpr.projekat;

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

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReviewStudentController {
    public ListView listView;

    private Subject activeSubject;
    private MaterialManagementDAO materialManagementDAO;
    private ObservableList<Material> materialCollection;
    private ArrayList<String> collection = new ArrayList<>();
    private Student activeStudent;

    public ReviewStudentController(Subject selectedItem, Student student) {
        activeStudent = student;
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
                Scanner reading;
                String realName = nameFile.substring(0, nameFile.length() - 5);
                try {
                    reading = new Scanner(new FileReader(realName));
                    int i = 1;
                    String text = "";
                    while (reading.hasNext()) {
                        String row = reading.nextLine();
                        if (!row.contains("Tačan odgovor:")) {
                            text = text + row + "\n";
                        }
                        i = i + 1;
                    }
                    String path = "materials\\" + realName+"xxdeletexx";
                    File file = new File(path);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try (FileWriter f = new FileWriter(file);
                         BufferedWriter b = new BufferedWriter(f);
                         PrintWriter p = new PrintWriter(b);) {
                        p.println(text);

                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Datoteka " + realName + " ne postoji ili se ne može otvoriti");
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file = new File("materials", realName + "xxdeletexx");
                Path absolutePath = Paths.get(file.getAbsolutePath());

                try {
                    Desktop.getDesktop().open(absolutePath.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File file = new File("materials", nameFile);
                Path absolutePath = Paths.get(file.getAbsolutePath());

                try {
                    Desktop.getDesktop().open(absolutePath.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeStudent.fxml"), bundle);
        HomeStudentController studentContoller=new HomeStudentController(materialManagementDAO.searchStudent(activeStudent.getUsername()));
        loader.setController(studentContoller);
        root=loader.load();
        stage.setTitle("Početna stranica za profesora");
        stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
        stage.setMinHeight(500); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.setResizable(true);
            stage.show();
    }

}
