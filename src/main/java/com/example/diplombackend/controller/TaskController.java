package com.example.diplombackend.controller;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.service.GeometryParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    GeometryParserService geometryParserService;
    Task task = new Task(1L,"Draw Line!", "5", false);

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok(task);
    }

    @PostMapping("/check/{id}")
    public ResponseEntity<?> checkTaskIsDoneById(@RequestBody String userInput,
                                                 @PathVariable String id) {
        task.setIsDone(userInput.contains("line"));

        int count = 0;
        for (Figure f : geometryParserService.parseText(userInput)) {
            System.out.println(++count + ". " + f);
        }

        return ResponseEntity.ok("");
    }
}
