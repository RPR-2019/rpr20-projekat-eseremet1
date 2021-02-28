package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class NewSubjectControllerTest {
    MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"), bundle);
            NewSubjectController subjectController=new NewSubjectController(null, materialManagementDAO.professors());
            loader.setController(subjectController);
            root=loader.load();
            stage.setTitle("Dodavanje profesora");
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
        Button addBtn = robot.lookup("#addBtn").queryAs(Button.class);
        Button cancelBtn = robot.lookup("#cancelBtn").queryAs(Button.class);
        TextField name = robot.lookup("#nameField").queryAs(TextField.class);

    }

    @Test
    public void testNewSubject1(FxRobot robot) throws IOException {
        robot.clickOn("#addBtn");
        TextField name = robot.lookup("#nameField").queryAs(TextField.class);
        Background bg = name.getBackground();
        boolean color = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("d43417"))
                color= true;

        assertTrue(color);
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
