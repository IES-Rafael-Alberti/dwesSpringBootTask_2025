package com.example.tasks.controllers;

import com.example.tasks.entities.Task;
import com.example.tasks.repositories.TaskRepository;
import com.example.tasks.dto.CreateTaskDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Task> list(@RequestParam(required = false) Boolean done) {
        if (done == null) {
            return repository.findAll();
        } else {
            return repository.findByDone(done);
        }
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid CreateTaskDTO dto) {
        Task newTask = Task.builder()
                .title(dto.title())
                .done(false)
                .build();

        Task saved = repository.save(newTask);

        return ResponseEntity
                .created(URI.create("/tasks/" + saved.getId()))
                .body(saved);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggle(@PathVariable Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.setDone(!task.isDone());
        Task updated = repository.save(task);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Task not found");
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}