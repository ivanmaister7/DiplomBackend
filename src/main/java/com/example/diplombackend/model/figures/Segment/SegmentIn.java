package com.example.diplombackend.model.figures.Segment;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Polygon.Polygon;
import lombok.Data;

@Data
public class SegmentIn extends Segment implements Figure {
    private Polygon base;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Segment","SegmentIn") +
                ", base=" + base.getName() +
                ')';
    }
}
