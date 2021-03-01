package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class HomeAdminController {
    public Button proffesorBtn;
    public Button studentBtn;
    public Button subjectBtn;

    @FXML
    public void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Tooltip toolTip1 = new Tooltip();
        Tooltip toolTip2 = new Tooltip();
        Tooltip toolTip3 = new Tooltip();


        if(bundle.getLocale().toString().equals("bs")) {
            toolTip1.setText("Upravljanje profesorima");
            toolTip2.setText("Upravljanje studentima");
            toolTip3.setText("Upravljanje predmetima");

        } else {
            toolTip1.setText("Manage professors");
            toolTip2.setText("Manage students");
            toolTip3.setText("Manage subjects");
        }

        proffesorBtn.setTooltip(toolTip1);
        studentBtn.setTooltip(toolTip2);
        subjectBtn.setTooltip(toolTip3);
    }
    public void proffesorAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/controlProfessors.fxml" ), bundle);
        Parent root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Upravljanje profesorima");
        } else {
            stage.setTitle("Manage professors");
        }
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();
        Stage stageClose = (Stage) proffesorBtn.getScene().getWindow();
        stageClose.close();
    }

    public void studentAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/controlStudents.fxml" ), bundle);
        Parent root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Upravljanje studentima");
        } else {
            stage.setTitle("Manage students");
        }
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();
        Stage stageClose = (Stage) proffesorBtn.getScene().getWindow();
        stageClose.close();
    }

    public void subjectAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/controlSubjects.fxml" ), bundle);
        Parent root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            stage.setTitle("Upravljanje predmetima");
        } else {
            stage.setTitle("Manage subjects");
        }
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();
        Stage stageClose = (Stage) proffesorBtn.getScene().getWindow();
        stageClose.close();
    }
}
