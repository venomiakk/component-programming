package org.gameoflife.model;

import org.gameoflife.model.exceptions.DaoException;
import org.gameoflife.model.exceptions.DatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcGOLBoardDaoTest {

    @Test
    void read() throws DaoException {
        PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
        GameOfLifeBoard testowa = new GameOfLifeBoard(4,4,pgols,4);
        JdbcGOLBoardDao dao = new JdbcGOLBoardDao("test1");
        dao.write(testowa);
        GameOfLifeBoard testowa1 = dao.read();
        assertEquals(testowa1, testowa);


    }

    @Test
    void write() throws DaoException {
        PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
        GameOfLifeBoard testowa = new GameOfLifeBoard(4,4,pgols,4);
        JdbcGOLBoardDao dao = new JdbcGOLBoardDao("test2");
        dao.write(testowa);
        GameOfLifeBoard testowa1 = dao.read();
        assertEquals(testowa1, testowa);
    }

    @Test
    void close()
    {
        JdbcGOLBoardDao dao = new JdbcGOLBoardDao("test1");
        try
        {
            dao.close();
        } catch (Exception e) {
            throw new RuntimeException("");
        }
    }

    @Test
    void connectExceptionTest()
    {

    }
    @Test
    void selectExceptionTest()
    {
        assertThrows(DatabaseException.class, () -> {
            JdbcGOLBoardDao dao = new JdbcGOLBoardDao("adfj;;h");
            dao.read();
        });
    }
    @Test
    void insertExceptionTest() throws DaoException {
        assertThrows(DatabaseException.class, () -> {
            JdbcGOLBoardDao dao = new JdbcGOLBoardDao("test1");
            PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
            GameOfLifeBoard testowa = new GameOfLifeBoard(4,4,pgols,4);
            dao.write(testowa);
        });
    }

}