package com.example.diplombackend.service;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.description.Description;
import com.example.diplombackend.model.description.FigureDescription;
import com.example.diplombackend.model.description.LineDescription;
import com.example.diplombackend.model.description.PointDescription;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Point.PointType;
import com.example.diplombackend.repository.TaskRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    PointService pointService;
    @Autowired
    LineService lineService;
    @Autowired
    SegmentService segmentService;
    @Autowired
    RayService rayService;
    @Autowired
    PolylineService polylineService;
    @Autowired
    VectorService vectorService;
    @Autowired
    CircleService circleService;
    @Autowired
    AngleService angleService;
    @Autowired
    PolygonService polygonService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    @FigureDescription
    List<Description> descriptions;

    public boolean checkAnswerById(List<Description> input, Long id) {
//        List<String> figuresText = new ArrayList<>(TextParser.splitByRegex(input, "\n"));
//        List<Figure> figures = new ArrayList<>();
//        List<Description> descriptions = new ArrayList<>();
//        int count = 0;
//        System.out.println(figuresText.size() - 1);
//        for (String line : figuresText) {
//            System.out.println(count++ + ". " + line);
//        }
//        List<String> pointsText = figuresText
//                .stream()
//                .filter(e -> e.contains(", ,") || (e.contains("Polygon") && e.contains("point")))
//                .toList();
//        pointsText
//                .forEach(line -> {
//                    Point point = pointService.createPointFrom(line, figures);
//                    figures.add(point.getClass().cast(point));
//                });
//        figuresText.removeAll(pointsText);
//        for (Description figure : input) {
//            if (figure instanceof PointDescription) {
//                descriptions.add(pointService.createDescriptionFromPoint((Point) figure));
//            } else if (figure instanceof Polyline) {
//                descriptions.add(polylineService.createDescriptionFromPolyline((Polyline) figure));
//            } else if (figure.contains("line")) {
//                Optional<Line> line1 = lineService.createLineFrom(figure, descriptions);
//                line1.ifPresent(value -> descriptions.add(value.getClass().cast(value)));
//            } else if (figure.contains("segment")) {
//                Segment segment = segmentService.createSegmentFrom(figure, descriptions);
//                descriptions.add(segment.getClass().cast(segment));
//            } else if (figure.contains("ray")) {
//                Ray ray = rayService.createRayFrom(figure, descriptions);
//                descriptions.add(ray.getClass().cast(ray));
//            } else if (figure.contains("vector")) {
//                Vector vector = vectorService.createVectorFrom(figure, descriptions);
//                descriptions.add(vector.getClass().cast(vector));
//            } else if (figure.contains("Semicircle")) {
//                Semicircle semicircle = circleService.createSemicircleFrom(figure, descriptions);
//                descriptions.add(semicircle.getClass().cast(semicircle));
//            } else if (figure.contains("circle")) {
//                Circle circle = circleService.createCircleFrom(figure, descriptions);
//                descriptions.add(circle.getClass().cast(circle));
//            } else if (figure.contains("Polygon")) {
//                Polygon polygon = polygonService.createPolygonFrom(figure, descriptions);
//                descriptions.add(polygon.getClass().cast(polygon));
//            } else if (figure.contains("angle")) {
//                Angle angle = angleService.createAngleFrom(figure, descriptions);
//                descriptions.add(angle.getClass().cast(angle));
//            }
//        }

        Task task = taskRepository.findById(id).orElseThrow();
        List<Description> allDescription = task.getAllDescription();
//        LineDescription(lineDescription_id=null, name=null, equation=null, type=SINGLELINE, task=null)
//        PointDescription(pointDescription_id=null, name=null, type=POINT, coordinate1=null, coordinate2=null, task=null)
//        PointDescription(pointDescription_id=null, name=null, type=POINT, coordinate1=null, coordinate2=null, task=null)

        for (Description c : descriptions) {
            boolean checkAnswer = checkAnswerForType(input, allDescription, c.getClass());
            if (!checkAnswer) {
                return false;
            }
        }
        return true;
    }

    private <T extends Description> boolean checkAnswerForType(List<Description> input, List<Description> answer, Class<T> type) {
        List<T> inputOfType = getDescriptionsOfType(input, type);
        List<T> answerOfType = getDescriptionsOfType(answer, type);
        return answerOfType.size() <= inputOfType.size() && checkAnswer(inputOfType, answerOfType);
    }

    public static  <T extends Description> List<T> getDescriptionsOfType(List<Description> descriptions, Class<T> type) {
        return descriptions.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }


    @SneakyThrows
    public <T> T findMaxDescription(T description, List<T> context) {
        T maxDescription = null;
        if (context.isEmpty()){
            return null;
        }
        int count = 0;
        int maxCount = 0;
        for (T tempDescription : context) {
            for (Field field : description.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(description) == null || field.get(description).equals(field.get(tempDescription))) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                maxDescription = tempDescription;
            }
            count = 0;
        }
        if (maxDescription == null){
            return null;
        }
        maxDescription = (T) maxDescription.getClass().getConstructor(maxDescription.getClass()).newInstance(maxDescription);
        for (Field field : description.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(description) == null ||
                    !field.get(description).equals(field.get(maxDescription))) {
                field.set(maxDescription, null);
            } else {
                field.set(maxDescription, field.get(description));
            }
        }
        return maxDescription;
    }
    public <T> boolean checkAnswer(List<T> userDrawDescription, List<T> answerDescription) {
        for (T description : answerDescription) {
            if (!containsIgnoreId(answerDescription, findMaxDescription(description, userDrawDescription))){
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    private <T> boolean containsIgnoreId(List<T> answerDescription, T maxDescription) {
        if (maxDescription == null) {
            return false;
        }
        int count = 0;
        for (T description : answerDescription) {
            for (Field field : description.getClass().getDeclaredFields()) {
                if (field.get(description) != null && field.get(maxDescription) == null) {
                    continue;
                }
                if (field.get(description) == null || field.get(description).equals(field.get(maxDescription))) {
                    count++;
                }
            }
            if (count >= description.getClass().getDeclaredFields().length - 2){
                return true;
            }
            count = 0;
        }
        return false;
    }
    public <T extends Description> List<T> findCommonElementsByType(List<Description> context1, List<Description> context2, Class<T> type) {
        List<T> context1OfType = getDescriptionsOfType(context1, type);
        List<T> context2OfType = getDescriptionsOfType(context2, type);
        return findCommonElements(context1OfType, context2OfType);
    }

    private <T> List<T> findCommonElements(List<T> context1, List<T> context2) {
        return context1.size() > context2.size() ?
                findCommonElements(context2, context1) :
                context1.stream()
                        .map(e -> findMaxDescription(e, context2))
                        .collect(Collectors.toList());
    }
    public List<Description> findCommonElementsFull(List<Description> context1, List<Description> context2) {
        List<Description> result = new ArrayList<>();
        for (Description c : descriptions) {
            result.addAll(findCommonElementsByType(context1, context2, c.getClass()));
        }
        return result;
    }

}
