package com.example.diplombackend.service;

import com.example.diplombackend.model.description.PolygonDescription;
import com.example.diplombackend.model.description.PolylineDescription;
import com.example.diplombackend.model.figures.Angle.Angle;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import com.example.diplombackend.model.figures.Polygon.PolygonFixed;
import com.example.diplombackend.model.figures.Polyline.Polyline;
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

        if (polygon instanceof PolygonFixed) {
            context.add(new Angle(polygon.getName()+"Angle",
                    (polygon.getSides() - 2.0) * 180 / polygon.getSides(),
                    null,null,null));
        } else {
            int pointsCount = polygon.getPoints().size();
            for (int i = 0; i < pointsCount; i++) {
                Point p1 = polygon.getPoints().get((pointsCount - 1 + i) % pointsCount);
                Point p2 = polygon.getPoints().get((pointsCount - 1 + i + 1) % pointsCount);
                Point p3 = polygon.getPoints().get((pointsCount - 1 + i + 2) % pointsCount);
                double d1 = distance(p1, p2);
                double d2 = distance(p1, p3);
                double d3 = distance(p2, p3);
                context.add(new Angle(p1.getName() + p2.getName() + p3.getName(),
                        Math.toDegrees(Math.acos( (d1*d1 + d2*d2 - d3*d3) / (2*d1*d2) )),
                        p1,p2,p3));
            }
        }

        return polygon;
    }
    public double distance(Point p1, Point p2) {
        double newX = p1.getCoordinate1()-p2.getCoordinate1();
        double newY = p1.getCoordinate2()-p2.getCoordinate2();
        return Math.sqrt(newX*newX + newY*newY);
    }

    public PolygonDescription createDescriptionFromPolygon(Polygon polygon) {
        return PolygonDescription
                .builder()
                .name(polygon.getName())
                .square(polygon.getSquare())
                .type(polygon.getType())
                .build();
    }
}
