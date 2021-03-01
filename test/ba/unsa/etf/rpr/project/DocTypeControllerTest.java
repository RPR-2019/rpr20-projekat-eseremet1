package ba.unsa.etf.rpr.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.w3c.dom.Text;

import java.util.Locale;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
public class DocTypeControllerTest {
    @Start
    public void start (Stage stage) throws Exception {
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Locale.setDefault(new Locale("bs", "BA"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseDocType.fxml"), bundle);
        DocTypeController docTypeController = new DocTypeController(new Subject(2,"RPR"), new Professor(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","Seremet12","https://i.giphy.com/media/yFQ0ywscgobJK/giphy_s.gif"));
        loader.setController(docTypeController);
        root = loader.load();
        stage.setTitle("Izbor vrste željenog materijala");
        stage.setScene(new Scene(root, 500, 300)); //stavljamo početni ekran
        stage.setMinHeight(300); //da se ne može više smanjivati
        stage.setMinWidth(200);
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }


    @Test
    public void testButtons (FxRobot robot) {
        Button pdfBtn = robot.lookup("#pdfBtn").queryAs(Button.class);
        Button wordBtn = robot.lookup("#wordBtn").queryAs(Button.class);
        Button txtBtn = robot.lookup("#txtBtn").queryAs(Button.class);
    }

    @Test
    public void testPDFBtn(FxRobot robot) {
        Button pdfBtn = robot.lookup("#pdfBtn").queryAs(Button.class);
        robot.clickOn("#pdfBtn");
        Button finishBtn = robot.lookup("#finishBtn").queryAs(Button.class);
        robot.clickOn("#finishBtn");
    }

    @Test
    public void testWordBtn (FxRobot robot) {
        Button wordBtn = robot.lookup("#wordBtn").queryAs(Button.class);
        robot.clickOn("#wordBtn");
        Button finishBtn = robot.lookup("#finishBtn").queryAs(Button.class);
        robot.clickOn("#finishBtn");
    }

    @Test
    public void testTXTBtn (FxRobot robot) {
        Button txtBtn = robot.lookup("#txtBtn").queryAs(Button.class);
        robot.clickOn("#txtBtn");
        TextField field = robot.lookup("#docNameField").queryAs(TextField.class);
        robot.clickOn("#docNameField");
    }
}
