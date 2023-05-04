package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SingleLine extends Line implements Figure {
    private Point B;

    public SingleLine(String xAxis, String s, Point point, Point point1) {
        super(xAxis,s,point);
        this.B = point1;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length() - 1) +
                ", B=" + B +
                ')';
    }
}
