package com.dazmy.todolist.security.service.implementation;

import com.dazmy.todolist.security.entity.Role;
import com.dazmy.todolist.security.entity.User;
import com.dazmy.todolist.security.model.request.UserRequest;
import com.dazmy.todolist.security.model.response.SignInResponse;
import com.dazmy.todolist.security.model.response.UserResponse;
import com.dazmy.todolist.security.repository.UserRepository;
import com.dazmy.todolist.security.service.AuthService;
import com.dazmy.todolist.security.service.JwtService;
import com.dazmy.todolist.security.service.ValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private ValidationService validationService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String signUp(UserRequest request) {
        validationService.validate(request);

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username was taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return "OK";
    }

    @Override
    @Transactional(readOnly = true)
    public SignInResponse signIn(UserRequest request) {
        validationService.validate(request);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        log.info("check for user manual");
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password invalid");
        }

        log.info("Start creating token");
        String jwtToken = jwtService.generateToken(user.getUsername());

        return SignInResponse.builder()
                .user(UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole().name())
                        .build())
                .token(jwtToken)
                .build();
    }
}
