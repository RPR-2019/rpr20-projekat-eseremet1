package ba.unsa.etf.rpr.project;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.robot.Motion;
import org.testfx.service.query.NodeQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.Locale.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class HomeProfessorControllerTest {

    @Start
    public void start (Stage stage) throws Exception {


        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeProfessor.fxml"), bundle);
        MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
        HomeProfessorController professorContoller=new HomeProfessorController(materialManagementDAO.searchProfessor("eseremet1"));
        loader.setController(professorContoller);
        root=loader.load();
        stage.setTitle("Početna stranica za profesora");
        stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
        stage.setMinHeight(500); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.show();
        stage.toFront();
    }


    @Test
    public void testButtons (FxRobot robot) {
        robot.lookup("#addDocumentBtn").queryAs(Button.class);
        robot.lookup("#quizBtn").queryAs(Button.class);
        robot.lookup("#reviewBtn").queryAs(Button.class);
    }

    @Test
    public void testAddDocument(FxRobot robot) {

        Button btn = robot.lookup("#addDocumentBtn").queryAs(Button.class);
        robot.clickOn("#addDocumentBtn");
        check(
                null, "Morate izabrati predmet za koji želite dodati materijal!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        Button logout = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testAddQuiz(FxRobot robot) {

        Button btn = robot.lookup("#quizBtn").queryAs(Button.class);
        robot.clickOn("#quizBtn");
        check(
                null, "Morate izabrati predmet za koji želite dodati kviz!", robot);
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        Button logout = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
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
        assertEquals("Elma", name.getText());
        Button btn1 = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testLanguage(FxRobot robot) {
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
