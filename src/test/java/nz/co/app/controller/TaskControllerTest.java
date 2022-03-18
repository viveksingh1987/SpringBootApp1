package nz.co.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.app.db.Tasks;
import nz.co.app.service.TasksService;
import nz.co.app.utils.TasksUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(SpringExtension.class)
@ComponentScan
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @MockBean
    private TasksService tasksService;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void findallAllTasksTest() throws Exception {

        Mockito.when(tasksService.findAllTasks()).thenReturn(tasksList);
        mockMvc.perform(get("/task/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(tasksList.size())));

        verify(tasksService, times(1)).findAllTasks();
    }

    @Test
    public void findTaskByIdTest() throws Exception {
        Mockito.when(tasksService.findTaskDetails(newTask.getId())).thenReturn(newTask);
        mockMvc.perform(get("/task/{id}", newTask.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newTask.getTitle())))
                .andExpect(jsonPath("$.description", is(newTask.getDescription())));

        verify(tasksService, times(1)).findTaskDetails(newTask.getId());
    }

    @Test
    public void deleteTaskTest() throws Exception {

        ResponseEntity<Object> responseEntity = new ResponseEntity<>("Successfully Deleted the Task.", HttpStatus.OK);
        when(tasksService.deleteTask(newTask.getId())).thenReturn(responseEntity);

        mockMvc.perform(delete("/task/{id}", newTask.getId() ))
                .andExpect(status().isOk());
        verify(tasksService, times(1)).deleteTask(newTask.getId());
    }

    @Test
    public void finallAllOverdueTest() throws Exception {
        Mockito.when(tasksService.findAllOverDueTasks()).thenReturn(tasksList);
        mockMvc.perform(get("/task/alloverdue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(tasksList.size())));

        verify(tasksService, times(1)).findAllOverDueTasks();
    }

    @Test
    public void addTaskBadRequestTest() throws Exception {
        mockMvc.perform(post("/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Tasks())))
                .andExpect(status().isBadRequest());
    }

}
