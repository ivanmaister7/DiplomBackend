package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Angle.Angle;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class AngleService {

    public Angle createAngleFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        Angle angle = new Angle();
        List<String> temp = elements.subList(1, lastIndex(elements));

        angle.setName(last(split(first(elements))));
        angle.setAngle(Double.parseDouble(last(split(last(elements))).replaceAll("°","")));
        String A = last(splitByRegex(first(temp), "\\("));
        String B = prelast(temp);
        String C = last(temp).replaceAll("\\)", "");
        angle.setA((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(A))
                .findFirst()
                .orElseThrow());
        angle.setB((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(B))
                .findFirst()
                .orElseThrow());
        angle.setC((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(C))
                .findFirst()
                .orElseThrow());

        return angle;
    }
}
