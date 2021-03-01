package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class NewStudentControllerTest {
    MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Locale.setDefault(new Locale("bs", "BA"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addStudent.fxml"), bundle);
            NewStudentController studentController =new NewStudentController(null);
            //new Student(1,"Eldar","Šeremet","eseremet2@etf.unsa.ba","eseremet2","Seremet123","https://i.giphy.com/media/yFQ0ywscgobJK/giphy_s.gif","18318")
            loader.setController(studentController);
            root=loader.load();
            stage.setTitle("Dodavanje studenta");
            stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
            stage.setResizable(false);
            stage.show();
            stage.toFront();

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
    public void testNewStudent1(FxRobot robot) throws IOException {
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
    public void testNewStudent2(FxRobot robot) throws IOException {
        robot.clickOn("#nameField");
        robot.write("Ensar");
        robot.clickOn("#surnameField");
        robot.write("Šeremet");
        robot.clickOn("#usernameField");
        robot.write("eseremet100");
        robot.clickOn("#generateBtn");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#cancelBtn");
    }


    public void check(final String expectedHeader, final String expectedContent, FxRobot robot) {
        final Stage actualAlertDialog = getTopModalStage(robot);
        assertNotNull(actualAlertDialog);

        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        assertEquals(expectedHeader, dialogPane.getHeaderText());
        assertEquals(expectedContent, dialogPane.getContentText());


    }

    private Stage getTopModalStage(FxRobot robot) {
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (Stage) allWindows
                .stream()
                .filter(window -> window instanceof Stage)
                .filter(window -> ((Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }

}
