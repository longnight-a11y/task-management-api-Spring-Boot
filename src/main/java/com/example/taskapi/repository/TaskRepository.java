package com.example.taskapi.repository;

import com.example.taskapi.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUserId(UUID userId);

    @Query("SELECT t FROM Task t JOIN FETCH t.user")
    Page<Task> findAllWithUser(Pageable pageable);

    @Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.id = :id")
    Optional<Task> findByIdWithUser(@Param("id") UUID id);
}
