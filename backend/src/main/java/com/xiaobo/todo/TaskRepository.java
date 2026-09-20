package com.xiaobo.todo;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

@Singleton
public class TaskRepository {

    private final MongoCollection<Document> tasks;

    public TaskRepository(MongoClient client, @Value("${mongodb.database:todo}") String database) {
        this.tasks = client.getDatabase(database).getCollection("tasks");
    }

    public List<Task> findAll() {
        List<Task> result = new ArrayList<>();
        tasks.find().sort(Sorts.descending("createdDate")).forEach(d -> result.add(toTask(d)));
        return result;
    }

    public Task create(TaskRequest req) {
        Document doc = new Document("title", req.title())
                .append("description", req.description())
                .append("completed", Boolean.TRUE.equals(req.completed()))
                .append("createdDate", Instant.now().toString());
        if (req.dueDate() != null) doc.append("dueDate", req.dueDate().toString());
        if (req.additionalContent() != null) doc.append("additionalContent", req.additionalContent());
        tasks.insertOne(doc);
        return toTask(doc);
    }

    public Optional<Task> update(String id, TaskRequest req) {
        ObjectId oid = parse(id);
        if (oid == null) return Optional.empty();
        List<Bson> updates = new ArrayList<>();
        updates.add(Updates.set("title", req.title()));
        updates.add(Updates.set("description", req.description()));
        updates.add(req.dueDate() != null
                ? Updates.set("dueDate", req.dueDate().toString())
                : Updates.unset("dueDate"));
        updates.add(req.additionalContent() != null
                ? Updates.set("additionalContent", req.additionalContent())
                : Updates.unset("additionalContent"));
        if (req.completed() != null) updates.add(Updates.set("completed", req.completed()));
        Document updated = tasks.findOneAndUpdate(
                eq("_id", oid),
                Updates.combine(updates),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return Optional.ofNullable(updated).map(TaskRepository::toTask);
    }

    public boolean delete(String id) {
        ObjectId oid = parse(id);
        return oid != null && tasks.deleteOne(eq("_id", oid)).getDeletedCount() > 0;
    }

    private static ObjectId parse(String id) {
        return ObjectId.isValid(id) ? new ObjectId(id) : null;
    }

    private static Task toTask(Document d) {
        String due = d.getString("dueDate");
        return new Task(
                d.getObjectId("_id").toHexString(),
                d.getString("title"),
                d.getString("description"),
                d.getBoolean("completed", false),
                Instant.parse(d.getString("createdDate")),
                due == null ? null : LocalDate.parse(due),
                d.getString("additionalContent"));
    }
}
