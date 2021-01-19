package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Professor extends User {

    public Professor() {
    }

    public Professor(Integer id, String name, String surname, String email, String username, String password, String picture) {
        super(id, name, surname, email, username, password, picture);
    }
}
