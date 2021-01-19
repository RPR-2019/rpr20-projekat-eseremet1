package ba.unsa.etf.rpr.projekat;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student extends User {
    public Student() {
    }

    public Student(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty email, SimpleStringProperty username, SimpleStringProperty password, String picture) {
        super(id, name, surname, email, username, password, picture);
    }
}
