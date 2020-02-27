package com.laylasahara.todoapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laylasahara.todoapp.dao.TodoRepository;
import com.laylasahara.todoapp.model.Category;
import com.laylasahara.todoapp.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoAppApplicationTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoRepository todoRepository;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> entity) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, entity);
    }

    @Test
    void getAllTodosTest() throws Exception {
        when(todoRepository.findAll()).thenReturn(makeTodos());
        MvcResult result = this.mvc.perform(get("/todos").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

        String content = result.getResponse().getContentAsString();
        Todo[] todos = mapFromJson(content, Todo[].class);
        assertTrue(todos.length > 0);

    }

    @Test
    void postTodoTest() throws Exception {
        Todo todo = makeTodos().get(1);

        String jsonString = mapToJson(todo);
        MvcResult result = mvc.perform(post("/todo").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

    }

    @Test
    void putTodoTest() throws Exception {
        Todo todo = makeTodos().get(2);

        String jsonString = mapToJson(todo);
        MvcResult result = mvc.perform(put("/todo").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

    }

    @Test
    void getTodoTest() throws Exception {
        int id = 3;
        Optional<Todo> todo = Optional.ofNullable(makeTodos().get(id - 1));

        when(todoRepository.findById(id)).thenReturn(todo);
        MvcResult result = mvc.perform(get("/todo/" + id).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

    }

    private List<Todo> makeTodos() {
        Todo todo1 = new Todo();
        todo1.setTitle("The title 1");
//        todo1.setId(1);
        todo1.setDescription("Tellus mauris a diam maecenas sed enim ut sem viverra.");
        todo1.setCategory(Category.PENDING);

        Todo todo2 = new Todo();
        todo2.setTitle("The title 2");
//        todo2.setId(2);
        todo2.setDescription("elit at imperdiet dui accumsan sit amet nulla facilisi morbi");
        todo2.setCategory(Category.PENDING);

        Todo todo3 = new Todo();
//        todo3.setId(3);
        todo3.setTitle("The title 3");
        todo3.setDescription("tempus iaculis urna id volutpat lacus laoreet non curabitur");
        todo3.setCategory(Category.INPROGRESS);

        return Arrays.asList(todo1, todo2, todo3);
    }
}