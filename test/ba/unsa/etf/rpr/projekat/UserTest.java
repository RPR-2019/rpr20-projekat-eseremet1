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
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("Elma", student.getName());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","");
        assertEquals("Eldar", professor.getName());
        student.setName("Eldar");
        professor.setName("Elma");
        assertEquals("Eldar", student.getName());
        assertEquals("Elma", professor.getName());
    }

    @Test
    void getSurname() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("Šeremet", student.getSurname());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","");
        assertEquals("Šeremet", professor.getSurname());
    }


    @Test
    void setSurname() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("Šeremet", student.getSurname());
        Professor professor = new Professor(2,"Eldar","Alkić","ealkic1@etf.unsa.ba","ealkic1","elma","");
        assertEquals("Alkić", professor.getSurname());
        student.setSurname("Pilav");
        professor.setSurname("Smlatić");
        assertEquals("Pilav", student.getSurname());
        assertEquals("Smlatić", professor.getSurname());
    }

    @Test
    void getEmail() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("eseremet1@etf.unsa.ba", student.getEmail());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet2@etf.unsa.ba","eseremet2","elma","");
        assertEquals("eseremet2@etf.unsa.ba", professor.getSurname());
    }


    @Test
    void setEmail() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("eseremet1@etf.unsa.ba", student.getEmail());
        Professor professor = new Professor(2,"Eldar","Alkić","ealkic1@etf.unsa.ba","ealkic1","elma","");
        assertEquals("ealkic1@etf.unsa.ba", professor.getEmail());
        student.setEmail("eseremet2@etf.unsa.ba");
        professor.setEmail("ealkic2@etf.unsa.ba");
        assertEquals("eseremet2@etf.unsa.ba", student.getEmail());
        assertEquals("ealki21@etf.unsa.ba", professor.getEmail());
    }

    @Test
    void getUsername() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("eseremet1", student.getUsername());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet2@etf.unsa.ba","eseremet2","elma","");
        assertEquals("eseremet2", professor.getUsername());
    }


    @Test
    void setUsername() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("eseremet1", student.getUsername());
        Professor professor = new Professor(2,"Eldar","Alkić","ealkic1@etf.unsa.ba","ealkic1","elma","");
        assertEquals("ealkic1", professor.getUsername());
        student.setUsername("eseremet2");
        professor.setUsername("ealkic2");
        assertEquals("eseremet2", student.getUsername());
        assertEquals("ealki21", professor.getUsername());
    }

    @Test
    void getPassword() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("elma", student.getPassword());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet2@etf.unsa.ba","eseremet2","elma1","");
        assertEquals("elma1", professor.getPassword());
    }



    @Test
    void setPassword() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("elma", student.getPassword());
        Professor professor = new Professor(2,"Eldar","Alkić","ealkic1@etf.unsa.ba","ealkic1","eldar","");
        assertEquals("eldar", professor.getPassword());
        student.setPassword("pass1");
        professor.setPassword("pass2");
        assertEquals("pass1", student.getPassword());
        assertEquals("pass2", professor.getPassword());
    }

    @Test
    void getPicture() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("", student.getPicture());
        Professor professor = new Professor(2,"Eldar","Šeremet","eseremet2@etf.unsa.ba","eseremet2","elma1","slika");
        assertEquals("slika", professor.getPicture());
    }

    @Test
    void setPicture() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("", student.getPicture());
        Professor professor = new Professor(2,"Eldar","Alkić","ealkic1@etf.unsa.ba","ealkic1","eldar","");
        assertEquals("", professor.getPicture());
        student.setPicture("slika1");
        professor.setPicture("slika2");
        assertEquals("slika1", student.getPicture());
        assertEquals("slika2", professor.getPicture());
    }
}