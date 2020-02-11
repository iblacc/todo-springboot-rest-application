package com.laylasahara.todoapp.dao;

import com.laylasahara.todoapp.model.Category;
import com.laylasahara.todoapp.model.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
    List<Todo> findByCategory(Category category);
}
