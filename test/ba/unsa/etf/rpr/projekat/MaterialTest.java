package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    @Test
    void getId() {
        Subject subject = new Subject(2, "OBP");
        Material material = new Material(1,"proba",subject,1);
        assertEquals(1, material.getId());
    }

    @Test
    void setId() {
        Material material = new Material(1,"proba",null,1);
        assertEquals(1, material.getId());
        material.setId(2);
        assertEquals(2, material.getId());
    }

    @Test
    void getName() {
        Material material = new Material(1,"proba",null,1);
        assertEquals("proba", material.getName());
    }

    @Test
    void setName() {
        Material material = new Material(1,"proba",null,1);
        assertEquals("proba", material.getName());
        material.setName("proba1");
        assertEquals("proba1", material.getName());
    }

    @Test
    void getSubject() {
        Subject subject = new Subject(2, "OBP");
        Material material = new Material(1,"proba",subject,1);
        assertEquals(subject, material.getSubject());
    }

    @Test
    void setSubject() {
        Subject subject = new Subject(2, "OBP");
        Material material = new Material(1,"proba",null,1);
        assertEquals(null, material.getSubject());
        material.setSubject(subject);
        assertEquals(subject, material.getSubject());
    }

    @Test
    void getType() {
        Material material = new Material(1,"proba",null,1);
        assertEquals(Visibility.PUBLIC, material.getType());
    }

    @Test
    void setType() {
        Material material = new Material(1,"proba",null,2);
        assertEquals(Visibility.PRIVATE, material.getType());
        material.setType(Visibility.CUSTOM);
        assertEquals(Visibility.CUSTOM, material.getType());
    }
}