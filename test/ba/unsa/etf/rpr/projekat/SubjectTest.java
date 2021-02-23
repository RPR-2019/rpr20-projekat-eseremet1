package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {
    Subject subject;
    @BeforeEach
    void initialization() {
        subject = new Subject(1, "RPR");
    }
    @Test
    void getId() {
        assertEquals(1, subject.getId());
    }

    @Test
    void setId() {
        subject.setId(3);
        assertEquals(3, subject.getId());
    }

    @Test
    void getName() {
        assertEquals("RPR", subject.getName());
    }

    @Test
    void setName() {
        subject.setName("AFJ");
        assertEquals("AFJ", subject.getName());
    }

    @Test
    void testToString() {
        Subject subject = new Subject(2, "Inženjerska matematika");
        assertEquals("Inženjerska matematika", subject.toString());
    }
}