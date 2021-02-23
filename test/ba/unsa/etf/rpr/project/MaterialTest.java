package ba.unsa.etf.rpr.project;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {
    private Material material;
    private Subject subject;

    @BeforeEach
    void initialization() {
        subject = new Subject(2, "OBP");
        material = new Material(1,"proba", subject,1);
    }
    @Test
    void getId() {
        assertEquals(1, material.getId());
    }

    @Test
    void setId() {
        material.setId(2);
        assertEquals(2, material.getId());
    }

    @Test
    void getName() {
        assertEquals("proba", material.getName());
    }

    @Test
    void setName() {
        material.setName("proba1");
        assertEquals("proba1", material.getName());
    }

    @Test
    void getSubject() {
        assertEquals(subject, material.getSubject());
    }

    @Test
    void setSubject() {
        material.setSubject(null);
        assertEquals(null, material.getSubject());
    }

    @Test
    void getType() {
        assertEquals(Visibility.PUBLIC, material.getType());
    }

    @Test
    void setType() {
        material.setType(Visibility.CUSTOM);
        assertEquals(Visibility.CUSTOM, material.getType());
    }
}