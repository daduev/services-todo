package org.home.dev.service.todo;

import org.home.dev.db.entity.Todo;
import org.home.dev.db.entity.TodoGroup;
import org.home.dev.db.repository.TodoGroupRepo;
import org.home.dev.db.repository.TodoRepo;
import org.home.dev.dto.todo.TodoDto;
import org.home.dev.dto.todo.TodoGroupDto;
import org.home.dev.service.user.UsersServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl {

    private final TodoRepo todoRepo;
    private final TodoGroupRepo todoGroupRepo;
    private final UsersServiceImpl usersServiceImpl;

    public TodoServiceImpl(TodoRepo todoRepo, TodoGroupRepo todoGroupRepo, UsersServiceImpl usersServiceImpl) {
        this.todoRepo = todoRepo;
        this.todoGroupRepo = todoGroupRepo;
        this.usersServiceImpl = usersServiceImpl;
    }

    public void addTodo(Long todoGroupId, TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setText(todoDto.getText());
        todo.setTodoGroup(todoGroupRepo.findOneById(todoGroupId));
        todoRepo.save(todo);
    }

    public void editTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepo.findById(id).orElseThrow();
        todo.setText(todoDto.getText());
        todoRepo.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepo.deleteById(id);
    }

    public void done(Long id, boolean done) {
        Todo todo = todoRepo.findById(id).orElseThrow();
        todo.setDone(done);
        todoRepo.save(todo);
    }

    public List<TodoDto> getTodos(Long todoGroupId, Optional<Boolean> hideCompletedOpt) {
        Long userId = usersServiceImpl.getCurrentUser().getId();
        return todoRepo.findAllByUserIdAndTodoGroupId(userId, todoGroupId, hideCompletedOpt.orElse(null))
                .stream()
                .map(t -> {
                    TodoDto todoDto = new TodoDto();
                    todoDto.setId(t.getId());
                    todoDto.setText(t.getText());
                    todoDto.setDone(t.isDone());
                    return todoDto;
                })
                .sorted(Comparator.comparing(TodoDto::getId))
                .collect(Collectors.toList());
    }

    public TodoGroupDto addTodoGroup(TodoGroupDto todoGroupDto) {
        TodoGroup todoGroup = new TodoGroup();
        todoGroup.setUser(usersServiceImpl.getCurrentUser());
        todoGroup.setName(todoGroupDto.getName());
        todoGroupDto.setId(todoGroupRepo.save(todoGroup).getId());
        return todoGroupDto;
    }

    public List<TodoGroupDto> getTodoGroups() {
        Long userId = usersServiceImpl.getCurrentUser().getId();
        return todoGroupRepo.findAllByUserId(userId).stream()
                .map(todoGroup -> new TodoGroupDto(todoGroup.getId(), todoGroup.getName()))
                .sorted(Comparator.comparing(TodoGroupDto::getId))
                .collect(Collectors.toList());
    }

    public TodoGroupDto editTodoGroup(Long id, TodoGroupDto todoGroupDto) {
        TodoGroup todoGroup = todoGroupRepo.findOneById(id);
        todoGroup.setName(todoGroupDto.getName());
        todoGroup = todoGroupRepo.save(todoGroup);
        return new TodoGroupDto(todoGroup.getId(), todoGroup.getName());
    }
}
