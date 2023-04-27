package com.example.diplombackend.model.figures.Angle;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class Angle implements Figure {
    private String name;
    private double angle;
    private Point A;
    private Point B;
    private Point C;
}
