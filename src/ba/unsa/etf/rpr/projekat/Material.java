package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Material {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private Subject subject;
    public enum Visibility { PUBLIC("1"), PRIVATE("2"), CUSTOM("3");
        private final String type;
        Visibility(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
    public Material() {
    }

    public Material(Integer id, String name, Subject subject) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.subject = subject;
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


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return name.get();
    }
}
