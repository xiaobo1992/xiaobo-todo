package com.xiaobo.todo;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.time.LocalDate;

@Serdeable
public record Task(
        String id,
        String title,
        String description,
        boolean completed,
        Instant createdDate,
        @Nullable LocalDate dueDate,
        @Nullable String additionalContent) {}
