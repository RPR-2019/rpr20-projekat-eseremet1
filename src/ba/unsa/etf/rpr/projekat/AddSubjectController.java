package ba.unsa.etf.rpr.projekat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class AddSubjectController {
    public TextField nameField;
    private SubjectDAO subjectDAO;
    private Subject subject;
    public Button addBtn;
    public ChoiceBox<Professor> choiceProfessor;
    private ObservableList<Professor> professors;


    public AddSubjectController(Subject subject, ArrayList<Professor> professors) {
        subjectDAO = SubjectDAO.getInstance();
        this.subject = subject;
        this.professors= FXCollections.observableArrayList(professors);
    }

    @FXML
    public void initialize() {
        choiceProfessor.setItems(professors);
        if(subject!=null) {
            nameField.setText(subject.getName());
            addBtn.setText("Izmijeni");
        }

        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (nameField.getText().trim().isEmpty()) {
                    nameField.getStyleClass().removeAll("poljeJeIspravno");
                    nameField.getStyleClass().add("poljeNijeIspravno");
                } else {
                    nameField.getStyleClass().removeAll("poljeNijeIspravno");
                    nameField.getStyleClass().add("poljeJeIspravno");
                }
            }
        });

    }


    public void addAction(ActionEvent actionEvent) {
        boolean sveOk = true;
        if(nameField.getText().trim().isEmpty()) {
            nameField.getStyleClass().removeAll("poljeIspravno");
            nameField.getStyleClass().addAll("poljeNijeIspravno");
            sveOk=false;
        } else {
            nameField.getStyleClass().removeAll("poljeNijeIspravno");
            nameField.getStyleClass().add("poljeIspravno");
        }

        for (Subject oldSubject: subjectDAO.subjects()) {
            if(oldSubject.getName().equals(nameField.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Upozorenje");
                alert.setHeaderText(null);
                alert.setContentText("Predmet već postoji!");

                alert.showAndWait();

                sveOk=false;
                break;

            }
        }


        if (!sveOk) return;

        if (subject== null) subject = new Subject();
        subject.setName(nameField.getText());
        subject.setProfessor(choiceProfessor.getValue());

        Stage stageClose = (Stage) nameField.getScene().getWindow();
        stageClose.close();
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
