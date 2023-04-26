package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.*;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Round.Circle;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class CircleService {
    public Circle createCircleFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        List<String> temp = elements.subList(1, lastIndex(elements));
        Circle circle = new Circle();
//        LineType type = checkType(temp, context);
//
//        if (type.equals(LineType.SINGLE)) {
//            circle = new SingleLine();
//            String B = last(temp).replaceAll("\\)","");
//            ((SingleLine) circle).setB((Point) context.stream()
//                    .filter(e -> e instanceof Point && e.getName().equals(B))
//                    .findFirst()
//                    .orElse(context.get(0))); //TODO: remove in future and back previous version
////                    .orElseThrow());
//        } else if (type.equals(LineType.PERPENDICULAR)) {
//            circle = new PerpendicularLine();
//            String base = last(temp).replaceAll("\\)","");
//            ((PerpendicularLine) circle).setBase((Line) context.stream()
//                    .filter(e -> e instanceof Line && e.getName().equals(base))
//                    .findFirst()
//                    .orElseThrow());
//        } else if (type.equals(LineType.PARALLEL)) {
//            circle = new ParallelLine();
//            String base = last(temp).replaceAll("\\)","");
//            ((ParallelLine) circle).setBase((Line) context.stream()
//                    .filter(e -> e instanceof Line && e.getName().equals(base))
//                    .findFirst()
//                    .orElse(context.get(2))); //TODO: remove in future and back previous version
//            //.orElseThrow();
//        } else if (type.equals(LineType.PERPENDICULAR_BISECTOR)) {
//            circle = new PerpendicularBisectorLine();
//            String base = last(temp).replaceAll("\\)","");
//            ((PerpendicularBisectorLine) circle).setB((Point) context.stream()
//                    .filter(e -> e instanceof Point && e.getName().equals(base))
//                    .findFirst()
//                    .orElseThrow());
//        } else if (type.equals(LineType.ANGLE_BISECTOR)) {
//            circle = new AngleBisectorLine();
//            String B = prelast(temp);
//            String C = last(temp).replaceAll("\\)","");
//            ((AngleBisectorLine) circle).setB((Point) context.stream()
//                    .filter(e -> e instanceof Point && e.getName().equals(B))
//                    .findFirst()
//                    .orElseThrow());
//            ((AngleBisectorLine) circle).setC((Point) context.stream()
//                    .filter(e -> e instanceof Point && e.getName().equals(C))
//                    .findFirst()
//                    .orElseThrow());
//        }

        circle.setName(last(TextParser.split(first(elements))));
        circle.setEquation(last(splitByRegex(last(elements), ": ")));
        String center = last(splitByRegex(first(temp), "\\("));
        String R = last(splitByRegex(first(temp), "\\("));
        circle.setCenter((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(center))
                .findFirst()
                .orElseThrow());
        circle.setR((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(R))
                .findFirst()
                .orElseThrow());



        return circle;
    }

    public LineType checkType(List<String> input, List<Figure> context) {
        String name = last(input).replaceAll("\\)","");
        Figure figure = context.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(context.get(0)); //TODO: remove in future and back previous version
        //.orElseThrow();
        return first(input).contains("Line") &&
                figure instanceof Point ? LineType.SINGLE :
                first(input).contains("PerpendicularBisector") &&
                        figure instanceof Point ? LineType.PERPENDICULAR_BISECTOR :
                        first(input).contains("AngleBisector") &&
                                figure instanceof Point ? LineType.ANGLE_BISECTOR :
                                first(input).contains("PerpendicularLine") &&
                                        figure instanceof Line ? LineType.PERPENDICULAR : LineType.PARALLEL;
    }
}
