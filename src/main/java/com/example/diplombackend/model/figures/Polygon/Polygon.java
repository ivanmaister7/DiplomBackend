package com.example.diplombackend.model.figures.Polygon;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Polygon implements Figure {
    private String name;
    private Double square;
    private int sides;
    private List<Point> points = new ArrayList<>();
    public int getSides() {
        return getPoints().size();
    }
    public PolygonType getType() {
        return PolygonType.valueOfSides(getPoints().size());
    }
    public void addPoint(Point point) {
        this.points.add(point);
        this.points = this.points.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Polygon(" +
                "name='" + name + '\'' +
                ", square=" + square +
                ", sides=" + getSides() +
                ", points=" + points +
                ", type=" + getType() +
                ')';
    }
}
