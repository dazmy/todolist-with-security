package com.dazmy.todolist.security.service;

import com.dazmy.todolist.security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    User getCurrentUser();
}
