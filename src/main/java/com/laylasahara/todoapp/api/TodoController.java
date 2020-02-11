package com.laylasahara.todoapp.api;

import com.laylasahara.todoapp.model.Category;
import com.laylasahara.todoapp.model.Todo;
import com.laylasahara.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(path = "todos", method = RequestMethod.GET)
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @RequestMapping(path = "todo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTodo(@RequestBody Todo todo) {
        todoService.addTodo(todo);
    }

    @RequestMapping(path = "todo", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTodo(@RequestBody Todo updateTodo) {
        todoService.updateTodo(updateTodo);
    }

    @RequestMapping(path = "todo/{id}", method = RequestMethod.GET)
    public Todo getTodo(@PathVariable("id") int id) {
        return todoService.getTodo(id);
    }

    @RequestMapping(path = "todo/{id}", method = RequestMethod.DELETE)
    public void deleteTodo(@PathVariable("id") int id) {
        todoService.delete(id);
    }

    @RequestMapping(path = "todo/category/next/{id}", method = RequestMethod.GET)
    public void nextCategory(@PathVariable("id") int id) {
        todoService.nextCategory(id);
    }

    @RequestMapping(path = "todos/filter/{category}", method = RequestMethod.GET)
    public List<Todo> filterTodos(@PathVariable("category")Category category) {
        return todoService.filter(category);
    }
}
