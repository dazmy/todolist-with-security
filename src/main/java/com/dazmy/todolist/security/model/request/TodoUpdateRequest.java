package com.dazmy.todolist.security.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoUpdateRequest {
    @JsonIgnore
    @NotBlank
    private String id;

    @Size(max = 100)
    private String name;

    private Boolean isLike;

    private Boolean isDone;
}
