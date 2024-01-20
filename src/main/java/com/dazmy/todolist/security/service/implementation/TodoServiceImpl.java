package com.dazmy.todolist.security.service.implementation;

import com.dazmy.todolist.security.entity.Todo;
import com.dazmy.todolist.security.entity.User;
import com.dazmy.todolist.security.model.request.TodoRequest;
import com.dazmy.todolist.security.model.request.TodoUpdateRequest;
import com.dazmy.todolist.security.model.response.NoteResponse;
import com.dazmy.todolist.security.model.response.TodoResponse;
import com.dazmy.todolist.security.repository.TodoRepository;
import com.dazmy.todolist.security.service.TodoService;
import com.dazmy.todolist.security.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private ValidationService validationService;

    @Override
    @Transactional
    public TodoResponse create(User user, TodoRequest request) {
        validationService.validate(request);

        Todo todo = new Todo();
        todo.setId(UUID.randomUUID().toString());
        todo.setUser(user);
        todo.setName(request.getName());
        todo.setIsLike(false);
        todo.setIsDone(false);
        todoRepository.save(todo);

        return TodoResponse.builder()
                .id(todo.getId())
                .name(todo.getName())
                .isLike(todo.getIsLike())
                .isDone(todo.getIsDone())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAll(User user, boolean like, boolean done) {
        // do not query exits user, because it's catch in filter
        final List<Todo> todos;

        if (like && done) {
            todos = todoRepository.findAllByUserAndIsLikeIsTrueAndIsDoneIsTrue(user);
        } else if (like) {
            todos = todoRepository.findAllByUserAndIsLikeIsTrue(user);
        } else if (done) {
            todos = todoRepository.findAllByUserAndIsDoneIsTrue(user);
        } else {
            todos = todoRepository.findAllByUser(user);
        }

        return todos.stream().map(this::toTodoResponse).toList();
    }

    @Override
    @Transactional
    public TodoResponse update(User user, TodoUpdateRequest request) {
        validationService.validate(request);

        Todo todo = todoRepository.findByIdAndUser(request.getId(), user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        if (Objects.nonNull(request.getName())) {
            todo.setName(request.getName());
        }

        if (Objects.nonNull(request.getIsLike())) {
            todo.setIsLike(request.getIsLike());
        }

        if (Objects.nonNull(request.getIsDone())) {
            todo.setIsDone(request.getIsDone());
        }

        todoRepository.save(todo);

        return toTodoResponse(todo);
    }

    @Override
    @Transactional
    public String delete(User user, String id) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        todoRepository.delete(todo);

        return "OK";
    }

    private TodoResponse toTodoResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .name(todo.getName())
                .isLike(todo.getIsLike())
                .isDone(todo.getIsDone())
                .note(todo.getNote() != null ? NoteResponse.builder().id(todo.getNote().getId()).info(todo.getNote().getInfo()).build() : null)
                .build();
    }
}
