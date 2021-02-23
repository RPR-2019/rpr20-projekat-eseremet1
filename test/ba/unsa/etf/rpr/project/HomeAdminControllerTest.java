package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


@ExtendWith(ApplicationExtension.class)
public class HomeAdminControllerTest {
    public TextField userNameField;
    public PasswordField passwordField;
    public Button logInBtn;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/homeAdmin.fxml" ), bundle);
        Parent root = loader.load();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }


    @Test
    public void testProfessorBtn (FxRobot robot) {
        Button professorBtn = robot.lookup("#proffesorBtn").queryAs(Button.class);
        robot.clickOn("#proffesorBtn");
        Button logout = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testStudentBtn(FxRobot robot) {
        Button studentBtn = robot.lookup("#studentBtn").queryAs(Button.class);
        robot.clickOn("#studentBtn");
        Button logout = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

    @Test
    public void testSubjectBtn (FxRobot robot) {
        Button subjectBtn = robot.lookup("#subjectBtn").queryAs(Button.class);
        robot.clickOn("#subjectBtn");
        Button logout = robot.lookup("#logoutBtn").queryAs(Button.class);
        robot.clickOn("#logoutBtn");
        Button cancel = robot.lookup("#cancelBtn").queryAs(Button.class);
        robot.clickOn("#cancelBtn");
    }

}
