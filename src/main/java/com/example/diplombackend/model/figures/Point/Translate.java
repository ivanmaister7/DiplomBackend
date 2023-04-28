package com.example.diplombackend.model.figures.Point;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Line.Vector;
import lombok.Data;

@Data
public class Translate extends Point implements Figure {
    private Vector vector;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Point","Translate") +
                ", vector=" + vector.getName() +
                ')';
    }
}
