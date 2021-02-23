package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ProfileController {
    public Label nameField;
    public Label surnameField;
    public Label usernameField;
    public Label passwordField;
    public Label emailField;
    private Professor professor;
    private Student student;
    private SearchController search;
    private User user;
    public Button imageUser;
    public Menu languageMenu;
    public Menu helpMenu;
    public MenuItem bosnianMenu;
    public MenuItem englishMenu;
    public MenuItem aboutMenu;
    public Label nameLabel;
    public Label surnameLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label emailLabel;
    public Button changeBtn;
    public ProfileController(User user) {
        this.user = user;
        if(user instanceof Professor) {
            professor = (Professor) user;
        } else {
            student = (Student) user;
        }
    }

    @FXML
    public void initialize() {

        if(user instanceof Professor) {
            nameField.setText(professor.getName());
            surnameField.setText(professor.getSurname());
            usernameField.setText(professor.getUsername());
            passwordField.setText(professor.getPassword());
            emailField.setText(professor.getEmail());
            if(!professor.getPicture().isEmpty()) {
                ImageView choose = new ImageView(professor.getPicture());
                choose.setFitWidth(200);
                choose.setFitHeight(200);
                imageUser.setGraphic(choose);
            }
        } else {
            nameField.setText(student.getName());
            surnameField.setText(student.getSurname());
            usernameField.setText(student.getUsername());
            passwordField.setText(student.getPassword());
            emailField.setText(student.getEmail());
            if(!student.getPicture().isEmpty()) {
                ImageView choose = new ImageView(student.getPicture());
                choose.setFitWidth(200);
                choose.setFitHeight(200);
                imageUser.setGraphic(choose);
            }
        }

    }

    public void backAction(ActionEvent actionEvent) throws IOException {
        if(user instanceof Professor) {
            Stage stageClose = (Stage) imageUser.getScene().getWindow();
            stageClose.close();
            Stage stage = new Stage();
            Parent root = null;
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeProfessor.fxml"), bundle);
            HomeProfessorController professorContoller=new HomeProfessorController(professor);
            loader.setController(professorContoller);
            root=loader.load();
            stage.setTitle("Početna stranica za profesora");
            stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
            stage.setMinHeight(500); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.setResizable(true);
            stage.show();
        } else {
            Stage stageClose = (Stage) imageUser.getScene().getWindow();
            stageClose.close();
            Stage stage = new Stage();
            Parent root = null;
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeStudent.fxml"), bundle);
            HomeStudentController studentController = new HomeStudentController(student);
            loader.setController(studentController);
            root=loader.load();
            stage.setTitle("Početna stranica za studenta");
            stage.setScene(new Scene(root, 1500, 700)); //stavljamo početni ekran
            stage.setMinHeight(500); //da se ne može više smanjivati
            stage.setMinWidth(200);
            stage.setResizable(true);
            stage.show();
        }

    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) nameField.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changePassword.fxml"), bundle);
        PasswordController passwordController =new PasswordController(user);
        loader.setController(passwordController);
        root=loader.load();
        stage.setTitle("Promijenite lozinku!");
        stage.setScene(new Scene(root, 1200, 500)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

    public void imageAction(ActionEvent actionEvent) {
            try {
                Stage stage = new Stage();
                SearchController ctrl = new SearchController(user);

                Parent root = null;
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"), bundle);
                SearchController searchController =new SearchController(user);
                loader.setController(searchController);
                root = loader.load();
                stage.setTitle("Pretraga slike - Giphy");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
                search = ctrl;

                stage.setOnHidden(windowEvent -> {
                    if(user instanceof Professor) {
                        ImageView choose = new ImageView(professor.getPicture());
                        choose.setFitWidth(200);
                        choose.setFitHeight(200);
                        imageUser.setGraphic(choose);
                    } else {
                        ImageView choose = new ImageView(student.getPicture());
                        choose.setFitWidth(200);
                        choose.setFitHeight(200);
                        imageUser.setGraphic(choose);
                    }

                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stageClose = (Stage) imageUser.getScene().getWindow();
        stageClose.close();
        Stage stage = new Stage();
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/logIn.fxml" ), bundle);
        root = loader.load();
        stage.setTitle("Prijavite se!");
        stage.setScene(new Scene(root, 1200, 700)); //stavljamo početni ekran
        stage.setResizable(false);


        stage.show();
    }

    public void aboutAction(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/about.fxml" ), bundle);
        Parent root = loader.load();
        myStage.setTitle("About");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public void bosnianAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        translate();
    }

    public void englishAction(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        translate();
    }
    private void translate() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translation", Locale.getDefault());
        languageMenu.setText(resourceBundle.getString("language"));
        helpMenu.setText(resourceBundle.getString("_help"));
        aboutMenu.setText(resourceBundle.getString("_about"));
        bosnianMenu.setText(resourceBundle.getString("bosnian"));
        englishMenu.setText(resourceBundle.getString("english"));
        nameLabel.setText(resourceBundle.getString("name"));
        surnameLabel.setText(resourceBundle.getString("surname"));
        usernameLabel.setText(resourceBundle.getString("username"));
        passwordLabel.setText(resourceBundle.getString("password"));
        emailLabel.setText(resourceBundle.getString("email"));
        changeBtn.setText(resourceBundle.getString("changePassword"));
    }

    }

