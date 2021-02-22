package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;

public class Student extends User {
    private SimpleStringProperty index;

    public Student() {
    }
    public Student(Integer id, String name, String surname, String email, String username, String password, String picture, String index) {
        super(id, name, surname, email, username, password, picture);
        this.index = new SimpleStringProperty(index);
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
