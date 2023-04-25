package com.example.diplombackend.model.figures.Line;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class Segment implements Figure {
    private String name;
    private Double length;
    private Point A;
    private Point B;
}
