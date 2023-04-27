package com.example.diplombackend.model.figures.Round;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CircleByPoints extends Circle implements Figure {
    private List<Point> points = new ArrayList<>();
    public void addPoint(Point point) {
        this.points.add(point);
    }

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Circle","CircleByPoints") +
                ", points=" + points +
                ')';
    }
}
