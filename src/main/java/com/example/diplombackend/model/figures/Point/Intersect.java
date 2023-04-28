package com.example.diplombackend.model.figures.Point;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class Intersect extends Point implements Figure {
    private Line line1;
    private Line line2;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Point","Intersect") +
                ", line1=" + line1.getName() +
                ", line2=" + line2.getName() +
                ')';
    }
}
