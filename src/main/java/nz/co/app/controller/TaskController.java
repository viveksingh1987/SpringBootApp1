package nz.co.app.controller;

import nz.co.app.db.Tasks;
import nz.co.app.service.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TasksService tasksService;

    public TaskController(TasksService tasksService) {

        this.tasksService = tasksService;
    }

    @GetMapping("/all")
    public List<Tasks> getAllTasks() {

        return tasksService.findAllTasks();
    }

    @GetMapping("/alloverdue")
    public List<Tasks> getAllOverDueTasks() {

        return tasksService.findAllOverDueTasks();
    }

    @GetMapping("/{id}")
    public Tasks getTaskDetails(@PathVariable int id) {

        return tasksService.findTaskDetails(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Tasks addTask(@RequestBody @Valid Tasks task) {

        return tasksService.addNewTask(task);
    }

    @PutMapping("/update")
    public Tasks updateTask(@RequestBody @Valid Tasks task) {

        return tasksService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable int id) {
        return tasksService.deleteTask(id);
    }

}
