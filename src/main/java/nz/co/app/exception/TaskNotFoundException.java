package nz.co.app.exception;

public class TaskNotFoundException  extends  RuntimeException{
    public TaskNotFoundException(int id){
        super(String.format("Task with Id %d not found.", id));
    }
}
