package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class AngleBisectorLine extends Line implements Figure {
    private Point B;
    private Point C;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Line", "AngleBisectorLine") +
                ", B=" + B +
                ", C=" + C +
                ')';
    }
}
