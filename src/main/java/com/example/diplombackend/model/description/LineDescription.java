package com.example.diplombackend.model.description;

import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Point.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FigureDescription
@Component
public class LineDescription implements Description {
    private String name;
    private String equation;
    private LineType type;

    public LineDescription(LineDescription other) {
        this.name = other.name;
        this.equation = other.equation;
        this.type = other.type;
    }
}

