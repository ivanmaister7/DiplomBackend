package com.example.diplombackend.service;

import com.example.diplombackend.model.description.LineDescription;
import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.*;
import com.example.diplombackend.model.figures.Point.Point;
import com.example.diplombackend.model.figures.Round.Circle;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class LineService {
    public Optional<Line> createLineFrom(String input, List<Figure> context) {
        List<String> elements = splitByRegex(input, ", ");
        List<String> temp = elements.subList(1, lastIndex(elements));
        Line line = null;
        LineType type = checkType(temp, context);

        if (type.equals(LineType.SINGLELINE)) {
            line = new SingleLine();
            String B = last(temp).replaceAll("\\)","");
            ((SingleLine) line).setB((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(B))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.PERPENDICULARLINE)) {
            line = new PerpendicularLine();
            String base = last(temp).replaceAll("\\)","");
            ((PerpendicularLine) line).setBase((Line) context.stream()
                    .filter(e -> e instanceof Line && e.getName().equals(base))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.PARALLELLINE)) {
            line = new ParallelLine();
            String base = last(temp).replaceAll("\\)","");
            ((ParallelLine) line).setBase(context.stream()
                    .filter(e -> e.getName().equals(base))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.PERPENDICULARBISECTORLINE)) {
            line = new PerpendicularBisectorLine();
            String base = last(temp).replaceAll("\\)","");
            ((PerpendicularBisectorLine) line).setB((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(base))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.ANGLEBISECTORLINE)) {
            line = new AngleBisectorLine();
            String B = prelast(temp);
            String C = last(temp).replaceAll("\\)","");
            ((AngleBisectorLine) line).setB((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(B))
                    .findFirst()
                    .orElseThrow());
            ((AngleBisectorLine) line).setC((Point) context.stream()
                    .filter(e -> e instanceof Point && e.getName().equals(C))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.TANGENT)) {
            line = new Tangent();
            String circle = last(temp).replaceAll("\\)","");
            ((Tangent) line).setCircle((Circle) context.stream()
                    .filter(e -> e instanceof Circle && e.getName().equals(circle))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.POLAR)) {
            line = new Polar();
            String circle = last(temp).replaceAll("\\)","");
            ((Polar) line).setCircle((Circle) context.stream()
                    .filter(e -> e instanceof Circle && e.getName().equals(circle))
                    .findFirst()
                    .orElseThrow());
        } else if (type.equals(LineType.UNSUPPORTED)) {
            return Optional.empty();
        }

        line.setName(last(TextParser.split(first(elements))));
        line.setEquation(last(splitByRegex(last(elements), ": ")));
        String A = last(splitByRegex(first(temp), "\\("));
        line.setA((Point) context.stream()
                .filter(e -> e instanceof Point && e.getName().equals(A))
                .findFirst()
                .orElseThrow());

        return Optional.of(line);
    }

    public LineType checkType(List<String> input, List<Figure> context) {
        if (input.get(0).contains("FitLine")) {
            return LineType.UNSUPPORTED;
        }
        String name = last(input).replaceAll("\\)","");
        Figure figure = context.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow();
        return first(input).contains("Line") &&
                figure instanceof Point ? LineType.SINGLELINE :
                first(input).contains("PerpendicularBisector") &&
                        figure instanceof Point ? LineType.PERPENDICULARBISECTORLINE :
                        first(input).contains("AngleBisector") &&
                                figure instanceof Point ? LineType.ANGLEBISECTORLINE :
                                first(input).contains("PerpendicularLine") &&
                                        figure instanceof Line ? LineType.PERPENDICULARLINE :
                                        first(input).contains("Tangent") &&
                                                figure instanceof Circle ? LineType.TANGENT :
                                                first(input).contains("Polar") &&
                                                        figure instanceof Circle ? LineType.POLAR:
                                                        first(input).contains("Line") ? LineType.PARALLELLINE : LineType.SINGLELINE;
    }

    public LineDescription createDescriptionFromLine(Line line) {
        return LineDescription
                .builder()
                .name(line.getName())
                .equation(line.getEquation())
                .type(line.getName().contains("Axis") ?
                        LineType.AXIS :
                        LineType.valueOf(line.getClass().getSimpleName().toUpperCase()))
                .build();
    }

}
