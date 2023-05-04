package com.example.diplombackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserTask {
    @EmbeddedId
    private UserTaskKey userTaskKey = new UserTaskKey();
    @ManyToOne
    @MapsId("task_id")
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private boolean isDone;
}
