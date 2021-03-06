package ba.unsa.etf.rpr.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class QuizController {
    private Professor activeProfessor;
    public TextArea textAreaField;
    public Label statusBarLabel;
    public ListView listView;
    public TextField docNameField;
    public ChoiceBox<Visibility> visibilityBox;
    private ObservableList<Visibility> visibilities;
    public QuizController(Professor activeProfessor) {
        this.activeProfessor = activeProfessor;
        visibilities = FXCollections.observableArrayList(Visibility.values());

    }

    @FXML
    public void initialize() {
        visibilityBox.setItems(visibilities);
        visibilityBox.setValue(visibilities.get(0));
        textAreaField.setText("1. Pitanje1"+"\n"+"a. Odgovor1"+"\n"+"b. Odgovor2"+"\n"+"c. Odgovor3"+"\n"+ "Tačan odgovor: Odgovor"+"\n"+"2. Pitanje2"+"\n"+"a. Odgovor1"+"\n"+"b. Odgovor2"+"\n"+"c. Odgovor3"+"\n"+ "Tačan odgovor: Odgovor"+"\n"+"3. Pitanje3"+"\n"+"a. Odgovor1"+"\n"+"b. Odgovor2"+"\n"+"c. Odgovor3"+"\n"+ "Tačan odgovor: Odgovor");
        ResourceBundle bundle1 = ResourceBundle.getBundle("Translation");
        if(bundle1.getLocale().toString().equals("bs")) {
            statusBarLabel.setText("Napomena: Kviz mora poštovati navedeni format!");
        } else {
            statusBarLabel.setText("Reference: The quiz must follow the specified format!");
        }
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
        Stage stage1 = (Stage) textAreaField.getScene().getWindow();
        stage1.close();
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
                ResourceBundle bundle1 = ResourceBundle.getBundle("Translation");
                Stage stage1 = (Stage) textAreaField.getScene().getWindow();
                if(bundle1.getLocale().toString().equals("bs")) {
                    statusBarLabel.setText("Datoteka uspješno spremljena!");
                } else {
                    statusBarLabel.setText("Document has been saved successfully!");
                }


            } catch(Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }

    }

    public void newAction(ActionEvent actionEvent) {
        textAreaField.setText("1. Pitanje1"+"\n"+"a. Odgovor1"+"\n"+"b. Odgovor2"+"\n"+"c. Odgovor3"+"\n"+ "Tačan odgovor: Odgovor"+"\n"+"2. Pitanje2"+"\n"+"a. Odgovor1"+"\n"+"b. Odgovor2"+"\n"+"c. Odgovor3"+"\n"+ "Tačan odgovor: Odgovor"+"\n"+"3. Pitanje3"+"\n"+"a. Odgovor1"+"\n"+"b. Odgovor2"+"\n"+"c. Odgovor3"+"\n"+ "Tačan odgovor: Odgovor");
        ResourceBundle bundle1 = ResourceBundle.getBundle("Translation");
        Stage stage1 = (Stage) textAreaField.getScene().getWindow();
        if(bundle1.getLocale().toString().equals("bs")) {
            statusBarLabel.setText("Napomena: Kviz mora poštovati navedeni format!");
        } else {
            statusBarLabel.setText("Reference: The quiz must follow the specified format!");
        }
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

            String pathCopy = docNameField.getText();
            File fileCopy = new File(pathCopy);
            try {
                file.createNewFile();
                fileCopy.createNewFile();
                try (FileWriter f = new FileWriter(file);
                     BufferedWriter b = new BufferedWriter(f);
                     PrintWriter p = new PrintWriter(b);) {
                    p.println(textAreaField.getText());
                    ResourceBundle bundle1 = ResourceBundle.getBundle("Translation");
                    Stage stage1 = (Stage) textAreaField.getScene().getWindow();
                    if(bundle1.getLocale().toString().equals("bs")) {
                        statusBarLabel.setText("Kviz je objavljen!");
                    } else {
                        statusBarLabel.setText("Quiz is published!");
                    }
                    MaterialManagementDAO materialManagementDAO = MaterialManagementDAO.getInstance();
                    if(visibilityBox.getValue().equals(Visibility.PUBLIC)) {
                        Material material = new Material(materialManagementDAO.getId(), docNameField.getText()+"-QUIZ", activeProfessor.getSubject(), 1);
                        materialManagementDAO.addMaterial(material);
                    } else if(visibilityBox.getValue().equals(Visibility.PRIVATE)) {
                        Material material = new Material(materialManagementDAO.getId(), docNameField.getText()+"-QUIZ", activeProfessor.getSubject(), 2);
                        materialManagementDAO.addMaterial(material);
                    } else {
                        Material material = new Material(materialManagementDAO.getId(),docNameField.getText()+"-QUIZ", activeProfessor.getSubject(), 3);
                        materialManagementDAO.addMaterial(material);
                    }
                    Stage stage = new Stage();
                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/progress.fxml" ), bundle);
                    Parent root = loader.load();
                    stage.setScene(new Scene(root, 200, 100));
                    stage.setResizable(false);
                    stage.show();



                    Stage closeStage = (Stage) docNameField.getScene().getWindow();
                    closeStage.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
                try (FileWriter f = new FileWriter(fileCopy);
                     BufferedWriter b = new BufferedWriter(f);
                     PrintWriter p = new PrintWriter(b);) {
                    p.println(textAreaField.getText());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void aboutAction(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/aboutTXT.fxml" ), bundle);
        Parent root = loader.load();
        if(bundle.getLocale().toString().equals("bs")) {
            myStage.setTitle("O nama");
        } else {
            myStage.setTitle("About");
        }
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }
}
