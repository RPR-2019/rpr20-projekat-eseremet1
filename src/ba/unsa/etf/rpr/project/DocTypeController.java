package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class DocTypeController {
    public Button pdfBtn;
    private Subject selectedSubject;
    private Professor activeProfessor;

    public DocTypeController(Subject subject, Professor professor) {
        selectedSubject = subject;
        activeProfessor = professor;
    }

    public void uploadPDFDocumentAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addPDFDocument.fxml"), bundle);
        PDFDocumentController PDFDocumentController=new PDFDocumentController(selectedSubject,activeProfessor);
        loader.setController(PDFDocumentController);
        root=loader.load();
        stage.setTitle("PDF document");
        stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();

        Stage stage1 = (Stage) pdfBtn.getScene().getWindow();
        stage1.close();
    }

    public void createWordDocumentAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWordDocument.fxml"), bundle);
        WordDocumentController wordDocumentController=new WordDocumentController(selectedSubject,activeProfessor);
        loader.setController(wordDocumentController);
        root=loader.load();
        stage.setTitle("Word document");
        stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();

        Stage stage1 = (Stage) pdfBtn.getScene().getWindow();
        stage1.close();

    }

    public void createTxtDocumentAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addTXTDocument.fxml"), bundle);
            TXTDocumentController TXTDocumentController=new TXTDocumentController(selectedSubject,activeProfessor);
            loader.setController(TXTDocumentController);
            root=loader.load();
            stage.setTitle("Izbor vrste željenog materijala");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setMinHeight(300); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.show();

            Stage stage1 = (Stage) pdfBtn.getScene().getWindow();
            stage1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
