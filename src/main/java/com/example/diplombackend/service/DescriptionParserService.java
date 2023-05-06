package com.example.diplombackend.service;

import com.example.diplombackend.model.description.Description;
import com.example.diplombackend.model.figures.Angle.Angle;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Line.Ray;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import com.example.diplombackend.model.figures.Polyline.Polyline;
import com.example.diplombackend.model.figures.Round.Circle;
import com.example.diplombackend.model.figures.Round.Semicircle;
import com.example.diplombackend.model.figures.Segment.Segment;
import com.example.diplombackend.model.figures.Vector.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DescriptionParserService {
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
    public List<Description> parseFigure(List<Figure> input) {
//        List<String> figuresText = new ArrayList<>(TextParser.splitByRegex(input, "\n"));
//        List<Figure> figures = new ArrayList<>();
        List<Description> descriptions = new ArrayList<>();
//        int count = 0;
//        System.out.println(figuresText.size() - 1);
//        for (String line : figuresText) {
//            System.out.println(count++ + ". " + line);
//        }
//        List<String> pointsText = figuresText
//                .stream()
//                .filter(e -> e.contains(", ,") || (e.contains("Polygon") && e.contains("point")))
//                .toList();
//        pointsText
//                .forEach(line -> {
//                    Point point = pointService.createPointFrom(line, figures);
//                    figures.add(point.getClass().cast(point));
//                });
//        figuresText.removeAll(pointsText);
        for (Figure figure : input) {
            if (figure instanceof Point) {
                descriptions.add(pointService.createDescriptionFromPoint((Point) figure));
            } else if (figure instanceof Ray) {
                descriptions.add(rayService.createDescriptionFromRay((Ray) figure));
            } else if (figure instanceof Line) {
                descriptions.add(lineService.createDescriptionFromLine((Line) figure));
            } else if (figure instanceof Segment) {
                descriptions.add(segmentService.createDescriptionFromSegment((Segment) figure));
            } else if (figure instanceof Polyline) {
                descriptions.add(polylineService.createDescriptionFromPolyline((Polyline) figure));
            } else if (figure instanceof Vector) {
                descriptions.add(vectorService.createDescriptionFromVector((Vector) figure));
            } else if (figure instanceof Angle) {
                descriptions.add(angleService.createDescriptionFromAngle((Angle) figure));
            } else if (figure instanceof Polygon) {
                descriptions.add(polygonService.createDescriptionFromPolygon((Polygon) figure));
            } else if (figure instanceof Circle) {
                descriptions.add(circleService.createDescriptionFromCircle((Circle) figure));
            } else if (figure instanceof Semicircle) {
                descriptions.add(circleService.createDescriptionFromSemicircle((Semicircle) figure));

//            } else if (figure.contains("line")) {
//                Optional<Line> line1 = lineService.createLineFrom(figure, descriptions);
//                line1.ifPresent(value -> descriptions.add(value.getClass().cast(value)));
//            } else if (figure.contains("segment")) {
//                Segment segment = segmentService.createSegmentFrom(figure, descriptions);
//                descriptions.add(segment.getClass().cast(segment));
//            } else if (figure.contains("ray")) {
//                Ray ray = rayService.createRayFrom(figure, descriptions);
//                descriptions.add(ray.getClass().cast(ray));
//            } else if (figure.contains("vector")) {
//                Vector vector = vectorService.createVectorFrom(figure, descriptions);
//                descriptions.add(vector.getClass().cast(vector));
//            } else if (figure.contains("Semicircle")) {
//                Semicircle semicircle = circleService.createSemicircleFrom(figure, descriptions);
//                descriptions.add(semicircle.getClass().cast(semicircle));
//            } else if (figure.contains("circle")) {
//                Circle circle = circleService.createCircleFrom(figure, descriptions);
//                descriptions.add(circle.getClass().cast(circle));
//            } else if (figure.contains("Polygon")) {
//                Polygon polygon = polygonService.createPolygonFrom(figure, descriptions);
//                descriptions.add(polygon.getClass().cast(polygon));
//            } else if (figure.contains("angle")) {
//                Angle angle = angleService.createAngleFrom(figure, descriptions);
//                descriptions.add(angle.getClass().cast(angle));
            }
        }
        return descriptions;
    }

}
