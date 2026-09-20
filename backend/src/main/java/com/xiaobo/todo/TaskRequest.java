package com.xiaobo.todo;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/** Body for POST /task and PUT /task/:id. `completed` is only applied when present. */
@Serdeable
public record TaskRequest(
        @NotBlank String title,
        @NotBlank String description,
        @Nullable LocalDate dueDate,
        @Nullable String additionalContent,
        @Nullable Boolean completed) {}
