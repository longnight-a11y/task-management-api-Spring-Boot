package com.example.taskapi.fixture;

import com.example.taskapi.Entity.Task;
import com.example.taskapi.Entity.User;

import java.util.UUID;

public final class TaskFixture {

    private TaskFixture(){}

    public static Task task(User user, String title, String description){
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(false);
        task.setUser(user);
        return task;
    }
}
