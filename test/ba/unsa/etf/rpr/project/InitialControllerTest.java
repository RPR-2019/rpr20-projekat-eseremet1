package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

@ExtendWith(ApplicationExtension.class)
public class InitialControllerTest {

    @Start
    public void start (Stage stage) throws Exception {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Locale.setDefault(new Locale("bs", "BA"));
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/initial.fxml" ), bundle);
            Parent root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.toFront();
    }


    @Test
    public void testButtonClick (FxRobot robot) {
        Button logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
        robot.clickOn("#logInBtn");
    }

}
