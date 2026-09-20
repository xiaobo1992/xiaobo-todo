package com.xiaobo.todo;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/** Body for POST /task and PUT /task/{id}. `completed` is only applied when present. */
public record TaskRequest(
        @NotBlank String title,
        @NotBlank String description,
        LocalDate dueDate,
        String additionalContent,
        Boolean completed) {}
