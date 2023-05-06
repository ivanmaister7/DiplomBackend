package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Round.CircleType;
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
public class CircleDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long circleDescription_id;
    public String name;
    public String equation;
    public Double radius;
    public CircleType type;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public CircleDescription(CircleDescription other) {
        this.name = other.name;
        this.equation = other.equation;
        this.type = other.type;
        this.radius = other.radius;
    }
}

