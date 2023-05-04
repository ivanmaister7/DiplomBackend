package com.example.diplombackend.controller;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.description.Description;
import com.example.diplombackend.model.description.PointDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.PointType;
import com.example.diplombackend.service.DescriptionParserService;
import com.example.diplombackend.service.GeometryParserService;
import com.example.diplombackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    GeometryParserService geometryParserService;
    @Autowired
    DescriptionParserService descriptionParserService;
    @Autowired
    TaskService taskService;
    Task task = new Task(1L,"Draw midpoint named Mid of fixed points Start(-5,2) and End(-1,2)", "5", false);
    Task task2 = new Task(2L,"Draw midpoint (-3,2) of random points", "5", false);
    Task task3 = new Task(3L,"Draw line named f of y = 2 on points (-5,2) and (-1,2)", "5", false);
    Task task4 = new Task(4L,"Draw any line", "5", false);

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok(id.equals("3") ? task3 : task4);
    }

    @PostMapping("/check/{id}")
    public ResponseEntity<?> checkTaskIsDoneById(@RequestBody String userInput,
                                                 @PathVariable String id) {
        List<Figure> figures = geometryParserService.parseText(userInput);
        List<Description> descriptions = descriptionParserService.parseFigure(figures);
        (id.equals("3") ? task3 : task4).setIsDone(taskService.checkAnswerById(descriptions, id));

        return ResponseEntity.ok("");
    }
}
