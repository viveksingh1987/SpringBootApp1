package nz.co.app.service;

import nz.co.app.db.TaskRepository;
import nz.co.app.db.Tasks;
import nz.co.app.exception.NoDataFoundException;
import nz.co.app.exception.TaskNotFoundException;
import nz.co.app.exception.TaskRespositoryException;
import nz.co.app.utils.TasksUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TasksService implements TasksServiceInterface {

    private final TaskRepository taskRepository;

    @Autowired
    private TasksUtils tasksUtils;

    @Autowired
    public TasksService(TaskRepository taskRepository) { this.taskRepository = taskRepository;}

    /**
     * Service method to get all the available Tasks
     * @return
     */
    @Override
    public List<Tasks> findAllTasks() {
        List<Tasks> tasks = taskRepository.findAll();
        if(tasks.isEmpty()) {
            throw new NoDataFoundException();
        }
        return  tasks;
    }

    /**
     * Service method to find all OverDue Tasks
     * @return
     */
    @Override
    public List<Tasks> findAllOverDueTasks() {
        List<Tasks> overDuetasks = tasksUtils
                .filterOverDueTasks(taskRepository.findAll());
        if(overDuetasks.isEmpty()){
            throw  new NoDataFoundException();
        }
        return  overDuetasks;
    }

    /**
     * Service Method to find Task Details for given TaskId
     * @param taskId
     * @return
     */
    @Override
    public Tasks findTaskDetails(int taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(()-> new TaskNotFoundException(taskId));
    }

    /**
     * Service Method to add new Task
     * @param task
     * @return
     */
    @Override
    public Tasks addNewTask(Tasks task) {
        try {
            return taskRepository.save(task);
        } catch( DataAccessException ex) {
            throw new TaskRespositoryException("Failed to add New Task", new Exception("ADD"));
        }
    }

    /**
     * Service Method to Update the given Task
     * @param task
     * @return Tasks
     */
    @Override
    public Tasks updateTask(Tasks task) {
        try{
            return taskRepository.save(task);
        } catch (DataAccessException ex) {
            throw new TaskRespositoryException("Failed to Update Task", new Exception("UPDATE"));
        }
    }

    /**
     * Service Method to delete the Task for given taskId
     * @param taskId
     * @return
     */
    @Override
    public ResponseEntity<Object> deleteTask(int taskId) {
        try {
            taskRepository.deleteById(taskId);
            return new ResponseEntity<>("Successfully Deleted the Task.", HttpStatus.OK);
        } catch (DataAccessException ex) {
            throw new TaskRespositoryException("Failed to Delete Task", new Exception("DELETE"));
        }
    }
}
