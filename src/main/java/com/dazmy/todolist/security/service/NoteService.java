package com.dazmy.todolist.security.service;

import com.dazmy.todolist.security.model.request.NoteRequest;
import com.dazmy.todolist.security.model.response.NoteResponse;

public interface NoteService {
    NoteResponse create(NoteRequest request, String id);

    NoteResponse update(NoteRequest request, String id);
}
