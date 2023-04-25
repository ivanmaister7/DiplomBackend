package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Line.Vector;
import com.example.diplombackend.model.figures.Point.Point;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class VectorService {
    public Vector createVectorFrom(String input, List<Figure> context) {
        StringBuilder builder = new StringBuilder(input);
        builder.setCharAt(input.lastIndexOf(','), ';');
        input = builder.toString();

        List<String> elements = splitByRegex(input, ", ");
        Vector vector = new Vector();
        vector.setName(last(TextParser.split(first(elements))));
        vector.setEquation(last(splitByRegex(last(elements), "= ")));
        List<String> temp = elements.subList(1, lastIndex(elements));
        String A = last(splitByRegex(first(temp), "\\("));
        String B = last(temp).replaceAll("\\)","");
        vector.setA((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(A))
                .findFirst()
                .orElseThrow());
        vector.setB((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(B))
                .findFirst()
                .orElseThrow());

        return vector;
    }
}
