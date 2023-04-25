package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.*;
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
    @Autowired
    LineService lineService;
    @Autowired
    SegmentService segmentService;
    @Autowired
    RayService rayService;
    @Autowired
    PolylineService polylineService;
    @Autowired
    VectorService vectorService;
    public List<Figure> parseText(String input) {
        List<String> figuresText = TextParser.splitByRegex(input, "\n");
        List<Figure> figures = new ArrayList<>();
        int count = 0;
        for (String line : figuresText) {
            System.out.println(count++ + ". " + line);
            if (line.contains("point")) {
                Point point = pointService.createPointFrom(line, figures);
                figures.add(point.getClass().cast(point));
            } else if (line.contains("polyline")) {
                Polyline polyline = polylineService.createPolylineFrom(line, figures);
                figures.add(polyline.getClass().cast(polyline));
            } else if (line.contains("line")) {
                Line line1 = lineService.createLineFrom(line, figures);
                figures.add(line1.getClass().cast(line1));
            } else if (line.contains("segment")) {
                Segment segment = segmentService.createSegmentFrom(line, figures);
                figures.add(segment.getClass().cast(segment));
            } else if (line.contains("ray")) {
                Ray ray = rayService.createRayFrom(line, figures);
                figures.add(ray.getClass().cast(ray));
            } else if (line.contains("vector")) {
                Vector vector = vectorService.createVectorFrom(line, figures);
                figures.add(vector.getClass().cast(vector));
            }
        }
        System.out.println("----------------------------");
        return figures;
    }

}
