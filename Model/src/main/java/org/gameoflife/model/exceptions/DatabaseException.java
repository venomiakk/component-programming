package org.gameoflife.model.exceptions;

public class DatabaseException extends DaoException {
    public static final String NULL_NAME = "null.name";

    public static final String NOCELL = "nocell.error";

    public static final String CONNECTION_ERROR = "connection.error";
    public static final String SELECT_ERROR = "select.error";
    public static final String INSERINTO_ERROR = "insertInto.error";
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
