package com.example.diplombackend.model.description;

import com.example.diplombackend.model.Task;
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
public class PointDescription implements Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long pointDescription_id;
    public String name;
    public PointType type;
    public Double coordinate1;
    public Double coordinate2;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    public Task task;

    public PointDescription(PointDescription other) {
        this.name = other.name;
        this.type = other.type;
        this.coordinate1 = other.coordinate1;
        this.coordinate2 = other.coordinate2;
    }
}

