package com.dazmy.todolist.security.service.implementation;

import com.dazmy.todolist.security.entity.User;
import com.dazmy.todolist.security.repository.UserRepository;
import com.dazmy.todolist.security.service.JwtService;
import com.dazmy.todolist.security.service.UserService;
import com.dazmy.todolist.security.service.custom.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JwtService jwtService;

    @Override
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Override
    public User getCurrentUser() {
        String username = jwtService.getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
