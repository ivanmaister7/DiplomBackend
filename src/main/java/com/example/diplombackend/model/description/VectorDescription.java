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
public class VectorDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long vectorDescription_id;
    public String name;
    public String equation;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public VectorDescription(VectorDescription other) {
        this.name = other.name;
        this.equation = other.equation;
    }
}

