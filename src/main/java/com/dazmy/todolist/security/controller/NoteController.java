package com.dazmy.todolist.security.controller;

import com.dazmy.todolist.security.model.request.NoteRequest;
import com.dazmy.todolist.security.model.response.CoreResponse;
import com.dazmy.todolist.security.model.response.NoteResponse;
import com.dazmy.todolist.security.service.NoteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(path = "/api/v1/todolist/{todolistId}/note")
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CoreResponse<NoteResponse> create(@RequestBody NoteRequest request, @PathVariable String todolistId, HttpServletResponse response) {
        NoteResponse noteResponse = noteService.create(request, todolistId);

        response.setStatus(HttpStatus.CREATED.value());
        return CoreResponse.<NoteResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(noteResponse)
                .build();
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CoreResponse<NoteResponse> update(@RequestBody NoteRequest request, @PathVariable String todolistId) {
        NoteResponse noteResponse = noteService.update(request, todolistId);

        return CoreResponse.<NoteResponse>builder()
                .status(HttpStatus.OK.value())
                .data(noteResponse)
                .build();
    }
}
