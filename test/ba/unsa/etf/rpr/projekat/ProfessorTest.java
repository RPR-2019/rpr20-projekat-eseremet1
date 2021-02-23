package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    @Test
    void getSubject() {
        Professor professor = new Professor(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","pass1","slika");
        assertEquals(null, professor.getSubject());

    }

    @Test
    void setSubject() {
        Subject subject = new Subject(1, "RPR");
        Professor professor = new Professor(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","pass1","slika");
        assertEquals(null, professor.getSubject());
        professor.setSubject(subject);
        assertEquals(subject, professor.getSubject());
    }

    @Test
    void testToString() {
        Professor professor = new Professor(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","pass1","slika");
        assertEquals("Elma Šeremet", professor.toString());
    }
}