package com.example.diplombackend.repository;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
