package com.example.diplombackend.service;

import com.example.diplombackend.model.description.CircleDescription;
import com.example.diplombackend.model.description.LineDescription;
import com.example.diplombackend.model.description.SemicircleDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Round.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class CircleService {
    public Semicircle createSemicircleFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        List<String> temp = elements.subList(1, lastIndex(elements));
        Semicircle semicircle = new Semicircle();
        semicircle.setName(last(split(first(elements))));
        semicircle.setLength(Double.parseDouble(last(split(last(elements)))));
        String start = last(splitByRegex(first(temp), "\\("));
        String end = last(temp).replaceAll("\\)","");
        semicircle.setStart((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(start))
                .findFirst()
                .orElseThrow());
        semicircle.setEnd((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(end))
                .findFirst()
                .orElseThrow());

        return semicircle;
    }
    public Circle createCircleFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        List<String> temp = elements.subList(1, lastIndex(elements));
        Circle circle = null;
        CircleType type = checkType(temp);

        if (type.equals(CircleType.CIRCLE)) {
            circle = new Circle();
            String center = last(splitByRegex(first(temp), "\\("));
            circle.setCenter((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(center))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(CircleType.CIRCLER)) {
            circle = new CircleR();
            String center = last(splitByRegex(first(temp), "\\("));
            String R = last(temp).replaceAll( "\\)", "");
            circle.setCenter((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(center))
                    .findFirst()
                    .orElseThrow());
            ((CircleR) circle).setR((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(R))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(CircleType.CIRCLEBYPOINTS)) {
            circle = new CircleByPoints();
            for (String pointName : temp) {
                ((CircleByPoints) circle).addPoint((Point) context.stream()
                        .filter(e -> e instanceof Point && e.getName().equals(pointName
                                .replaceAll("Circle\\(","")
                                .replaceAll("\\)","")))
                        .findFirst()
                        .orElseThrow());
            }
        }

        circle.setName(last(TextParser.split(first(elements))));
        circle.setEquation(last(splitByRegex(last(elements), ": ")));

        return circle;
    }

    public CircleType checkType(List<String> input) {
        return last(input)
                .replaceAll("\\)","")
                .matches("\\d{1,13}(\\.\\d*)?") ? CircleType.CIRCLE :
                prelast(input).contains("Segment") ? CircleType.CIRCLE :
                        input.size() == 3 ? CircleType.CIRCLEBYPOINTS : CircleType.CIRCLER;
    }
    public CircleDescription createDescriptionFromCircle(Circle circle) {
        return CircleDescription
                .builder()
                .name(circle.getName())
                .equation(circle.getEquation())
                .radius(circle.getRadius())
                .type(CircleType.valueOf(circle.getClass().getSimpleName().toUpperCase()))
                .build();
    }
    public SemicircleDescription createDescriptionFromSemicircle(Semicircle circle) {
        return SemicircleDescription
                .builder()
                .name(circle.getName())
                .length(circle.getLength())
                .radius(circle.getRadius())
                .build();
    }
}
