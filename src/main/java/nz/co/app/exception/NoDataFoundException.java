package nz.co.app.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException () {
        super("Sorry No Task Details Found.");
    }
}
