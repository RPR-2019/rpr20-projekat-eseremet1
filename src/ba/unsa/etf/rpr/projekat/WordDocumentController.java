package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

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

    public WordDocumentController(Subject subject) {
        selectedSubject = subject;
    }

    public void uploadWordAction(ActionEvent actionEvent) {
        FileChooser izbornik = new FileChooser();
        izbornik.setTitle("Izaberite datoteku");
        izbornik.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document","*.docx"));
        File izabrani = izbornik.showOpenDialog(loadBtn.getScene().getWindow());
        Path path1= Paths.get(izabrani.getAbsolutePath());
        if(izabrani==null) return;
        try {
            String text = new String(String.valueOf(Files.readAllBytes(izabrani.toPath())));
            pdfNameLabel.setText(izabrani.getName());
            pdfProgressBar.setProgress(100);
            String docName=pdfNameLabel.getText();
            if(pdfNameLabel.getText().isEmpty()) {
                pdfNameLabel.getStyleClass().removeAll("poljeJeIspravno");
                pdfNameLabel.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                Path absolutePath = Paths.get("C:\\Users\\User\\IdeaProjects\\upravljanjeNastavnimMaterijalom\\resources\\materials\\"+izabrani.getName());
                File file = new File(String.valueOf(absolutePath));
                Files.move(path1,absolutePath);
                Desktop.getDesktop().open(absolutePath.toFile());
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Pojavila se greška prilikom učitavanja .docx datoteke -" + izabrani.getName());
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.showAndWait();

        }
    }
}
