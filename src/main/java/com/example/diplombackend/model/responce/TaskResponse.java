package com.example.diplombackend.model.responce;

import com.example.diplombackend.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    private Task task;
    private boolean isDone;
}
