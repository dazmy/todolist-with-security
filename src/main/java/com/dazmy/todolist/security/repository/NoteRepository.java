package com.dazmy.todolist.security.repository;

import com.dazmy.todolist.security.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, String> {
}
