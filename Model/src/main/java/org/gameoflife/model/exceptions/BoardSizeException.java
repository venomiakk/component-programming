package org.gameoflife.model.exceptions;

public class BoardSizeException extends ApplicationException {
    public BoardSizeException(String message) {
        super(message);
    }

    public BoardSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
