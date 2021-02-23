package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {
    Student student;
    @BeforeEach
    void initialization() {
        student = new Student(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
    }
    @Test
    void getIndex() {
        assertEquals("18318", student.getIndex());
    }

    @Test
    void setIndex() {
        student.setIndex("10000");
        assertEquals("10000", student.getIndex());
    }

    @Test
    void testToString() {
        assertEquals("Elma Šeremet", student.toString());
    }
}