package com.xiaobo.todo;

import java.time.Instant;
import java.time.LocalDate;

public record Task(
        String id,
        String title,
        String description,
        boolean completed,
        Instant createdDate,
        LocalDate dueDate,
        String additionalContent) {}
