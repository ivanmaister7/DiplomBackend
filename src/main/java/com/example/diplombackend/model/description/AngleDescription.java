package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
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
public class AngleDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long angleDescription_id;
    public String name;
    public Double angle;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public AngleDescription(AngleDescription other) {
        this.name = other.name;
        this.angle = other.angle;
    }
}

