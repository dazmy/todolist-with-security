package com.dazmy.todolist.security.controller;

import com.dazmy.todolist.security.entity.User;
import com.dazmy.todolist.security.model.request.TodoRequest;
import com.dazmy.todolist.security.model.request.TodoUpdateRequest;
import com.dazmy.todolist.security.model.response.CoreResponse;
import com.dazmy.todolist.security.model.response.TodoResponse;
import com.dazmy.todolist.security.service.TodoService;
import com.dazmy.todolist.security.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(path = "/api/v1/todolist")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;
    private UserService userService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CoreResponse<TodoResponse> create(@RequestBody TodoRequest request, HttpServletResponse response) {
        User user = userService.getCurrentUser();
        TodoResponse todoResponse = todoService.create(user, request);

        response.setStatus(HttpStatus.CREATED.value());
        return CoreResponse.<TodoResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(todoResponse)
                .build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public CoreResponse<List<TodoResponse>> getAll(@RequestParam(required = false, defaultValue = "false") boolean like,
                                                   @RequestParam(required = false, defaultValue = "false") boolean done) {
        User user = userService.getCurrentUser();
        List<TodoResponse> todoResponses = todoService.getAll(user, like, done);

        return CoreResponse.<List<TodoResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(todoResponses)
                .build();
    }

    @PatchMapping(path = "/{todolistId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public CoreResponse<TodoResponse> update(@RequestBody TodoUpdateRequest request, @PathVariable String todolistId) {
        request.setId(todolistId);

        User user = userService.getCurrentUser();
        TodoResponse todoResponse = todoService.update(user, request);

        return CoreResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .data(todoResponse)
                .build();
    }

    @DeleteMapping(path = "/{todolistId}")
    public CoreResponse<String> delete(@PathVariable String todolistId) {
        User user = userService.getCurrentUser();
        String result = todoService.delete(user, todolistId);

        return CoreResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .data(result)
                .build();
    }
}
