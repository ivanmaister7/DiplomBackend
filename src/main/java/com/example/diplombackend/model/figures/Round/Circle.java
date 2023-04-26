package com.example.diplombackend.model.figures.Round;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Point.Point;
import lombok.Data;

@Data
public class Circle implements Figure {
    private String name;
    private String equation;
    private Point center;
    private Point R;
}
