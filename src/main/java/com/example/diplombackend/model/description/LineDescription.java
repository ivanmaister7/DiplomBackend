package com.example.diplombackend.model.description;

import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Point.PointType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LineDescription implements Description {
    private String name;
    private String equation;
    private LineType type;
}
