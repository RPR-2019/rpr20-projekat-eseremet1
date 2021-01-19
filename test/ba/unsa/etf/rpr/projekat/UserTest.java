package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals(1, student.getId());
        Professor professor = new Professor(2,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","");
        assertEquals(2, professor.getId());
    }
    @Test
    void setId() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals(1, student.getId());
        Professor professor = new Professor(2,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","");
        assertEquals(2, professor.getId());
        student.setId(3);
        professor.setId(4);
        assertEquals(3, student.getId());
        assertEquals(4, professor.getId());
    }

    @Test
    void getName() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("Elma", student.getName());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","");
        assertEquals("Eldar", professor.getName());
    }

    @Test
    void setName() {
    }

    @Test
    void getSurname() {
    }

    @Test
    void surnameProperty() {
    }

    @Test
    void setSurname() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void emailProperty() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void getUsername() {
    }

    @Test
    void usernameProperty() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void passwordProperty() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void getPicture() {
    }

    @Test
    void setPicture() {
    }
}