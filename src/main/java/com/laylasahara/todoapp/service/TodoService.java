package com.laylasahara.todoapp.service;

import com.laylasahara.todoapp.dao.TodoRepository;
import com.laylasahara.todoapp.model.Category;
import com.laylasahara.todoapp.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private TodoRepository todoRepo;

    @Autowired
    public TodoService(TodoRepository todoRepo) {
        this.todoRepo = todoRepo;
    }

    public List<Todo> getAllTodos() {
        return (List<Todo>) todoRepo.findAll();
    }

    public void addTodo(Todo todo) {
        todo.setCreatedAt(getDate());
        todo.setCategory(Category.PENDING);
        todoRepo.save(todo);
    }

    public void updateTodo(Todo updatedTodo) {
        updatedTodo.setUpdatedAt(getDate());
        todoRepo.save(updatedTodo);
    }

    public Todo getTodo(int id) {
        Optional<Todo> todo = todoRepo.findById(id);
        return todo.orElse(null);
    }

    public void delete(int id) {
        todoRepo.deleteById(id);
    }

    public List<Todo> filter(Category category) {
        return todoRepo.findByCategory(category);
    }

    public void nextCategory(int id) {
        Todo todo = getTodo(id);
        Category category = todo.getCategory();
        switch(category) {
            case PENDING:
                todo.setCategory(Category.INPROGRESS);
                break;
            case INPROGRESS:
                todo.setCategory(Category.DONE);
                todo.setCompletedAt(getDate());
                todo.setStatus(true);
                break;
            default:
                todo.setCategory(Category.PENDING);
                todo.setCompletedAt(null);
                todo.setStatus(false);
        }
        todoRepo.save(todo);
    }

    private String getDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a | dd-MMM"));
    }
}
