package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.example.diplombackend.ListExtension.first;
import static com.example.diplombackend.ListExtension.last;

@Data
public class Polyline implements Figure {
    private String name;
    private Double length;
    private List<Point> points = new ArrayList<>();

    public Point getStartPoint() {
        return first(points);
    }
    public Point getEndPoint() {
        return last(points);
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }
}
