package learn.data;

public class DataException extends Exception {

    public DataException(String message) {
        super(message);
    }

    public DataException(String ex, Throwable arg) {
        super(ex, arg);
    }
}
