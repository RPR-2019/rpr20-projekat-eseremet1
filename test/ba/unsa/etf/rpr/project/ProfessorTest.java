package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {
    private Professor professor;
    private Subject subject;

    @BeforeEach
    void initialization() {
        subject = new Subject(1, "RPR");
        professor = new Professor(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","pass1","slika");
    }

    @Test
    void getSubject() {
        assertEquals(null, professor.getSubject());
    }

    @Test
    void setSubject() {
        professor.setSubject(subject);
        assertEquals(subject, professor.getSubject());
    }

    @Test
    void testToString() {
        assertEquals("Elma Šeremet", professor.toString());
    }
}