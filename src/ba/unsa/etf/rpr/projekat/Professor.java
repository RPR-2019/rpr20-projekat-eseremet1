package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Professor extends User {

    public Professor() {
    }

    public Professor(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty email, SimpleStringProperty username, SimpleStringProperty password, String picture) {
        super(id, name, surname, email, username, password, picture);
    }
}
