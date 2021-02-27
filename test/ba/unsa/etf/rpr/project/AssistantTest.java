package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {
    Assistant assistant;
    @BeforeEach
    void initialization() {
        assistant = new Assistant(1,"Elma","Šeremet","eseremet1@etf.unsa.ba","eseremet1","elma","","18318");
        Assistant assistant1 = new Assistant();
    }
    @Test
    void getIndex() {
        assertEquals("18318", assistant.getIndex());
    }

    @Test
    void setIndex() {
        assistant.setIndex("10000");
        assertEquals("10000", assistant.getIndex());
    }

    @Test
    void testToString() {
        assertEquals("Elma Šeremet", assistant.toString());
    }

}