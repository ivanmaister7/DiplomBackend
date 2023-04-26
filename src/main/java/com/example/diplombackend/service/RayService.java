package com.example.diplombackend.service;

import com.example.diplombackend.model.figures.Figure;
import com.example.diplombackend.model.figures.Line.Line;
import com.example.diplombackend.model.figures.Line.Ray;
import com.example.diplombackend.model.figures.Line.SingleLine;
import com.example.diplombackend.model.figures.Point.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.diplombackend.ListExtension.first;
import static com.example.diplombackend.ListExtension.last;
import static com.example.diplombackend.service.TextParser.splitByRegex;

@Service
public class RayService {
    @Autowired
    LineService lineService;
    public Ray createRayFrom(String input, List<Figure> context) {
        SingleLine ray = (SingleLine) lineService.createLineFrom(input, context);
        return new Ray(ray);
    }
}
