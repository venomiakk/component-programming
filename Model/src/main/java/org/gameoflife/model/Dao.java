package org.gameoflife.model;

import org.gameoflife.model.exceptions.DaoException;

public interface Dao<T> extends AutoCloseable {

    T read() throws DaoException;

    void write(T obj) throws DaoException;
}
