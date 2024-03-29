package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Angle.Angle;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.*;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import com.example.diplombackend.model.figures.Polyline.Polyline;
import com.example.diplombackend.model.figures.Round.Circle;
import com.example.diplombackend.model.figures.Round.Semicircle;
import com.example.diplombackend.model.figures.Segment.Segment;
import com.example.diplombackend.model.figures.Vector.Vector;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    AngleService angleService;
    @Autowired
    PolygonService polygonService;
    public List<Figure> parseText(String input) {
        List<String> figuresText = new ArrayList<>(TextParser.splitByRegex(input, "\n"));
        List<Figure> figures = Lists.newArrayList(
                new SingleLine("xAxis","y = 0",new Point(), new Point()),
                new SingleLine("yAxis","x = 0",new Point(), new Point())
        );
        List<String> pointsText = figuresText
                .stream()
                .filter(e -> e.contains(", ,") || (e.contains("Polygon") && e.contains("point")))
                .toList();
        pointsText
                .forEach(line -> {
                    Point point = pointService.createPointFrom(line, figures);
                    figures.add(point.getClass().cast(point));
                });
        figuresText.removeAll(pointsText);
        for (String line : figuresText) {
            if (line.contains("point")) {
                Point point = pointService.createPointFrom(line, figures);
                figures.add(point.getClass().cast(point));
            } else if (line.contains("polyline")) {
                Polyline polyline = polylineService.createPolylineFrom(line, figures);
                figures.add(polyline.getClass().cast(polyline));
            } else if (line.contains("line")) {
                Optional<Line> line1 = lineService.createLineFrom(line, figures);
                line1.ifPresent(value -> figures.add(value.getClass().cast(value)));
            } else if (line.contains("segment")) {
                Segment segment = segmentService.createSegmentFrom(line, figures);
                figures.add(segment.getClass().cast(segment));
            } else if (line.contains("ray")) {
                Ray ray = rayService.createRayFrom(line, figures);
                figures.add(ray.getClass().cast(ray));
            } else if (line.contains("vector")) {
                Vector vector = vectorService.createVectorFrom(line, figures);
                figures.add(vector.getClass().cast(vector));
            } else if (line.contains("Semicircle")) {
                Semicircle semicircle = circleService.createSemicircleFrom(line, figures);
                figures.add(semicircle.getClass().cast(semicircle));
            } else if (line.contains("circle")) {
                Circle circle = circleService.createCircleFrom(line, figures);
                figures.add(circle.getClass().cast(circle));
            } else if (line.contains("Polygon")) {
                Polygon polygon = polygonService.createPolygonFrom(line, figures);
                figures.add(polygon.getClass().cast(polygon));
            } else if (line.contains("angle")) {
                Angle angle = angleService.createAngleFrom(line, figures);
                figures.add(angle.getClass().cast(angle));
            }
        }
        segmentService.optimizeAll(figures);
        return figures;
    }

}
