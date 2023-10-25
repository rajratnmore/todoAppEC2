package com.geekster.todoApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.JstlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    List<Todo> todoList;


    @PostMapping("todo")
    public String addTodo(@RequestBody Todo myTodo){
        todoList.add(myTodo);
        return "todo added";
    }

    @GetMapping("todos")
    public List<Todo> getAllTodos(){
        return this.todoList;
    }

    @PutMapping("todo/id/{id}/status")
    public String setTodoStatusById(@PathVariable Integer id, @RequestParam boolean status){

        for(Todo todo : todoList){
            if(todo.getTodoId().equals(id)){
                todo.setTodoStatus(status);
                return "todo : "+id+" updated to "+status;
            }
        }
        return "Invalid id";
    }

    @DeleteMapping("todo/id/{id}")
    public String deleteTodoById(@PathVariable Integer id){
        for(Todo todo : todoList){
            if(todo.getTodoId().equals(id)){
                todoList.remove(todo);
                return "todo "+id+" removed";
            }
        }
        return "Invalid id";
    }

    @PostMapping("todos")
    public String addListOfTodo(@RequestBody List<Todo> myTodo){
        todoList.addAll(myTodo);
        return myTodo.size()+" todo added";
    }

    @GetMapping("undoneTodos")
    public List<Todo> getAllUndoTodos(){
        return todoList.stream().filter(todo -> !todo.isTodoStatus()).collect(Collectors.toList());
    }

    @DeleteMapping("deleteTodos")
    public List<Todo> deleteTodoByIds(@RequestBody List<Integer> ids){
        Set<Integer> set = new HashSet<>(ids);
        todoList.removeIf(todo -> set.contains(todo.getTodoId()));
        return todoList;
    }

}
