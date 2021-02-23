package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class InitialController {
    public Button logInBtn;

    public void logInAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/logIn.fxml" ), bundle);
        Parent root = loader.load();
        stage.setTitle("Prijava");
        stage.setScene(new Scene(root, 1200, 700));
        stage.setResizable(false);
        stage.show();
        Stage stageClose = (Stage) logInBtn.getScene().getWindow();
        stageClose.close();
    }
}
