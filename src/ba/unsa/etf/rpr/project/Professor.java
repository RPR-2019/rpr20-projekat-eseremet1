package ba.unsa.etf.rpr.project;


public class Professor extends User {

    Subject subject;
    public Professor() {
    }

    public Professor(Integer id, String name, String surname, String email, String username, String password, String picture) {
        super(id, name, surname, email, username, password, picture);
        this.subject=subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
