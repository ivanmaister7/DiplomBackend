package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Polygon.PolygonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FigureDescription
@Component
@Entity
public class PolygonDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long polygonDescription_id;
    public String name;
    public Double square;
    public PolygonType type;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public PolygonDescription(PolygonDescription other) {
        this.name = other.name;
        this.square = other.square;
        this.type = other.type;
    }
}

