package com.example.diplombackend.model.figures.Round;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import static com.example.diplombackend.ListExtension.*;
import static com.example.diplombackend.service.TextParser.split;

@Data
public class Circle implements Figure {
    private String name;
    private String equation;
    private Point center;

    public Double getRadius() {
        return Math.sqrt(Double.parseDouble(last(split(equation))));
    }

    @Override
    public String toString() {
        return "Circle(" +
                "name='" + name + '\'' +
                ", equation='" + equation + '\'' +
                ", center=" + center +
                ", radius=" + getRadius() +
                ')';
    }
}
