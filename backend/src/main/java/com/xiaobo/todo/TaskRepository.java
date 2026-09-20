package com.xiaobo.todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/** Keeps tasks in memory and writes them to a local JSON file on every change. */
@Repository
public class TaskRepository {

    private final ObjectMapper mapper;
    private final Path file;
    private final Map<String, Task> tasks = new LinkedHashMap<>();

    public TaskRepository(ObjectMapper mapper, @Value("${todo.data-file}") String dataFile) {
        this.mapper = mapper;
        this.file = Path.of(dataFile).toAbsolutePath();
        load();
    }

    private void load() {
        if (!Files.exists(file)) {
            return;
        }
        try {
            if (Files.size(file) == 0) {
                return;
            }
            for (Task t : mapper.readValue(file.toFile(), new TypeReference<List<Task>>() {})) {
                tasks.put(t.id(), t);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read tasks file " + file, e);
        }
    }

    private void persist() {
        try {
            Files.createDirectories(file.getParent());
            Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
            mapper.writerWithDefaultPrettyPrinter().writeValue(tmp.toFile(), new ArrayList<>(tasks.values()));
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write tasks file " + file, e);
        }
    }

    public synchronized List<Task> findAllByOrderByCreatedDateDesc() {
        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::createdDate).reversed())
                .toList();
    }

    public synchronized Optional<Task> findById(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public synchronized boolean existsById(String id) {
        return tasks.containsKey(id);
    }

    public synchronized Task save(Task task) {
        Task saved = task.id() != null ? task : new Task(
                UUID.randomUUID().toString(), task.title(), task.description(), task.completed(),
                task.createdDate(), task.dueDate(), task.additionalContent());
        tasks.put(saved.id(), saved);
        persist();
        return saved;
    }

    public synchronized void deleteById(String id) {
        if (tasks.remove(id) != null) {
            persist();
        }
    }
}
