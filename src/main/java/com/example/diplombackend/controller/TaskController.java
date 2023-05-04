package com.example.diplombackend.controller;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.UserTask;
import com.example.diplombackend.model.description.Description;
import com.example.diplombackend.model.description.LineDescription;
import com.example.diplombackend.model.description.PointDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Point.PointType;
import com.example.diplombackend.repository.TaskRepository;
import com.example.diplombackend.repository.UserTaskRepository;
import com.example.diplombackend.service.DescriptionParserService;
import com.example.diplombackend.service.GeometryParserService;
import com.example.diplombackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.diplombackend.service.TaskService.getDescriptionsOfType;

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
    //TODO: remove repo and move to another service
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserTaskRepository userTaskRepository;
    List<Description> answer3 = List.of(
            PointDescription.builder().name(null).type(PointType.POINT).coordinate1(-1.0).coordinate2(2.0).build(),
            PointDescription.builder().name(null).type(PointType.POINT).coordinate1(-5.0).coordinate2(2.0).build(),
            LineDescription.builder().name("f").type(LineType.SINGLELINE).equation("y = 2").build()
    );
    List<Description> answer4 = List.of(
            PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
            PointDescription.builder().name(null).type(PointType.POINT).coordinate1(null).coordinate2(null).build(),
            LineDescription.builder().name(null).type(LineType.SINGLELINE).equation(null).build()
    );
//    Task task3 = new Task(1L,
//            "Draw line named f of y = 2 on points (-5,2) and (-1,2)",
//            false,
//            getDescriptionsOfType(answer3, PointDescription.class),
//            getDescriptionsOfType(answer3, LineDescription.class));
//    Task task4 = new Task(2L,
//            "Draw any line",
//            getDescriptionsOfType(answer4, PointDescription.class),
//            getDescriptionsOfType(answer4, LineDescription.class),
//            null);

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskRepository.findById(id).orElseThrow());
    }

    @GetMapping("/check/{id}/{user}")
    public ResponseEntity<?> getIsDoneById(@PathVariable Long id, @PathVariable Long user) {
//        System.out.println(id);
//        System.out.println(user);
        return ResponseEntity.ok(taskRepository
                .findById(id)
                .orElseThrow()
                .getUsers()
                .stream()
                .filter(e -> e.getUser().getId().equals(user))
                .findFirst()
                .orElseThrow()
        );
    }

    @PostMapping("/check/{id}/{user}")
    public ResponseEntity<?> checkTaskIsDoneById(@RequestBody String userInput,
                                                 @PathVariable Long id,
                                                 @PathVariable Long user) {
        List<Figure> figures = geometryParserService.parseText(userInput);
        List<Description> descriptions = descriptionParserService.parseFigure(figures);

//        Task task = new Task();
//        task.setIsDone(false);
//        task.setQuestion("Draw any line");
//        List<PointDescription> descriptionsOfType = getDescriptionsOfType(answer4, PointDescription.class);
//        descriptionsOfType.forEach(e -> e.setTask(task));
//        task.setDescriptions(descriptionsOfType);
//        List<LineDescription> descriptionsOfType2 = getDescriptionsOfType(answer4, LineDescription.class);
//        descriptionsOfType2.forEach(e -> e.setTask(task));
//        task.setDescriptions2(descriptionsOfType2);
//        taskRepository.save(task);
//        System.out.println(taskRepository.findAll());

        UserTask userTask = taskRepository
                .findById(id)
                .orElseThrow()
                .getUsers()
                .stream()
                .filter(e -> e.getUser().getId().equals(user))
                .findFirst()
                .orElseThrow();
        userTask.setDone(taskService.checkAnswerById(descriptions, id));
        userTaskRepository.save(userTask);


        System.out.println("END");
        return ResponseEntity.ok("");
    }
}
