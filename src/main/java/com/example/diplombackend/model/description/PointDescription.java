package com.example.diplombackend.model.description;

import com.example.diplombackend.model.figures.Point.PointType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointDescription implements Description{
    private String name;
    private PointType type;
    private Double coordinate1;
    private Double coordinate2;
}
