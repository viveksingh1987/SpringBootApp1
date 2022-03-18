package nz.co.app.service;

import nz.co.app.db.Tasks;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TasksServiceInterface {

    List<Tasks> findAllTasks();

    List<Tasks> findAllOverDueTasks();

    Tasks findTaskDetails(int id);

    Tasks addNewTask(Tasks task) throws Exception;

    Tasks updateTask(Tasks task) throws Exception;

    ResponseEntity<Object> deleteTask(int id) throws Exception;
}
