package ba.unsa.etf.rpr.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchController {
    public ScrollPane imagePane;
    private String url = null;
    public TextField text;
    public Button btnSearch;
    public Button btnCancel;
    public Button btnOk;
    private Professor professor;
    private User user;
    private Student student;

    public SearchController(User user) {
        this.user = user;
        if(user instanceof Professor) {
            professor = (Professor) user;
        } else {
            student = (Student) user;
        }
    }

    @FXML
    public void initialize() {
        imagePane.setFitToHeight(true);
        imagePane.setFitToWidth(true);
    }

    public void searchAction() {
        new Thread(() -> {
            try {
                URL urlGiphy = new URL(String.format("https://api.giphy.com/v1/gifs/search?api_key=oNpc1jLCGwTMlUOrBdl9BdSD439AbTXl&q=%s&limit=25&offset=0&rating=R&lang=en", text.getText()));
                URLConnection urlConnection = urlGiphy.openConnection();
                BufferedReader ulaz = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder json = new StringBuilder();
                while ((line = ulaz.readLine()) != null) {
                    json.append(line);
                }
                ulaz.close();
                JSONObject jsonObject = new JSONObject(json.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                FlowPane flowPane = new FlowPane();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ImageView newImage = new ImageView("/images/loading.gif");
                    newImage.setFitWidth(200);
                    newImage.setFitHeight(200);
                    Button btnNewImage = new Button();
                    btnNewImage.setGraphic(newImage);
                    btnNewImage.setOnMouseClicked(e -> {
                        Button chooseBtn = (Button) e.getSource();
                        ImageView picture = (ImageView) chooseBtn.getGraphic();
                        url = picture.getImage().getUrl();
                    });
                    Platform.runLater(() -> {
                        flowPane.getChildren().add(btnNewImage);
                        imagePane.setContent(flowPane);
                    });
                    JSONObject pictures = jsonArray.getJSONObject(i);
                    String imgURL = pictures.getJSONObject("images").get("original_still").toString();
                    JSONObject jsonObject1 = new JSONObject(imgURL);
                    newImage.setImage(new Image(jsonObject1.get("url").toString().replace("?", "\n").split("\n")[0].replaceAll("media[0-9]", "i")));
                    newImage.setFitWidth(200);
                    newImage.setFitHeight(200);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void okAction() {
        if (url == null || url.trim().isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(bundle.getLocale().toString().equals("bs")) {
                alert.setTitle("Upozorenje!");
                alert.setContentText("Nijedna slika nije izabrana");
            } else {
                alert.setTitle("Warning!");
                alert.setContentText("No image selected");
            }
            alert.showAndWait();

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();

        } else {
            if(user instanceof Professor) {
                professor.setPicture(url);
                MaterialManagementDAO instance = MaterialManagementDAO.getInstance();
                instance.changeProfessor(professor);
            } else {
                student.setPicture(url);
                MaterialManagementDAO instance = MaterialManagementDAO.getInstance();
                instance.changeStudent(student);
            }
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
