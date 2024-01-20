package com.dazmy.todolist.security.repository;

import com.dazmy.todolist.security.entity.Todo;
import com.dazmy.todolist.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findAllByUser(User user);
    List<Todo> findAllByUserAndIsLikeIsTrue(User user);
    List<Todo> findAllByUserAndIsDoneIsTrue(User user);
    List<Todo> findAllByUserAndIsLikeIsTrueAndIsDoneIsTrue(User user);

    Optional<Todo> findByIdAndUser(String id, User user);
}
