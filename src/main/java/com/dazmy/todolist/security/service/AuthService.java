package com.dazmy.todolist.security.service;

import com.dazmy.todolist.security.model.request.UserRequest;
import com.dazmy.todolist.security.model.response.SignInResponse;

public interface AuthService {
    String signUp(UserRequest request);

    SignInResponse signIn(UserRequest request);
}
