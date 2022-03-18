package nz.co.app.service;

import nz.co.app.db.TaskRepository;
import nz.co.app.db.Tasks;
import nz.co.app.exception.NoDataFoundException;
import nz.co.app.utils.TasksUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    TasksService tasksService;

    @Mock
    TaskRepository taskRepository;

    @Autowired
    TasksUtils tasksUtils;

    List<Tasks> tasksList;

    Tasks newTask;

    @BeforeEach
    void setUp() {
        tasksUtils = new TasksUtils();
        tasksList = new ArrayList<>();
        tasksList.add(new Tasks("shopping", "perform online shop", tasksUtils.subtractDate(new Date(), 3), "pending"));
        tasksList.add(new Tasks("dinning", "tonight dinner", new Date(), "pending"));
        tasksList.add(new Tasks("work", "attain meetings", new Date(), "complete"));

        newTask = new Tasks();
        newTask.setTitle("shopping");
        newTask.setDescription("perform online shop");
        newTask.setDue_date(new Date());
        newTask.setCreation_date(new Date());
        newTask.setId(1);
        newTask.setStatus("pending");
    }

    @Test
    public void findAllTaskTest() {
        when(taskRepository.findAll()).thenReturn(tasksList);
        List<Tasks> tasks = tasksService.findAllTasks();
        assertTrue(tasks.size() > 0);
        verify(taskRepository,times(1)).findAll();
    }

    @Test
    public void findTaskByIdTest() {
        when(taskRepository.findById(newTask.getId())).thenReturn(java.util.Optional.ofNullable(newTask));
        Tasks task = tasksService.findTaskDetails(newTask.getId());
        assertEquals(task.getId(), newTask.getId());
        verify(taskRepository,times(1)).findById(newTask.getId());
    }

    @Test
    public void findAllTaskNoDataFoundExTest() {
        NoDataFoundException ex = assertThrows(NoDataFoundException.class,
                ()-> tasksService.findAllTasks());
        assertEquals("Sorry No Task Details Found.", ex.getMessage());
    }

    @Test
    public void addTaskTest() {
        when(taskRepository.save(any(Tasks.class))).thenReturn(newTask);
        Tasks addedTask = tasksService.addNewTask(newTask);
        assertEquals(addedTask.getTitle(), newTask.getTitle());
        verify(taskRepository,times(1)).save(any());
    }

    @Test
    public void deleteTaskTest() {
        doNothing().when(taskRepository).deleteById(newTask.getId());
        tasksService.deleteTask(newTask.getId());
        verify(taskRepository,times(1)).deleteById(newTask.getId());
    }
}
