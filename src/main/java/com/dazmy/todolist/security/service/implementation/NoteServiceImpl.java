package com.dazmy.todolist.security.service.implementation;

import com.dazmy.todolist.security.entity.Note;
import com.dazmy.todolist.security.model.request.NoteRequest;
import com.dazmy.todolist.security.model.response.NoteResponse;
import com.dazmy.todolist.security.repository.NoteRepository;
import com.dazmy.todolist.security.repository.TodoRepository;
import com.dazmy.todolist.security.service.NoteService;
import com.dazmy.todolist.security.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private NoteRepository noteRepository;
    private TodoRepository todoRepository;
    private ValidationService validationService;

    @Override
    @Transactional
    public NoteResponse create(NoteRequest request, String id) {
        validationService.validate(request);

        validateTodoAndNoteCreate(id);

        Note note = new Note();
        note.setId(id);
        note.setInfo(request.getInfo());

        noteRepository.save(note);
        return toNoteResponse(note);
    }

    @Override
    @Transactional
    public NoteResponse update(NoteRequest request, String id) {
        validationService.validate(request);

        Note note = validateTodoAndNoteUpdate(id);
        note.setInfo(request.getInfo());

        return toNoteResponse(note);
    }

    private NoteResponse toNoteResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .info(note.getInfo())
                .build();
    }

    private void validateTodoExists(String id) {
        boolean todoExists = todoRepository.existsById(id);
        if (!todoExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
    }

    private void validateTodoAndNoteCreate(String id) {
        validateTodoExists(id);

        boolean noteExists = noteRepository.existsById(id);
        if (noteExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note was created");
        }
    }

    private Note validateTodoAndNoteUpdate(String id) {
        validateTodoExists(id);

        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }
}
