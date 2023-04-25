package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.MidPoint;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Point.PointIn;
import com.example.diplombackend.model.figures.Point.PointType;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;

@Service
public class PointService {
    public Point createPointFrom(String input, List<Figure> context) {
        List<String> elements = TextParser.splitByRegex(input, ", ");
        Point point = null;
        PointType type = checkType(input);
        // TODO: Point(Circle(E, 10)) check and implement
        // TODO: Translate(H, u) check and implement
        if (type.equals(PointType.SINGLE)) {
            point = new Point();
        } else if (type.equals(PointType.MIDPOINT)) {
            point = new MidPoint();
            List<String> temp = elements.subList(1, lastIndex(elements));
            String A = last(TextParser.splitByRegex(first(temp), "\\("));
            String B = last(temp).replaceAll("\\)","");
            ((MidPoint) point).setA((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(A))
                    .findFirst()
                    .orElseThrow());
            ((MidPoint) point).setB((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(B))
                    .findFirst()
                    .orElseThrow());

        } else if (type.equals(PointType.POINTIN)) {
            point = new PointIn();
            //String name = last(TextParser.splitByRegex(elements.get(1), "\\(")).replaceAll("\\)","");
            String name = "A"; // TODO: delete after implement all figures
            ((PointIn) point).setOwner(context.stream()
                    .filter(e -> e.getName().equals(name))
                    .findFirst()
                    .orElseThrow());
        }
        point.setName(last(TextParser.split(first(elements))));
        point.setCoordinate2(Double.parseDouble(last(elements).replaceAll("\\)","")));
        point.setCoordinate1(Double.parseDouble(last(TextParser.splitByRegex(prelast(elements), "\\("))));
        return point;
    }

    PointType checkType(String input) {
        return input.contains("Midpoint") ? PointType.MIDPOINT :
                input.contains("PointIn") ? PointType.POINTIN : PointType.SINGLE;
    }
}
