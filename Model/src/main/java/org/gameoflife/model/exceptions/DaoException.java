package org.gameoflife.model.exceptions;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DaoException extends ApplicationException {

    private static final ResourceBundle messages;
    //Message keys
    public static final String NULL_NAME = "null.name";
    public static final String OPEN_ERROR = "open.error";
    public static final String READ_ERROR = "read.error";
    public static final String WRITE_ERROR = "write.error";

    static {
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        messages = ResourceBundle.getBundle("lang", locale);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        String message;
        try {
            //Exception message is a key
            message = messages.getString(getMessage());
        } catch (MissingResourceException mre) {
            message = "No resource for " + getMessage() + "key";
        }
        return message;
    }
}
