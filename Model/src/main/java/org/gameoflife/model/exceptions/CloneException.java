package org.gameoflife.model.exceptions;

public class CloneException extends CloneNotSupportedException {

    public CloneException() {
        super();
    }
    public CloneException(String message) {
        super(message);
    }
}
