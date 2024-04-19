package org.gameoflife.model;


import org.gameoflife.model.exceptions.ApplicationException;
import org.gameoflife.model.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(FileGameOfLifeBoardDao.class);
    private final Path filePath;

    public FileGameOfLifeBoardDao(String fileName) throws ApplicationException {
        if (fileName == null) {
            throw new DaoException(DaoException.NULL_NAME);
        }
        this.filePath = Paths.get(fileName);
    }

    @Override
    public void close() {
        //korzystajac z try-with-resources implementacja tej metody jest niepotrzebna?
    }

    @Override
    public GameOfLifeBoard read() throws DaoException {
        Object obj;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(DaoException.READ_ERROR + e);
            throw new DaoException(DaoException.READ_ERROR, e);
        }
        return (GameOfLifeBoard) obj;
    }

    @Override
    public void write(GameOfLifeBoard obj) throws DaoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            logger.error(DaoException.WRITE_ERROR + e);
            throw new DaoException(DaoException.WRITE_ERROR, e);
        }
    }
}
