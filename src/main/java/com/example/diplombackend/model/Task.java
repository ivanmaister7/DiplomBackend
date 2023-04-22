package com.example.diplombackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @Column(nullable = false, unique = true)
    public Long task_id;

    @Column(nullable = false)
    public String question;

    @Column(nullable = false)
    public String answer;

    @Column(nullable = false)
    public Boolean isDone;
}
