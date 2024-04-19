package org.gameoflife.model;

import org.gameoflife.model.exceptions.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class FileGameOfLifeBoardDaoTest {

    String fileName = "test";
    PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
    GameOfLifeBoard golb = new GameOfLifeBoard(2, 2, pgols);

    @Test
    public void constructorTest() {
        try (FileGameOfLifeBoardDao test = new FileGameOfLifeBoardDao(fileName)){
            Field field = test.getClass().getDeclaredField("filePath");
            field.setAccessible(true);
            assertEquals(fileName, field.get(test).toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void constructorExceptionTest() {
        assertThrows(Exception.class, () -> {
            FileGameOfLifeBoardDao test = new FileGameOfLifeBoardDao(null);
            //nie potrzeba try-with-resources bo żaden zasób nie jest tworzony?
        });
    }

    @Test
    public void writeTest() {
        try (FileGameOfLifeBoardDao test = new FileGameOfLifeBoardDao(fileName)) {
            test.write(golb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void writeExceptionTest() {
        assertThrows(DaoException.class, () -> {
            GameOfLifeBoardDaoFactory factory = new GameOfLifeBoardDaoFactory();
            Dao<GameOfLifeBoard> test = factory.getFileDao("src");
            //czy to jest niebezpieczne?
            test.write(golb);
        });
    }

    @Test
    public void readTest() {
        try (FileGameOfLifeBoardDao test = new FileGameOfLifeBoardDao(fileName)) {
            test.write(golb);
            GameOfLifeBoard board = test.read();
            assertEquals(golb, board);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readExceptionTest() {
        assertThrows(DaoException.class, () -> {
            GameOfLifeBoardDaoFactory factory = new GameOfLifeBoardDaoFactory();
            Dao<GameOfLifeBoard> test = factory.getFileDao(fileName);
            test.read();
        });
    }

    @AfterEach
    public void deleteFile() {
        new File(fileName).delete();
    }
}
