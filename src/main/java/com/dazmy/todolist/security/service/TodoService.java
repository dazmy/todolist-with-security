package com.dazmy.todolist.security.service;

import com.dazmy.todolist.security.entity.User;
import com.dazmy.todolist.security.model.request.TodoRequest;
import com.dazmy.todolist.security.model.request.TodoUpdateRequest;
import com.dazmy.todolist.security.model.response.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse create(User user, TodoRequest request);

    List<TodoResponse> getAll(User user, boolean like, boolean done);

    TodoResponse update(User user, TodoUpdateRequest request);

    String delete(User user, String id);
}
