package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Vector.CollinearVector;
import com.example.diplombackend.model.figures.Vector.Vector;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Point.Translate;
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
        List<String> temp = elements.subList(1, lastIndex(elements));
        Vector vector = new Vector();
        String A = last(splitByRegex(first(temp), "\\("));
        String B = last(temp).replaceAll("\\)","");
        Point pointA = findByName(A, context);
        Point pointB = findByName(B, context);
        if (pointB.getClass().equals(Translate.class)) {
            vector = new CollinearVector();
            ((CollinearVector) vector).setCollinearTo(((Translate) pointB).getVector());
        }
        vector.setName(last(TextParser.split(first(elements))));
        vector.setEquation(last(splitByRegex(last(elements), "= ")));
        vector.setA(pointA);
        vector.setB(pointB);

        return vector;
    }

    private Point findByName(String name, List<Figure> context) {
        return (Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }
}
