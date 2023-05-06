package com.example.diplombackend.service;

import com.example.diplombackend.model.description.PolylineDescription;
import com.example.diplombackend.model.description.SegmentDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Polyline.Polyline;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Segment.Segment;
import com.example.diplombackend.model.figures.Segment.SegmentType;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class PolylineService {
    public Polyline createPolylineFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        Polyline polyline = new Polyline();
        polyline.setName(last(split(first(elements))));
        polyline.setLength(Double.parseDouble(last(split(last(elements)))));
        List<String> temp = elements.subList(1, lastIndex(elements));
        for (String pointName : temp) {
            polyline.addPoint((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(pointName
                            .replaceAll("Polyline\\(","")
                            .replaceAll("\\)","")))
                    .findFirst()
                    .orElseThrow());
        }

        return polyline;
    }
    public PolylineDescription createDescriptionFromPolyline(Polyline polyline) {
        return PolylineDescription
                .builder()
                .name(polyline.getName())
                .length(polyline.getLength())
                .build();
    }
}
