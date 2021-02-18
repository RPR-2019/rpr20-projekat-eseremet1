package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class TXTDocumentController {
    private Subject selectedSubject;
    private Professor activeProfessor;
    public TextArea textAreaField;
    public Label statusBarLabel;
    public ListView listView;
    public TextField docNameField;

    public TXTDocumentController(Subject subject, Professor professor) {
        selectedSubject = subject;
        activeProfessor = professor;
    }


    ArrayList<File> fileList = new ArrayList<>();
    @FXML
    public void initialize() {
        docNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (docNameField.getText().isEmpty()) {
                    docNameField.getStyleClass().removeAll("poljeJeIspravno");
                    docNameField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    docNameField.getStyleClass().removeAll("poljeNijeIspravno");
                    docNameField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });


    }

    public void exitAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void openAction(ActionEvent actionEvent) {
        FileChooser izbornik = new FileChooser();
        izbornik.setTitle("Izaberite datoteku");
        izbornik.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tektualna datoteka","*.txt"));
        File izabrani = izbornik.showOpenDialog(textAreaField.getScene().getWindow());
        if(izabrani==null) return;
        try {
            String text = new String(Files.readAllBytes(izabrani.toPath()));
            textAreaField.setText(text);
            statusBarLabel.setText("Datoteka uspješno učitana!");
            ObservableList<String> items = FXCollections.observableArrayList(izabrani.getName());
            listView.setItems(items);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Pojavila se greška prilikom učitavanja .txt datoteke -" + izabrani.getName());
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.showAndWait();

        }

    }

    public void saveAction(ActionEvent actionEvent) {

        JFileChooser fs = new JFileChooser(new File("c::\\"));
        fs.setDialogTitle("Spremi dokument");
        fs.setFileFilter(new FileTypeFilter(".txt", "Tekstualna datoteka"));
        int result = fs.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            String content = textAreaField.getText();
            File file =fs.getSelectedFile();

            try {
                FileWriter fw = new FileWriter(file.getPath());
                fw.write(content);
                fw.flush();
                fw.close();
                statusBarLabel.setText("Datoteka uspješno spremljena!");

            } catch(Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }

    }

    public void newAction(ActionEvent actionEvent) {
        textAreaField.setText("");
        statusBarLabel.setText("Text editor je pokrenut!");
        docNameField.setText("");
        ObservableList<String> items = FXCollections.observableArrayList("");
        listView.setItems(items);

    }

    public void uploadAction(ActionEvent actionEvent) throws IOException {

        String docName = docNameField.getText();
        if (docNameField.getText().isEmpty()) {
            docNameField.getStyleClass().removeAll("poljeJeIspravno");
            docNameField.getStyleClass().add("poljeNijeIspravno");
        } else {
            String path = "materials\\" + docNameField.getText();

            File file = new File(path);
            try {
                file.createNewFile();
                try (FileWriter f = new FileWriter(file);
                     BufferedWriter b = new BufferedWriter(f);
                     PrintWriter p = new PrintWriter(b);) {
                    p.println(textAreaField.getText());
                    statusBarLabel.setText("Materijal je objavljen!");
                    Stage stage = (Stage) docNameField.getScene().getWindow();
                    stage.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
