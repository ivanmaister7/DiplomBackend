package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Point.PointType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeometryParserService {
    @Autowired
    PointService pointService;
    public List<Figure> parseText(String input) {
        List<String> figuresText = TextParser.splitByRegex(input, "\n");
        List<Figure> figures = new ArrayList<>();
        int count = 0;
        for (String line : figuresText) {
            System.out.println(++count + ". " + line);
            if (line.contains("point")) {
                Point point = pointService.createPointFrom(line, figures);
                figures.add(point.getClass().cast(point));
            }
        }
        System.out.println("----------------------------");
        return figures;
    }

}
