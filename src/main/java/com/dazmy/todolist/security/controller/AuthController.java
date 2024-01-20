package com.dazmy.todolist.security.controller;

import com.dazmy.todolist.security.model.request.UserRequest;
import com.dazmy.todolist.security.model.response.CoreResponse;
import com.dazmy.todolist.security.model.response.SignInResponse;
import com.dazmy.todolist.security.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping(path = "/sign-up", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CoreResponse<String> signUp(@RequestBody UserRequest request, HttpServletResponse response) {
        String data = authService.signUp(request);

        response.setStatus(HttpStatus.CREATED.value());
        return CoreResponse.<String>builder().status(HttpStatus.CREATED.value()).data(data).build();
    }

    @PostMapping(path = "/sign-in", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CoreResponse<SignInResponse> signIn(@RequestBody UserRequest request) {
        SignInResponse signInResponse = authService.signIn(request);

        return CoreResponse.<SignInResponse>builder().status(HttpStatus.OK.value()).data(signInResponse).build();
    }
}
