package com.example.diplombackend.repository;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
}
