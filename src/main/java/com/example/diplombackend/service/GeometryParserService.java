package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.*;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import com.example.diplombackend.model.figures.Round.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    CircleService circleService;
    @Autowired
    PolygonService polygonService;
    public List<Figure> parseText(String input) {
        List<String> figuresText = TextParser.splitByRegex(input, "\n");
        List<Figure> figures = new ArrayList<>();
        int count = 0;
        figuresText
                .stream()
                .filter(e -> e.contains("point"))
                .forEach(line -> {
                    Point point = pointService.createPointFrom(line, figures);
                    figures.add(point.getClass().cast(point));
                });
        for (String line : figuresText
                .stream()
                .filter(e -> !e.contains("point"))
                .toList()) {
            System.out.println(count++ + ". " + line);
            if (line.contains("polyline")) {
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
            } else if (line.contains("circle")) {
                Circle circle = circleService.createCircleFrom(line, figures);
                figures.add(circle.getClass().cast(circle));
            } else if (line.contains("Polygon")) {
                Polygon polygon = polygonService.createPolygonFrom(line, figures);
                figures.add(polygon.getClass().cast(polygon));
            }
        }
        segmentService.optimizeAll(figures);
        System.out.println("----------------------------");
        return figures;
    }

}
