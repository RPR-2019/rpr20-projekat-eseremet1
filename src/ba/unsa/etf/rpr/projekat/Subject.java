package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty numberOfEnrolled;

    public Subject() {
    }

    public Subject(Integer id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.numberOfEnrolled = new SimpleIntegerProperty(0);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name=new SimpleStringProperty(name);
    }

    public int getNumberOfEnrolled() {
        return numberOfEnrolled.get();
    }

    public SimpleIntegerProperty numberOfEnrolledProperty() {
        return numberOfEnrolled;
    }

    public void setNumberOfEnrolled(int numberOfEnrolled) {
        this.numberOfEnrolled = new SimpleIntegerProperty(numberOfEnrolled);
    }
}
