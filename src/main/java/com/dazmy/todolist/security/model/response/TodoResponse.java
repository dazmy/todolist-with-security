package com.dazmy.todolist.security.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoResponse {
    private String id;
    private String name;
    private Boolean isLike;
    private Boolean isDone;
    private NoteResponse note;
}
