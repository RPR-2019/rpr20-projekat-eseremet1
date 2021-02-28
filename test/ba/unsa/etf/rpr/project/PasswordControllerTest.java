package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.assertj.core.error.future.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class PasswordControllerTest {
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changePassword.fxml"), bundle);
        PasswordController passwordController =new PasswordController(new Professor(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","Seremet12","https://i.giphy.com/media/yFQ0ywscgobJK/giphy_s.gif"));
        loader.setController(passwordController);
        root=loader.load();
        stage.setTitle("Promijenite lozinku!");
        stage.setScene(new Scene(root, 1200, 500)); //stavljamo početni ekran
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }


    @Test
    public void testInvalidPassword1 (FxRobot robot) {
        PasswordField btn1 = robot.lookup("#newPasswordField").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField");
        robot.write("elma");
        PasswordField btn2 = robot.lookup("#newPasswordField1").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField1");
        robot.write("elma");
        robot.clickOn("#changeBtn");
        check(
                "ba.unsa.etf.rpr.project.Password must be at least 8 characters long!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

    }

    @Test
    public void testInvalidPassword2 (FxRobot robot) {
        PasswordField btn1 = robot.lookup("#newPasswordField").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField");
        robot.write("elma");
        PasswordField btn2 = robot.lookup("#newPasswordField1").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField1");
        robot.write("elma1");
        robot.clickOn("#changeBtn");
        check(
                "Not confirmed the same password!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void testInvalidPassword3 (FxRobot robot) {
        PasswordField btn1 = robot.lookup("#newPasswordField").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField");
        robot.write("seremet123");
        PasswordField btn2 = robot.lookup("#newPasswordField1").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField1");
        robot.write("seremet123");
        robot.clickOn("#changeBtn");
        check(
                "The password must have uppercase, lowercase and number!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void testInvalidPassword4 (FxRobot robot) {
        PasswordField btn1 = robot.lookup("#newPasswordField").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField");
        robot.write("seremetelma");
        PasswordField btn2 = robot.lookup("#newPasswordField1").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField1");
        robot.write("seremetelma");
        robot.clickOn("#changeBtn");
        check(
                "The password must have uppercase, lowercase and number!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void testInvalidPassword5 (FxRobot robot) {
        PasswordField btn1 = robot.lookup("#newPasswordField").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField");
        robot.write("SEREMET123");
        PasswordField btn2 = robot.lookup("#newPasswordField1").queryAs(PasswordField.class);
        robot.clickOn("#newPasswordField1");
        robot.write("SEREMET123");
        robot.clickOn("#changeBtn");
        check(
                "The password must have uppercase, lowercase and number!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }


    public void check(final String expectedContent, FxRobot robot) {
        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedContent, dialogPane.getContentText());


    }

    private javafx.stage.Stage getTopModalStage(FxRobot robot) {
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (javafx.stage.Stage) allWindows
                .stream()
                .filter(window -> window instanceof javafx.stage.Stage)
                .filter(window -> ((javafx.stage.Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }
}
