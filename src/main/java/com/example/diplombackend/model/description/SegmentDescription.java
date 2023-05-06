package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
import com.example.diplombackend.model.figures.Line.LineType;
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
public class SegmentDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long segmentDescription_id;
    public String name;
    public Double length;
    public SegmentType type;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public SegmentDescription(SegmentDescription other) {
        this.name = other.name;
        this.length = other.length;
        this.type = other.type;
    }
}

