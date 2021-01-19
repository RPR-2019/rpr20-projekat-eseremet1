package ba.unsa.etf.rpr.projekat;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student extends User {
    private SimpleStringProperty index;
    public Student() {
    }

    public Student(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty email, SimpleStringProperty username, SimpleStringProperty password, String picture, SimpleStringProperty index) {
        super(id, name, surname, email, username, password, picture);
        this.index = index;
    }

    public String getIndex() {
        return index.get();
    }

    public SimpleStringProperty indexProperty() {
        return index;
    }

    public void setIndex(String index) {
        this.index.set(index);
    }
}
