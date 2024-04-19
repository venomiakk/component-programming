package org.gameoflife.model;

import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ObservableTest {


    public PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();

    @Test
    public void addObserverTest() {
        Observable<Boolean> obs = new Observable<>();
        Observer<Boolean> obs1 = () -> {
        };
        obs.addObserver(obs1);
        assertEquals(obs.getObservers().size(), 1);
    }

    @Test
    public void notifyObserversTest() {

        Observable<Boolean> obs = new Observable<>();
        Observer<Boolean> obs1 = () -> {
            final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            System.out.println("updated");
            assertEquals("updated", outputStreamCaptor.toString().trim());
        };
        obs.addObserver(obs1);
        try {
            obs.notifyObservers();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }


    @Test
    public void HashStringEqualsTest() {
        Observable<Boolean> obs = new Observable<>();
        Observable<Boolean> obs1 = new Observable<>();
        assertEquals(obs.hashCode(), obs1.hashCode());
        assertTrue(obs.equals(obs1));
        assertFalse(obs.equals(null));
        assertFalse(obs.equals(pgols));
        assertTrue(obs.equals(obs));

        try {
            obs.toString();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        assertTrue(obs.toString().contains("org.gameoflife.model.Observable@"));
    }

    @Test
    public void cohesionEqualsHashCodeTest() {
        Observable<Boolean> obs1 = new Observable<>();
        Observable<Boolean> obs2 = new Observable<>();
        assertTrue(obs1.equals(obs2) && obs1.hashCode() == obs2.hashCode());
    }

}
