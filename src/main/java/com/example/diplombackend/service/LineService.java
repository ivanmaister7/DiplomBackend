package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.*;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Point.PointType;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class LineService {
    public Line createLineFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        List<String> temp = elements.subList(1, lastIndex(elements));
        Line line = null;
        LineType type = checkType(temp, context);

        if (type.equals(LineType.SINGLE)) {
            line = new SingleLine();
            String B = last(temp).replaceAll("\\)","");
            ((SingleLine) line).setB((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(B))
                    .findFirst()
                    .orElse(context.get(0))); //TODO: remove in future and back previous version
//                    .orElseThrow());
        } else if (type.equals(LineType.PERPENDICULAR)) {
            line = new PerpendicularLine();
            String base = last(temp).replaceAll("\\)","");
            ((PerpendicularLine) line).setBase((Line) context.stream()
                    .filter(e -> e instanceof Line && e.getName().equals(base))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.PARALLEL)) {
            line = new ParallelLine();
            String base = last(temp).replaceAll("\\)","");
            ((ParallelLine) line).setBase((Line) context.stream()
                    .filter(e -> e instanceof Line && e.getName().equals(base))
                    .findFirst()
                    .orElseThrow());
        }

        line.setName(last(TextParser.split(first(elements))));
        line.setEquation(last(splitByRegex(last(elements), ": ")));
        String A = last(splitByRegex(first(temp), "\\("));
        line.setA((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(A))
                .findFirst()
                .orElseThrow());



        return line;
    }

    public LineType checkType(List<String> input, List<Figure> context) {
        String name = last(input).replaceAll("\\)","");
        Figure figure = context.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(context.get(0)); //TODO: remove in future and back previous version
                //.orElseThrow();
        return figure instanceof Point ? LineType.SINGLE :
                first(input).contains("peddicular") &&
                        figure instanceof Line ? LineType.PERPENDICULAR : LineType.PARALLEL;
    }
}
