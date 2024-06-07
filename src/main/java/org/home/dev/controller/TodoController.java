package org.home.dev.controller;

import org.home.dev.dto.todo.TodoDto;
import org.home.dev.dto.todo.TodoGroupDto;
import org.home.dev.service.todo.TodoServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    private final TodoServiceImpl todoServiceImpl;

    public TodoController(TodoServiceImpl todoServiceImpl) {
        this.todoServiceImpl = todoServiceImpl;
    }

    @PostMapping("/api/private/todos/{todoGroupId}/add")
    public void addTodo(@PathVariable(name = "todoGroupId")
                                    Long todoGroupId, @RequestBody TodoDto todoDto) {
        todoServiceImpl.addTodo(todoGroupId, todoDto);
    }

    @PutMapping("/api/private/todos/{id}/edit")
    public void editTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        todoServiceImpl.editTodo(id, todoDto);
    }

    @DeleteMapping("/api/private/todos/{id}/delete")
    public void deleteTodo(@PathVariable Long id) { todoServiceImpl.deleteTodo(id); }

    @PutMapping("/api/private/todos/{id}/done/{done}")
    public void editTodo(@PathVariable Long id, @PathVariable boolean done) {
        todoServiceImpl.done(id, done);
    }

    @GetMapping("/api/private/todos/{todoGroupId}")
    public List<TodoDto> getTodos(@PathVariable(required = false) Long todoGroupId,
                                  @RequestParam(name = "hideCompleted", required = false) Optional<Boolean> hideCompletedOpt) {
        return todoServiceImpl.getTodos(todoGroupId, hideCompletedOpt);
    }

    @PostMapping("/api/private/todoGroups/add")
    public TodoGroupDto addTodoGroup(@RequestBody TodoGroupDto todoGroupDto) {
        return todoServiceImpl.addTodoGroup(todoGroupDto);
    }

    @GetMapping("/api/private/todoGroups")
    public List<TodoGroupDto> getTodoGroups() {
        return todoServiceImpl.getTodoGroups();
    }

    @PutMapping("/api/private/todoGroups/{id}/edit")
    public TodoGroupDto editTodoGroup(@PathVariable Long id, @RequestBody TodoGroupDto todoGroupDto) {
        return todoServiceImpl.editTodoGroup(id, todoGroupDto);
    }

}
