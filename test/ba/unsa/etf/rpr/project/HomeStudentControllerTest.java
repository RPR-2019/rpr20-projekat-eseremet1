package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class HomeStudentControllerTest {
    @Start
    public void start (Stage stage) throws Exception {

        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Locale.setDefault(new Locale("bs", "BA"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeStudent.fxml"), bundle);
        MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
        HomeStudentController studentController=new HomeStudentController(materialManagementDAO.searchStudent("eseremet2"));
        loader.setController(studentController);
        root=loader.load();
        stage.setTitle("Početna stranica za studenta");
        stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
        stage.setMinHeight(500); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();
        stage.toFront();
    }


    @Test
    public void testButtons (FxRobot robot) {
        robot.lookup("#reviewBtn").queryAs(Button.class);
    }

    @Test
    public void testReview(FxRobot robot) {

        Button btn = robot.lookup("#reviewBtn").queryAs(Button.class);
        robot.clickOn("#reviewBtn");
        check(
                null, "Morate izabrati određeni predmet!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        Button logout = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testLogout(FxRobot robot) {
        Button btn = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testProfil(FxRobot robot) {
        Button btn = robot.lookup("#profilBtn").queryAs(Button.class);
        robot.clickOn("#profilBtn");
        Label name = robot.lookup("#nameField").queryAs(Label.class);
        assertEquals("Eldar", name.getText());
        Button btn1 = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testLanguage1(FxRobot robot) {
        robot.press(KeyCode.ALT).press(KeyCode.L).release(KeyCode.L).press(KeyCode.B).release(KeyCode.B).release(KeyCode.ALT);
        Button btn = robot.lookup("#profilBtn").queryAs(Button.class);
        robot.clickOn("#profilBtn");
        Label name = robot.lookup("#nameLabel").queryAs(Label.class);
        assertEquals("Ime", name.getText());
        Button btn1 = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
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
