package com.example.taskapi.service;

import com.example.taskapi.Entity.Task;
import com.example.taskapi.Entity.User;
import com.example.taskapi.dto.*;
import com.example.taskapi.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional  // ← 途中でエラーになったらDB変更を全部なかったことにする。GET系にはつけない
    public TaskResponse taskCreate(TaskCreateRequest taskCreateRequest, User currentUser){

        Task task = new Task();
        task.setTitle(taskCreateRequest.title());
        task.setDescription(taskCreateRequest.description());
        task.setUser(currentUser);
        return toResponse(taskRepository.save(task));  // toResponse()使う理由はobsidian見て

    }

    public PageResponse<TaskResponseWithUser> getTasks(int page, int size){

        Page<Task> result = taskRepository.findAllWithUser(PageRequest.of(page - 1, size));
        List<TaskResponseWithUser> items = result.getContent().stream().map(this::toResponseWithUser).toList();
        return new PageResponse<>(items, (int) result.getTotalElements(), page, size);
    }

    public List<TaskResponse> getMyTasks(UUID userId){

        return taskRepository.findByUserId(userId).stream()
                .map(this::toResponse).toList();
    }

    public TaskResponseWithUser getSingleTaskWithUser(UUID taskId){

        Task task = taskRepository.findByIdWithUser(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return toResponseWithUser(task);
    }

    @Transactional
    public void deleteTask(UUID taskId, User currentUser){

        Task task = taskRepository.findByIdWithUser(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        checkOwnership(task, currentUser, "delete");
        taskRepository.delete(task);
    }


    private void checkOwnership(Task t, User u, String action){
        if(!t.getUser().getId().equals(u.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to " + action + "this task.");
        }
    }

    private TaskResponse toResponse(Task t){
        return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted(), t.getUser().getId());
    }

    private TaskResponseWithUser toResponseWithUser(Task t){
        User u = t.getUser();
        return new TaskResponseWithUser(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted(),
                new UserResponse(u.getId(), u.getUsername()));
    }
}
