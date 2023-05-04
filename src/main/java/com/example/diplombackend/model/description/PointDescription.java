package com.example.diplombackend.model.description;

import com.example.diplombackend.model.figures.Point.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDescription implements Description {
    private String name;
    private PointType type;
    private Double coordinate1;
    private Double coordinate2;

    public PointDescription(PointDescription other) {
        this.name = other.name;
        this.type = other.type;
        this.coordinate1 = other.coordinate1;
        this.coordinate2 = other.coordinate2;
    }
}

