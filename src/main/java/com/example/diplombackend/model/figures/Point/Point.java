package com.example.diplombackend.model.figures.Point;

import com.example.diplombackend.model.figures.Figure;
import lombok.Data;

@Data
public class Point implements Figure {
    private String name;
    private double coordinate1;
    private double coordinate2;
}
