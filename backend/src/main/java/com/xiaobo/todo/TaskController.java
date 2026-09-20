package com.xiaobo.todo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;
import java.util.List;

@Validated
@Controller("/task")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @Get
    public List<Task> list() {
        return repository.findAll();
    }

    @Post
    public HttpResponse<Task> create(@Body @Valid TaskRequest request) {
        return HttpResponse.created(repository.create(request));
    }

    @Put("/{id}")
    public HttpResponse<Task> update(String id, @Body @Valid TaskRequest request) {
        return repository.update(id, request).map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(String id) {
        return repository.delete(id) ? HttpResponse.<Void>status(HttpStatus.NO_CONTENT) : HttpResponse.notFound();
    }
}
