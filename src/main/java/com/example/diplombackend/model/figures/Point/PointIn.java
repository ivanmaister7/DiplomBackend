package com.example.diplombackend.model.figures.Point;

import com.example.diplombackend.model.figures.Figure;
import lombok.Data;

@Data
public class PointIn extends Point {
    private Figure owner;

    @Override
    public String toString() {
        return super.toString()
                .substring(0, super.toString().length() - 1)
                .replaceAll("Point","PointIn") +
                ", owner=" + owner +
                ')';
    }
}
