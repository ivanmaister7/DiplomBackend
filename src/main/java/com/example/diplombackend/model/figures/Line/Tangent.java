package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import lombok.Data;

@Data
public class Tangent extends Line implements Figure {
//    private Circle circle; TODO: return to circle after implementation
    private Figure circle;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Line","Tangent") +
                ", circle=" + circle +
                ')';
    }
}
