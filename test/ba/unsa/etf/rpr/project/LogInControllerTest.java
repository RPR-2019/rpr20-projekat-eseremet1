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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

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
        check(
                null, "Uspješno ste prijavljeni kao administrator!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
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
        check(
                null, "Uspješno ste prijavljeni kao profesor!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
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
        check(
                null, "Uspješno ste prijavljeni kao student!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }


    public void check(final String expectedHeader, final String expectedContent, FxRobot robot) {
        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedHeader, dialogPane.getHeaderText());
        assertEquals(expectedContent, dialogPane.getContentText());


    }

    private javafx.stage.Stage getTopModalStage(FxRobot robot) {

        // Get a list of windows but ordered from top[0] to bottom[n] ones.
        // It is needed to get the first found modal window.
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (javafx.stage.Stage) allWindows
                .stream()
                .filter(window -> window instanceof javafx.stage.Stage)
                .filter(window -> ((javafx.stage.Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
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
