package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Line.LineType;
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
public class RayDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long rayDescription_id;
    public String name;
    public String equation;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public RayDescription(RayDescription other) {
        this.name = other.name;
        this.equation = other.equation;
    }
}

