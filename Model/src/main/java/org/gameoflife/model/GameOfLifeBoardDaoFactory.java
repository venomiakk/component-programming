package org.gameoflife.model;

import org.gameoflife.model.exceptions.ApplicationException;

public class GameOfLifeBoardDaoFactory {

    public Dao<GameOfLifeBoard> getFileDao(String fileName) throws ApplicationException {
        return new FileGameOfLifeBoardDao(fileName);
    }

    public Dao<GameOfLifeBoard> getDBDao(String fileName) {
        return new JdbcGOLBoardDao(fileName);
    }
}
