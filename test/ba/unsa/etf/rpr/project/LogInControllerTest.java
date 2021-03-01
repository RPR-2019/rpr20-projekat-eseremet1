package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class LogInControllerTest {
        public TextField userNameField;
        public PasswordField passwordField;
        public Button logInBtn;

        @Start
        public void start (Stage stage) throws Exception {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Locale.setDefault(new Locale("bs", "BA"));
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/logIn.fxml" ), bundle);
            Parent root = loader.load();
            stage.setScene(new Scene(root, 1200, 700));
            stage.setResizable(false);
            stage.show();
            stage.toFront();
        }


        @Test
        public void testUsernameField (FxRobot robot) {
            Button logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
            robot.clickOn("#logInBtn");
            robot.clickOn("#cancelBtn");
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
            robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
            robot.clickOn("#cancelBtn");
        }

    @Test
    public void testlogInAdminFxRobot (FxRobot robot) {
        logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
        passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
        userNameField = robot.lookup("#userNameField").queryAs(TextField.class);
        robot.clickOn("#userNameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#logInBtn");
        Button professorBtn = robot.lookup("#proffesorBtn").queryAs(Button.class);
        robot.clickOn("#proffesorBtn");
    }

    @Test
    public void testlogInProfessorFxRobot (FxRobot robot) {

        logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
        passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
        userNameField = robot.lookup("#userNameField").queryAs(TextField.class);
        robot.clickOn("#userNameField");
        robot.write("eseremet1");
        robot.clickOn("#passwordField");
        robot.write("Seremet123");
        robot.clickOn("#logInBtn");
        Button review = robot.lookup("#reviewBtn").queryAs(Button.class);
    }

    @Test
    public void testlogInStudentFxRobot (FxRobot robot) {

        logInBtn = robot.lookup("#logInBtn").queryAs(Button.class);
        passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
        userNameField = robot.lookup("#userNameField").queryAs(TextField.class);
        robot.clickOn("#userNameField");
        robot.write("eseremet2");
        robot.clickOn("#passwordField");
        robot.write("Seremet123");
        robot.clickOn("#logInBtn");
        Button review = robot.lookup("#reviewBtn").queryAs(Button.class);
    }


    @BeforeEach
    public void reset() throws SQLException {
            MaterialManagementDAO instance = MaterialManagementDAO.getInstance();
        instance.defaultData();
    }

    @AfterEach
    public void closeWindow(FxRobot robot) {
        if (robot.lookup("#cancelBtn").tryQuery().isPresent())
            robot.clickOn("#cancelBtn");
    }


}
