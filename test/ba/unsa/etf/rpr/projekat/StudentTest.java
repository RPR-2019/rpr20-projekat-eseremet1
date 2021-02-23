package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {

    @Test
    void getIndex() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        assertEquals("18318", student.getIndex());
    }

    @Test
    void setIndex() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        student.setIndex("10000");
        assertEquals("10000", student.getIndex());
    }

    @Test
    void testToString() {
        Student student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","pass1","slika","18318");
        assertEquals("Elma Šeremet", student.toString());
    }
}