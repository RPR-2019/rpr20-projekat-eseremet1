package ba.unsa.etf.rpr.project;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void testToString() {
        assertEquals("proba", material.toString());
    }

    @Test
    void testTypes() {
        Material material1 = new Material();
        material1.setType(Visibility.PUBLIC);
        Material material2 = new Material(2,"proba1", subject,2);
        Material material3 = new Material(2,"proba1", subject,3);

        List<Visibility> visibilities = new ArrayList<>();
        visibilities.add(material1.getType());
        visibilities.add(material2.getType());
        visibilities.add(material3.getType());

        List<Visibility> visibilities1 = new ArrayList<>();
        visibilities1.addAll(Arrays.asList(Visibility.values()));
        assertEquals(visibilities1, visibilities);

    }
}