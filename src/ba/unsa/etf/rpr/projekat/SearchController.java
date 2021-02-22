package ba.unsa.etf.rpr.projekat;

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

    public SearchController(Professor activeProfessor) {
        professor=activeProfessor;
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje!");
            alert.setContentText("Nijedna slika nije izabrana");
            alert.showAndWait();
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();

        } else {
                professor.setPicture(url);
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
