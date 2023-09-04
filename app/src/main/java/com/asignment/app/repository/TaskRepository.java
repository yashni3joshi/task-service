package com.asignment.app.repository;

import com.asignment.app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserUid(Long uid);
    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Task findTaskById(@Param("taskId") Long taskId);


}

