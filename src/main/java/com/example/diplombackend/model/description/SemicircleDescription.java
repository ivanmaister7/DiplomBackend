package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Segment.SegmentType;
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
public class SemicircleDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long semicircleDescription_id;
    public String name;
    public Double length;
    public Double radius;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public SemicircleDescription(SemicircleDescription other) {
        this.name = other.name;
        this.length = other.length;
        this.radius = other.radius;
    }
}

