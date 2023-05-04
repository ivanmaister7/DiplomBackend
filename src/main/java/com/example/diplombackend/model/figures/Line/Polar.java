package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Round.Circle;
import lombok.Data;

@Data
public class Polar extends Line implements Figure {
    private Circle circle;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Line","Polar") +
                ", circle=" + circle +
                ')';
    }
}
