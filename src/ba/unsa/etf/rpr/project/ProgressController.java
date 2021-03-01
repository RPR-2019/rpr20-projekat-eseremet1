package ba.unsa.etf.rpr.project;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {
    public ProgressBar progressBar;
    public ProgressIndicator indicator;
    public Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Load task = new Load();
        progressBar.progressProperty().bind(task.progressProperty());
        indicator.progressProperty().bind(task.progressProperty());
        label.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }

}
