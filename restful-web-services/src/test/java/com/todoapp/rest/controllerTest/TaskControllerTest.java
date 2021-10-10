package com.todoapp.rest.controllerTest;

import com.todoapp.rest.controller.TaskController;
import com.todoapp.rest.model.Task;
import com.todoapp.rest.service.TodoHardcodedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    TodoHardcodedService todoService;

    @Test
    public void returnProductsWhenGetAllTodosCalled()
            throws Exception {

        Task task = new Task(1, "subhadip", "Learn GO", new Date(), false, "Need to learn GO language");
        List<Task> tasks = Arrays.asList(task);

        given(todoService.findAll()).willReturn(tasks);

        mvc.perform(get("/api/v1//users/Subhadip/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void returnNoContentWhenGetTodoCalledWithInvalidID()
            throws Exception {

        Task task = new Task(1, "subhadip", "Learn GO", new Date(), false, "Need to learn GO language");
        List<Task> tasks = Arrays.asList(task);

        given(todoService.findAll()).willReturn(tasks);

        mvc.perform(get("/api/v1//users/Subhadip/todos/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
