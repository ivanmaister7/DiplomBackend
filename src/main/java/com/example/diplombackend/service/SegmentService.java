package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Segment;
import com.example.diplombackend.model.figures.Point.Point;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class SegmentService {
    public Segment createSegmentFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        Segment segment = new Segment();
        segment.setName(last(split(first(elements))));
        segment.setLength(Double.parseDouble(last(split(last(elements)))));
        List<String> temp = elements.subList(1, lastIndex(elements));
        String A = last(splitByRegex(first(temp), "\\("));
        String B = last(temp).replaceAll("\\)","");
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
}
