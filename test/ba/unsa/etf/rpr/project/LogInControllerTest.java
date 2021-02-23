package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class LogInControllerTest {
        public TextField userNameField;
        public PasswordField passwordField;
        public Button logInBtn;

        @Start
        public void start (Stage stage) throws Exception {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/logIn.fxml" ), bundle);
            Parent root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.toFront();
        }


        @Test
        public void testUsernameField (FxRobot robot) {
            Button logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
            robot.clickOn("#logInBtn");
        }

        @Test
        public void testValidUsername(FxRobot robot) {
            logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
            userNameField = robot.lookup("#userNameField").queryAs(TextField.class);
            robot.clickOn("#userNameField");
            robot.write("eseremet1");
            robot.clickOn("#logInBtn");
            Background bg = userNameField.getBackground();
            boolean color = false;
            for (BackgroundFill bf : bg.getFills())
                if (bf.getFill().toString().contains("d1ff47"))
                    color= true;

            assertTrue(color);
        }

    @Test
    public void testInvalidUsername(FxRobot robot) {
        logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
        passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
        robot.clickOn("#logInBtn");
        Background bg = passwordField.getBackground();
        boolean color = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("d43417"))
                color= true;

        assertTrue(color);
    }

}
