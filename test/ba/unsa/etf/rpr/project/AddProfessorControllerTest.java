package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class AddProfessorControllerTest {
    MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProfessor.fxml"), bundle);
            AddProfessorController professorContoller=new AddProfessorController(null, materialManagementDAO.subjects());
            loader.setController(professorContoller);
            root=loader.load();
            stage.setTitle("Dodavanje profesora");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testWindow(FxRobot robot) {
        Button generateBtnn = robot.lookup("#generateBtn").queryAs(Button.class);
        TextField name = robot.lookup("#nameField").queryAs(TextField.class);
        TextField surname = robot.lookup("#surnameField").queryAs(TextField.class);
    }

    @Test
    public void testGenerate(FxRobot robot) {
        Button generateBtnn = robot.lookup("#generateBtn").queryAs(Button.class);
        robot.clickOn("#generateBtn");
        PasswordField passwordField = robot.lookup("#passwordField").queryAs(PasswordField.class);
        check(
                "Vaša lozinka glasi: ", passwordField.getText(), robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        assertTrue(!passwordField.getText().isEmpty());
    }

    @Test
    public void testNewProfessor1(FxRobot robot) throws IOException {
        robot.clickOn("#nameField");
        robot.write("Elma");
        robot.clickOn("#surnameField");
        robot.write("Šeremet");
        robot.clickOn("#usernameField");
        robot.write("eseremet1");
        robot.clickOn("#generateBtn");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#addBtn");
        check(
                null, "Username već postoji!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void testNewProfessor2(FxRobot robot) throws IOException {
        robot.clickOn("#nameField");
        robot.write("Elma");
        robot.clickOn("#surnameField");
        robot.write("Šeremet");
        robot.clickOn("#usernameField");
        robot.write("eseremet100");
        robot.clickOn("#generateBtn");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#cancelBtn");
    }


    public void check(final String expectedHeader, final String expectedContent, FxRobot robot) {
        final javafx.stage.Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedHeader, dialogPane.getHeaderText());
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
