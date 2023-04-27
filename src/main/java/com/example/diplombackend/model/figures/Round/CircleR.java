package com.example.diplombackend.model.figures.Round;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class CircleR extends Circle implements Figure {
    private Point R;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Circle","CircleR") +
                ", R=" + R +
                ')';
    }
}
