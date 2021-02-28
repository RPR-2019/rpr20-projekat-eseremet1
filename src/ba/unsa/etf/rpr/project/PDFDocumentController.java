package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class PDFDocumentController {
    private ObservableList<Visibility> visibilities;
    public Button loadBtn;
    public Label pdfNameLabel;
    private Subject selectedSubject;
    private Professor activeProfessor;
    public ChoiceBox<Visibility> visibilityBox;
    private Material material;
    public PDFDocumentController(Subject subject, Professor professor) {
        selectedSubject = subject;
        activeProfessor = professor;
        visibilities = FXCollections.observableArrayList(Visibility.values());
    }

    @FXML
    public void initialize() {
        visibilityBox.setItems(visibilities);
        visibilityBox.setValue(visibilities.get(0));
    }


    public void uploadPDFAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Izaberite datoteku");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF document","*.pdf"));
        File chooser = fileChooser.showOpenDialog(loadBtn.getScene().getWindow());
        Path path1;
        try {
            path1= Paths.get(chooser.getAbsolutePath());
        } catch (NullPointerException n) {
            return;
        }
        if(chooser==null) return;
        try {
            String text = new String(String.valueOf(Files.readAllBytes(chooser.toPath())));
            pdfNameLabel.setText(chooser.getName());
            String docName=pdfNameLabel.getText();
            if(pdfNameLabel.getText().isEmpty()) {
                pdfNameLabel.getStyleClass().removeAll("poljeJeIspravno");
                pdfNameLabel.getStyleClass().add("poljeNijeIspravno");
            }
            else {

                File file = new File("materials", chooser.getName());
                Path absolutePath = Paths.get(file.getAbsolutePath());
                Files.move(path1,absolutePath);
                Desktop.getDesktop().open(absolutePath.toFile());
                MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
                    material = new Material(materialManagementDAO.getId(), chooser.getName(), selectedSubject, 1);

                materialManagementDAO.addMaterial(material);

            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Pojavila se greška prilikom učitavanja .pdf datoteke - " + chooser.getName());
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.showAndWait();

        }
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        MaterialManagementDAO instance = MaterialManagementDAO.getInstance();
        if(!pdfNameLabel.getText().isEmpty()) {
            Material material = instance.searchMaterial(pdfNameLabel.getText());
            if (visibilityBox.getValue().equals(Visibility.PUBLIC)) {
                material.setType(Visibility.PUBLIC);
            } else if (visibilityBox.getValue().equals(Visibility.PRIVATE)) {
                material.setType(Visibility.PRIVATE);
            } else {
                material.setType(Visibility.CUSTOM);
            }
            instance.changeMaterial(material);
            Stage stage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/progress.fxml" ), bundle);
            Parent root = loader.load();
            stage.setScene(new Scene(root, 200, 100));
            stage.setResizable(false);
            stage.show();

        }
        Stage stageClose = (Stage) pdfNameLabel.getScene().getWindow();
        stageClose.close();

    }
}
