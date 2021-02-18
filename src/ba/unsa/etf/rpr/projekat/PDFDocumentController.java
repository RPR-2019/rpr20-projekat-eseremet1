package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.Professor;
import ba.unsa.etf.rpr.projekat.Subject;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFDocumentController {
    public Button loadBtn;
    public Label pdfNameLabel;
    public ProgressBar pdfProgressBar;
    private Subject selectedSubject;
    private Professor activeProfessor;
    public PDFDocumentController(Subject subject, Professor professor) {
        selectedSubject = subject;
        activeProfessor = professor;
    }

    public void uploadPDFAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Izaberite datoteku");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF document","*.pdf"));
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
                Material material = new Material(materialManagementDAO.getId(), chooser.getName(),selectedSubject);
                materialManagementDAO.addMaterial(material);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Pojavila se greška prilikom učitavanja .pdf datoteke -" + chooser.getName());
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.showAndWait();

        }
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) pdfNameLabel.getScene().getWindow();
        stageClose.close();
    }
}
