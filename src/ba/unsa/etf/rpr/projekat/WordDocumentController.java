package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
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
    public WordDocumentController(Subject subject, Professor professor) {
        selectedSubject = subject;
        activeProfessor = professor;
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
                Path absolutePath = Paths.get("C:\\Users\\User\\IdeaProjects\\upravljanjeNastavnimMaterijalom\\resources\\materials\\"+chooser.getName());
                File file = new File(String.valueOf(absolutePath));
                Files.move(path1,absolutePath);
                Desktop.getDesktop().open(absolutePath.toFile());
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
        Stage stageClose = (Stage) pdfNameLabel.getScene().getWindow();
        stageClose.close();
    }
}
