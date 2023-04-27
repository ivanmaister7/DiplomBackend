package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import com.example.diplombackend.model.figures.Polygon.PolygonFixed;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class PolygonService {
    public Polygon createPolygonFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        Polygon polygon = new Polygon();
        List<String> temp = elements.subList(1, lastIndex(elements));
        for (String elem : temp) {
            String pointName = elem
                    .replaceAll("Polygon\\(","")
                    .replaceAll("\\)","");
            if (pointName.matches("\\d+")) {
                polygon = new PolygonFixed(polygon);
                polygon.setSides(Integer.parseInt(pointName));
            } else {
                polygon.addPoint((Point) context.stream()
                        .filter(e -> e instanceof Point && e.getName().equals(pointName))
                        .findFirst()
                        .orElseThrow());
            }
        }
        polygon.setName(last(split(first(elements))));
        polygon.setSquare(Double.parseDouble(last(split(last(elements)))));

        return polygon;
    }
}
