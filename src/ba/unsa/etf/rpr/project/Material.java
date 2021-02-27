package ba.unsa.etf.rpr.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Material {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private Subject subject;
    private Visibility type;
    public Material() {
    }

    public Material(Integer id, String name, Subject subject, int type) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.subject = subject;
        if(type==1) {
            this.type = ba.unsa.etf.rpr.project.Visibility.PUBLIC;
        } else if(type==2) {
            this.type = ba.unsa.etf.rpr.project.Visibility.PRIVATE;
        } else {
            this.type = ba.unsa.etf.rpr.project.Visibility.CUSTOM;
        }
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

    public ba.unsa.etf.rpr.project.Visibility getType() {
        return type;
    }

    public void setType(ba.unsa.etf.rpr.project.Visibility type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name.get();
    }

}
