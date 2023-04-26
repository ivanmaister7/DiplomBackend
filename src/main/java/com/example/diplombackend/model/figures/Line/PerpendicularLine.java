package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class PerpendicularLine extends Line implements Figure {
    private Line base;

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", base=" + base +
                ')';
    }
}
