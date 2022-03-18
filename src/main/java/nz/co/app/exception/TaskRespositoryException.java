package nz.co.app.exception;

public class TaskRespositoryException extends RuntimeException {

    public TaskRespositoryException(String message, Exception ex) {
        super(message);
    }
}
