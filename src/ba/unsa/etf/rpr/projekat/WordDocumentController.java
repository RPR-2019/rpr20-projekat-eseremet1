package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WordDocumentController {
    public Button loadBtn;
    public Label pdfNameLabel;
    public ProgressBar pdfProgressBar;
    private Subject selectedSubject;
    private Professor activeProfessor;
    public ChoiceBox<Visibility> visibilityBox;
    private ObservableList<Visibility> visibilities;
    private Material material;
    public WordDocumentController(Subject subject, Professor professor) {
        selectedSubject = subject;
        activeProfessor = professor;
        visibilities = FXCollections.observableArrayList(Visibility.values());
    }

    @FXML
    public void initialize() {
        visibilityBox.setItems(visibilities);
        visibilityBox.setValue(visibilities.get(0));
    }

    public void uploadWordAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Izaberite datoteku");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document","*.docx"));
        File chooser = fileChooser.showOpenDialog(loadBtn.getScene().getWindow());
        Path path1= Paths.get(chooser.getAbsolutePath());
        if(chooser==null) return;
        try {
            String text = new String(String.valueOf(Files.readAllBytes(chooser.toPath())));
            pdfNameLabel.setText(chooser.getName());
            pdfProgressBar.setProgress(100);
            String docName=pdfNameLabel.getText();
            if(pdfNameLabel.getText().isEmpty()) {
                pdfNameLabel.getStyleClass().removeAll("poljeJeIspravno");
                pdfNameLabel.getStyleClass().add("poljeNijeIspravno");
            }
            else {

                File file = new File("materials",chooser.getName());
                Path absolutePath = Paths.get(file.getAbsolutePath());
                Files.move(path1,absolutePath);
                Desktop.getDesktop().open(absolutePath.toFile());
                MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
                material = new Material(materialManagementDAO.getId(), chooser.getName(), selectedSubject, 1);

                materialManagementDAO.addMaterial(material);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Pojavila se greška prilikom učitavanja .docx datoteke -" + chooser.getName());
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.showAndWait();

        }
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        MaterialManagementDAO instance = MaterialManagementDAO.getInstance();
        Material material = instance.searchMaterial(pdfNameLabel.getText());
        if(visibilityBox.getValue().equals(Visibility.PUBLIC)) {
            material.setType(Visibility.PUBLIC);
        } else if(visibilityBox.getValue().equals(Visibility.PRIVATE)) {
            material.setType(Visibility.PRIVATE);
        } else {
            material.setType(Visibility.CUSTOM);
        }
        instance.changeMaterial(material);
        Stage stageClose = (Stage) pdfNameLabel.getScene().getWindow();
        stageClose.close();
    }
}
