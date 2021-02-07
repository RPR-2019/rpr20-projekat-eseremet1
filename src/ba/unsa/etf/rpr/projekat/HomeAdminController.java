package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeAdminController {
    public Button proffesorBtn;

    public void proffesorAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/controlProfessors.fxml"));
        stage.setTitle("Upravljanje profesorima");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();
        Stage stageClose = (Stage) proffesorBtn.getScene().getWindow();
        stageClose.close();
    }

    public void studentAction(ActionEvent actionEvent) {
    }

    public void subjectAction(ActionEvent actionEvent) {
    }
}
