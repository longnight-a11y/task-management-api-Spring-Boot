package com.example.taskapi.Controller;

import com.example.taskapi.Entity.User;
import com.example.taskapi.dto.PageResponse;
import com.example.taskapi.dto.TaskCreateRequest;
import com.example.taskapi.dto.TaskResponse;
import com.example.taskapi.dto.TaskResponseWithUser;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create Task")
    public TaskResponse createTask(@Valid @RequestBody TaskCreateRequest task,
                                   @AuthenticationPrincipal User currentUser){
        return taskService.taskCreate(task, currentUser);
    }

    @GetMapping
    @Operation(summary = "Get Tasks")
    public PageResponse<TaskResponseWithUser> getTasks(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size){
        return taskService.getTasks(page, size);
    }

    @GetMapping("/me")
    @Operation(summary = "Get My Tasks")
    public PageResponse<TaskResponseWithUser> getMyTasks(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @AuthenticationPrincipal User currentUser){
        return taskService.getMyTasks(page, size, currentUser.getId());
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get Single Task")
    public TaskResponseWithUser getSingleTaskWithUser(@PathVariable UUID taskId){
        return taskService.getSingleTaskWithUser(taskId);
    }

    // Updateもいつか実装したい

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete Task")
    public Map<String, String> deleteTask(@PathVariable UUID taskId, @AuthenticationPrincipal User currentUser){
        taskService.deleteTask(taskId, currentUser);
        return Map.of("detail", "Task was deleted successfully!");
    }

}
