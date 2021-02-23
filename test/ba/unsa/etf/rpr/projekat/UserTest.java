package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    Student student;
    Professor professor;
    @BeforeEach
    void initialization() {
        student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        professor = new Professor(2,"Eldar","Šeremet","eseremet2@etf.unsa.ba","eseremet2","elma1","slika");

    }
    @Test
    void getId() {
        assertEquals(1, student.getId());
        assertEquals(2, professor.getId());
    }
    @Test
    void setId() {
        student.setId(3);
        professor.setId(4);
        assertEquals(3, student.getId());
        assertEquals(4, professor.getId());
    }

    @Test
    void getName() {
        assertEquals("Elma", student.getName());
        assertEquals("Eldar", professor.getName());
    }

    @Test
    void setName() {
        student.setName("Eldar");
        professor.setName("Elma");
        assertEquals("Eldar", student.getName());
        assertEquals("Elma", professor.getName());
    }

    @Test
    void getSurname() {
        assertEquals("Šeremet", student.getSurname());
        assertEquals("Šeremet", professor.getSurname());
    }


    @Test
    void setSurname() {
        student.setSurname("Pilav");
        professor.setSurname("Smlatić");
        assertEquals("Pilav", student.getSurname());
        assertEquals("Smlatić", professor.getSurname());
    }

    @Test
    void getEmail() {
        assertEquals("eseremet1@etf.unsa.ba", student.getEmail());
        assertEquals("eseremet2@etf.unsa.ba", professor.getEmail());
    }


    @Test
    void setEmail() {
        student.setEmail("eseremet2@etf.unsa.ba");
        professor.setEmail("ealkic2@etf.unsa.ba");
        assertEquals("eseremet2@etf.unsa.ba", student.getEmail());
        assertEquals("ealkic2@etf.unsa.ba", professor.getEmail());
    }

    @Test
    void getUsername() {
        assertEquals("eseremet1", student.getUsername());
        assertEquals("eseremet2", professor.getUsername());
    }


    @Test
    void setUsername() {
        student.setUsername("eseremet2");
        professor.setUsername("ealkic2");
        assertEquals("eseremet2", student.getUsername());
        assertEquals("ealkic2", professor.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("elma", student.getPassword());
        assertEquals("elma1", professor.getPassword());
    }



    @Test
    void setPassword() {
        student.setPassword("pass1");
        professor.setPassword("pass2");
        assertEquals("pass1", student.getPassword());
        assertEquals("pass2", professor.getPassword());
    }

    @Test
    void getPicture() {
        assertEquals("", student.getPicture());
        assertEquals("slika", professor.getPicture());
    }

    @Test
    void setPicture() {
        student.setPicture("slika1");
        professor.setPicture("slika2");
        assertEquals("slika1", student.getPicture());
        assertEquals("slika2", professor.getPicture());
    }
}