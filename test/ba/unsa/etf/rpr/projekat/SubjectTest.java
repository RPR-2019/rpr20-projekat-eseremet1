package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    @Test
    void getId() {
        Subject subject = new Subject(1, "RPR");
        assertEquals(1, subject.getId());
    }

    @Test
    void setId() {
        Subject subject = new Subject(2, "OBP");
        assertEquals(2, subject.getId());
        subject.setId(3);
        assertEquals(3, subject.getId());
    }

    @Test
    void getName() {
        Subject subject = new Subject(1, "RPR");
        assertEquals("RPR", subject.getName());
    }

    @Test
    void setName() {
        Subject subject = new Subject(2, "OBP");
        assertEquals("OBP", subject.getName());
        subject.setName("AFJ");
        assertEquals("AFJ", subject.getName());
    }

    @Test
    void testToString() {
        Subject subject = new Subject(2, "Inženjerska matematika");
        assertEquals("Inženjerska matematika", subject.toString());
    }
}