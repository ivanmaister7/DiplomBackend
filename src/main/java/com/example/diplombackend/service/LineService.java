package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Point.Point;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class LineService {
    public Line createLineFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        Line line = new Line();
        line.setName(last(TextParser.split(first(elements))));
        line.setEquation(last(splitByRegex(last(elements), ": ")));
        List<String> temp = elements.subList(1, lastIndex(elements));
        String A = last(splitByRegex(first(temp), "\\("));
        String B = last(temp).replaceAll("\\)","");
        line.setA((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(A))
                .findFirst()
                .orElseThrow());
        line.setB((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(B))
                .findFirst()
                .orElseThrow());

        return line;
    }
}
