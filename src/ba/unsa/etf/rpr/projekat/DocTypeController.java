package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DocTypeController {
    public void uploadPDFDocumentAction(ActionEvent actionEvent) {

    }

    public void createWordDocumentAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/addWordDocument.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Word document");
        stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();

    }

    public void createTxtDocumentAction(ActionEvent actionEvent) {

    }
}
