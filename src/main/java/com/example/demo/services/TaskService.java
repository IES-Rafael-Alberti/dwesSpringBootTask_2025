package com.example.demo.services;

import com.example.demo.dto.CreateTaskDTO;
import com.example.demo.entities.Task;
import com.example.demo.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) { this.taskRepository = taskRepository; }

    public List<Task> list(Boolean done) {
        return (done == null) ? taskRepository.findAll() : taskRepository.findByDone(done);
    }

    public Task create(CreateTaskDTO dto) {
        Task newTask = Task.builder().title(dto.title()).done(false).build();
        return taskRepository.save(newTask);
    }

    public Task toggle(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Task not found"));
        task.setDone(!task.isDone());
        return task;
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) throw new IllegalArgumentException("Task not found");
        taskRepository.deleteById(id);
    }
}
