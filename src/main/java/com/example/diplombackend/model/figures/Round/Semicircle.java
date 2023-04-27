package com.example.diplombackend.model.figures.Round;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import static com.example.diplombackend.ListExtension.last;
import static com.example.diplombackend.service.TextParser.split;

@Data
public class Semicircle implements Figure {
    private String name;
    private double length;
    private Point start;
    private Point end;

    public double getRadius() {
        return length / 6.28;
    }

    @Override
    public String toString() {
        return "Semicircle(" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", start=" + start +
                ", end=" + end +
                ", radius=" + getRadius() +
                ')';
    }
}
