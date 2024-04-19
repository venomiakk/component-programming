package org.gameoflife.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeBoardDaoFactoryTest {

    @Test
    public void constructorTest() {
        try{
            GameOfLifeBoardDaoFactory boardDaoFactory = new GameOfLifeBoardDaoFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getFileDaoTest() throws Exception {
        GameOfLifeBoardDaoFactory boardDaoFactory = new GameOfLifeBoardDaoFactory();
        assertTrue(boardDaoFactory.getFileDao("test") instanceof Dao<GameOfLifeBoard>);
    }
}
