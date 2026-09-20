package com.xiaobo.todo;

import com.mongodb.client.MongoClient;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.Document;

@Controller("/health")
public class HealthController {

    private final MongoClient mongoClient;

    public HealthController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Serdeable
    public record Health(String status, String database) {}

    @Get
    public HttpResponse<Health> health() {
        try {
            mongoClient.getDatabase("admin").runCommand(new Document("ping", 1));
            return HttpResponse.ok(new Health("UP", "UP"));
        } catch (Exception e) {
            return HttpResponse.<Health>status(io.micronaut.http.HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new Health("DOWN", "DOWN"));
        }
    }
}
