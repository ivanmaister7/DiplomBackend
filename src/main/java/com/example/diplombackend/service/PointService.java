package com.example.diplombackend.service;

import com.example.diplombackend.model.description.PointDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Vector.Vector;
import com.example.diplombackend.model.figures.Point.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class PointService {
    public Point createPointFrom(String input, List<Figure> context) {
        List<String> elements = TextParser.splitByRegex(input, ", ");
        List<String> temp = elements.subList(1, lastIndex(elements) - 1);
        Point point = null;
        PointType type = checkType(input);
        // TODO: Point(Circle(E, 10)) check and implement
        if (type.equals(PointType.POINT)) {
            point = new Point();
        } else if (type.equals(PointType.MIDPOINT)) {
            point = new MidPoint();
            String A = last(TextParser.splitByRegex(first(temp), "\\("));
            String B = last(temp).replaceAll("\\)","");
            ((MidPoint) point).setA((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(A))
                    .findFirst()
                    .orElseThrow());
            ((MidPoint) point).setB((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(B))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(PointType.POINTIN)) {
            point = new PointIn();
            String name = last(TextParser.splitByRegex(elements.get(1), "\\(")).replaceAll("\\)","");
            ((PointIn) point).setOwner(context.stream()
                    .filter(e -> e.getName().equals(name))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(PointType.INTERSECT)) {
            point = new Intersect();

            String line1 = last(splitByRegex(first(temp), "\\("));
            String line2 = last(temp).replaceAll("\\)","");

            ((Intersect) point).setLine1(findLineByName(line1, context));
            ((Intersect) point).setLine2(findLineByName(line2, context));
        } else if (type.equals(PointType.TRANSLATE)) {
            point = new Translate();
            String vector = last(temp).replaceAll("\\)","");

            ((Translate) point).setVector(findVectorByName(vector, context));
        }
        point.setName(last(TextParser.split(first(elements))));
        point.setCoordinate2(Double.parseDouble(last(elements).replaceAll("\\)","")));
        point.setCoordinate1(Double.parseDouble(last(TextParser.splitByRegex(prelast(elements), "\\("))));
        return point;
    }

    public PointType checkType(String input) {
        return input.contains("Midpoint") ? PointType.MIDPOINT :
                input.contains("PointIn") ? PointType.POINTIN :
                        input.contains("Translate") ? PointType.TRANSLATE :
                                input.contains("Intersect") ? PointType.INTERSECT : PointType.POINT;
    }
    private Line findLineByName(String name, List<Figure> context) {
        return (Line) context.stream()
                .filter(e -> e instanceof Line && e.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }
    private Vector findVectorByName(String name, List<Figure> context) {
        return (Vector) context.stream()
                .filter(e -> e instanceof Vector && e.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    public PointDescription createDescriptionFromPoint(Point point) {
        return PointDescription
                .builder()
                .name(point.getName())
                .coordinate1(point.getCoordinate1())
                .coordinate2(point.getCoordinate2())
                .type(PointType.valueOf(point.getClass().getSimpleName().toUpperCase()))
                .build();
    }

    @SneakyThrows
    public PointDescription findMaxDescription(PointDescription description, List<PointDescription> context) {
        PointDescription maxDescription = null;
        if (context.isEmpty()){
            return null;
        }
        int count = 0;
        int maxCount = 0;
        for (PointDescription tempDescription : context) {
            for (Field field : PointDescription.class.getDeclaredFields()) {
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
        maxDescription = PointDescription.builder()
                .name(maxDescription.getName())
                .type(maxDescription.getType())
                .coordinate1(maxDescription.getCoordinate1())
                .coordinate2(maxDescription.getCoordinate2())
                .build();
        for (Field field : PointDescription.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(description) == null || !field.get(description).equals(field.get(maxDescription))) {
                field.set(maxDescription, null);
            }
        }
        return maxDescription;
    }

    public List<PointDescription> findCommonPointDescription(List<PointDescription> context1,
                                                             List<PointDescription> context2) {
        return context1.size() > context2.size() ?
                findCommonPointDescription(context2, context1) :
                context1.stream()
                .map(e -> findMaxDescription(e, context2))
                .collect(Collectors.toList());
    }

    public boolean checkAnswer(List<PointDescription> userDrawDescription,
                               List<PointDescription> answerDescription) {
        for (PointDescription description : answerDescription) {
            if (!answerDescription.contains(findMaxDescription(description, userDrawDescription))){
                return false;
            }
        }
        return true;
    }

}
