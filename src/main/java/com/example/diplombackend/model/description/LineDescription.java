package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Line.LineType;
import com.example.diplombackend.model.figures.Point.PointType;
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
public class LineDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long lineDescription_id;
    public String name;
    public String equation;
    public LineType type;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public LineDescription(LineDescription other) {
        this.name = other.name;
        this.equation = other.equation;
        this.type = other.type;
    }
}

