package com.example.diplombackend.controller;

import com.example.diplombackend.model.Book;
import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.UserTask;
import com.example.diplombackend.model.UserTaskKey;
import com.example.diplombackend.model.description.*;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.request.TaskRequest;
import com.example.diplombackend.model.responce.AttemptsResponse;
import com.example.diplombackend.model.responce.TaskResponse;
import com.example.diplombackend.repository.BookRepository;
import com.example.diplombackend.repository.TaskRepository;
import com.example.diplombackend.repository.UserRepository;
import com.example.diplombackend.repository.UserTaskRepository;
import com.example.diplombackend.service.DescriptionParserService;
import com.example.diplombackend.service.GeometryParserService;
import com.example.diplombackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    //TODO: remove repo and move to another service
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserTaskRepository userTaskRepository;
    @Autowired
    UserRepository userRepository;
    List<Description> firstDescriptions = null;
    AttemptsResponse attemptsResponse = new AttemptsResponse();
    @GetMapping("/info")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskRepository.findAll());
    }
    @GetMapping("/info/user/{id}")
    public ResponseEntity<?> getAllTasksForUser(@PathVariable Long id) {
        return ResponseEntity.ok(taskRepository
                .findAll()
                .stream()
                .map(e -> {
                    Optional<UserTask> userTask = userTaskRepository.findById(new UserTaskKey(e.getTask_id(), id));
                    return new TaskResponse(e, userTask.isPresent() && userTask.get().isDone());
                }));
    }
    @GetMapping("/info/filter/{id}")
    public ResponseEntity<?> getAllTasksByFilter(@RequestParam String filter, @PathVariable Long id) {
        if (filter.equals("All")) {
            return ResponseEntity.ok(taskRepository
                    .findAll()
                    .stream()
                    .map(e -> {
                        Optional<UserTask> userTask = userTaskRepository.findById(new UserTaskKey(e.getTask_id(), id));
                        return new TaskResponse(e, userTask.isPresent() && userTask.get().isDone());
                    }));
        }
        return ResponseEntity.ok(taskRepository
                .findAll()
                .stream()
                .filter(e -> e.getBook().toString().equals(filter))
                .map(e -> {
                    Optional<UserTask> userTask = userTaskRepository.findById(new UserTaskKey(e.getTask_id(), id));
                    return new TaskResponse(e, userTask.isPresent() && userTask.get().isDone());
                }));
    }
    @GetMapping("/info/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskRepository.findById(id).orElseThrow());
    }

    @GetMapping("/check/{id}/{user}")
    public ResponseEntity<?> getIsDoneById(@PathVariable Long id, @PathVariable Long user) {
        if (userTaskRepository.findById(new UserTaskKey(id,user)).isEmpty()) {
            UserTask userTask = new UserTask();
            userTask.setTask(taskRepository.findById(id).orElseThrow());
            userTask.setUser(userRepository.findById(user).orElseThrow());
            userTask.setDone(false);
            userTaskRepository.save(userTask);
        }

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

        return ResponseEntity.ok("");
    }
    @PostMapping("/new/{attempt}")
    public ResponseEntity<?> addNewTaskDescription(@RequestBody String userInput, @PathVariable String attempt) {
        List<Figure> figures = geometryParserService.parseText(userInput);
        List<Description> descriptions = descriptionParserService.parseFigure(figures);

        if (attempt.equals("1")) {
            firstDescriptions = descriptions;
            attemptsResponse.setAttempt1(true);
        } else {
            firstDescriptions = taskService.findCommonElementsFull(firstDescriptions, descriptions);
            attemptsResponse.setAttempt2(true);
        }
        return ResponseEntity.ok("");
    }
    @PostMapping("/new")
    public ResponseEntity<?> addNewTask(@RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setQuestion(taskRequest.getQuestion());
        task.setBook(bookRepository.findBookByName(taskRequest.getBook().split(", ")[0]));

        List<PointDescription> descriptionsOfType = getDescriptionsOfType(firstDescriptions, PointDescription.class);
        descriptionsOfType.forEach(e -> e.setTask(task));
        task.setDescriptions(descriptionsOfType);

        List<LineDescription> descriptionsOfType2 = getDescriptionsOfType(firstDescriptions, LineDescription.class);
        descriptionsOfType2.forEach(e -> e.setTask(task));
        task.setDescriptions2(descriptionsOfType2);

        List<SegmentDescription> descriptionsOfType3 = getDescriptionsOfType(firstDescriptions, SegmentDescription.class);
        descriptionsOfType3.forEach(e -> e.setTask(task));
        task.setDescriptions3(descriptionsOfType3);

        List<RayDescription> descriptionsOfType4 = getDescriptionsOfType(firstDescriptions, RayDescription.class);
        descriptionsOfType4.forEach(e -> e.setTask(task));
        task.setDescriptions4(descriptionsOfType4);

        List<PolylineDescription> descriptionsOfType5 = getDescriptionsOfType(firstDescriptions, PolylineDescription.class);
        descriptionsOfType5.forEach(e -> e.setTask(task));
        task.setDescriptions5(descriptionsOfType5);

        taskRepository.save(task);

        attemptsResponse = new AttemptsResponse();
        return ResponseEntity.ok("");
    }
    @PostMapping("/new/clear")
    public ResponseEntity<?> clearAttempts() {
        attemptsResponse = new AttemptsResponse();
        return ResponseEntity.ok("");
    }
    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        List<String> allBooks = bookRepository.findAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.toList());
        allBooks.add(0,"All");
        return ResponseEntity.ok(allBooks);
    }
    @GetMapping("/new/attempts")
    public ResponseEntity<?> getIsAttempts() {
        return ResponseEntity.ok(attemptsResponse);
    }
}
