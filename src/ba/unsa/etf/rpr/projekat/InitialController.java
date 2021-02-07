package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InitialController {
    public void logInAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/logIn.fxml"));
        stage.setTitle("Prijava");
        stage.setScene(new Scene(root, 1200, 700));
        stage.setResizable(false);
        stage.show();
    }
}
