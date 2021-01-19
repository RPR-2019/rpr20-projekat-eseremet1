package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty numberOfEnrolled;

    public Subject() {
    }

    public Subject(SimpleIntegerProperty id, SimpleStringProperty name, SimpleIntegerProperty numberOfEnrolled) {
        this.id = id;
        this.name = name;
        this.numberOfEnrolled = numberOfEnrolled;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getNumberOfEnrolled() {
        return numberOfEnrolled.get();
    }

    public SimpleIntegerProperty numberOfEnrolledProperty() {
        return numberOfEnrolled;
    }

    public void setNumberOfEnrolled(int numberOfEnrolled) {
        this.numberOfEnrolled.set(numberOfEnrolled);
    }
}
