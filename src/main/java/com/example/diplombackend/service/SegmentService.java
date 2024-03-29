package com.example.diplombackend.service;

import com.example.diplombackend.model.description.LineDescription;
import com.example.diplombackend.model.description.SegmentDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Segment.Segment;
import com.example.diplombackend.model.figures.Segment.SegmentIn;
import com.example.diplombackend.model.figures.Segment.SegmentType;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class SegmentService {

    public Segment createSegmentFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        Segment segment = null;
        List<String> temp = elements.subList(1, lastIndex(elements));
        SegmentType type = checkType(temp);
        if (type.equals(SegmentType.SEGMENT)) {
            segment = new Segment();
        } else if (type.equals(SegmentType.SEGMENTIN)) {
            segment = new SegmentIn();
            String base = last(temp).replaceAll("\\)","");
            ((SegmentIn) segment).setBase((Polygon) context.stream()
                    .filter(e -> e instanceof Polygon && e.getName().equals(base))
                    .findFirst()
                    .orElseThrow());
        }
        segment.setName(last(split(first(elements))));
        segment.setLength(Double.parseDouble(last(split(last(elements)))));
        String A = last(splitByRegex(first(temp), "\\("));
        String B = type.equals(SegmentType.SEGMENTIN) ?
                prelast(temp) :
                last(temp).replaceAll("\\)", "");
        segment.setA((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(A))
                .findFirst()
                .orElseThrow());
        segment.setB((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(B))
                .findFirst()
                .orElseThrow());

        return segment;
    }
    public SegmentType checkType(List<String> input) {
        return input.size() == 3 ? SegmentType.SEGMENTIN : SegmentType.SEGMENT;
    }

    public void optimizeAll(List<Figure> context) {
        context
                .stream()
                .filter(e -> e.getClass().equals(SegmentIn.class))
                .forEach(segment -> {
                    Polygon base = ((SegmentIn) segment).getBase();
                    base.addPoint(((SegmentIn) segment).getA());
                    base.addPoint(((SegmentIn) segment).getB());
                });
    }
    public SegmentDescription createDescriptionFromSegment(Segment segment) {
        return SegmentDescription
                .builder()
                .name(segment.getName())
                .length(segment.getLength())
                .type(SegmentType.valueOf(segment.getClass().getSimpleName().toUpperCase()))
                .build();
    }
}
