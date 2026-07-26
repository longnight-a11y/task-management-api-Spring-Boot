package com.example.taskapi.service;

import com.example.taskapi.Entity.Task;
import com.example.taskapi.Entity.User;
import com.example.taskapi.dto.PageResponse;
import com.example.taskapi.dto.TaskCreateRequest;
import com.example.taskapi.dto.TaskResponse;
import com.example.taskapi.dto.TaskResponseWithUser;
import com.example.taskapi.fixture.TaskFixture;
import com.example.taskapi.fixture.UserFixture;
import com.example.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;

    @BeforeEach
    void setUp(){
        user = UserFixture.user();
    }

    @Test
    void createTask_success(){
        TaskCreateRequest request = new TaskCreateRequest("Test", "Test Task", false);

        Task task = TaskFixture.task(user, request.title(), request.description());

        when(taskRepository.save(any(Task.class))).thenReturn(task);  // ← 今後save(Task)が踏まれたとき、Task taskを返してもらうよう設定

        TaskResponse response = taskService.taskCreate(request, user); // 実行

        assertEquals(task.getId(), response.id());
        assertEquals("Test", response.title());
        assertEquals(user.getId(), response.userId());
        verify(taskRepository).save(any(Task.class));  // save()が呼ばれたか確認
    }

    @Test
    void getTasks_success(){

        Task task = TaskFixture.task(user, "Task", "Description");
        // Page<>はinterface、PageImpl<>がその実装クラス
        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAllWithUser(any(PageRequest.class))).thenReturn(page);

        PageResponse<TaskResponseWithUser> response = taskService.getTasks(1, 10);  // 実行

        assertEquals(1, response.total());
        assertEquals(1, response.items().size());
        assertEquals(task.getId(), response.items().getFirst().id());

        verify(taskRepository).findAllWithUser(any(PageRequest.class));
    }

    @Test
    void getSingleTask_success(){

        Task task = TaskFixture.task(user, "Task", "Description");

        when(taskRepository.findByIdWithUser(task.getId())).thenReturn(Optional.of(task));

        TaskResponseWithUser response = taskService.getSingleTaskWithUser(task.getId());

        assertEquals(task.getId(), response.id());
        assertEquals(user.getId(), response.user().id());
    }

    @Test
    void getSingleTask_notFound(){

        UUID id = UUID.randomUUID();
        when(taskRepository.findByIdWithUser(id)).thenReturn(Optional.empty());
        // taskService.getSingleTaskWithUser(id) を実行したら、ResponseStatusException が発生してください って意味
        assertThrows(ResponseStatusException.class, () -> taskService.getSingleTaskWithUser(id));
    }

    @Test
    void deleteTask_success(){
        Task task = TaskFixture.task(user, "Task", "Description");

        when(taskRepository.findByIdWithUser(task.getId())).thenReturn(Optional.of(task));

        taskService.deleteTask(task.getId(), user);  // 実行

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_forbidden(){
        User otherUser = new User();
        Task task = TaskFixture.task(user, "Task", "Description");

        when(taskRepository.findByIdWithUser(task.getId())).thenReturn(Optional.of(task));

        assertThrows(ResponseStatusException.class, () -> taskService.deleteTask(task.getId(), otherUser));
        verify(taskRepository, never()).delete(task);  // taskRepository.delete(task) してないよな？の確認
    }


}
