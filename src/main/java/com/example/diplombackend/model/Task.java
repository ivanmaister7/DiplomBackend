package com.example.diplombackend.model;

import com.example.diplombackend.model.description.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long task_id;

    @Column(nullable = false)
    public String question;

//    @Column(nullable = false)
//    public Boolean isDone;

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<PointDescription> descriptions = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<LineDescription> descriptions2 = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<SegmentDescription> descriptions3 = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<RayDescription> descriptions4 = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public List<PolylineDescription> descriptions5 = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    public Collection<UserTask> users;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @JsonIgnore
    public List<Description> getAllDescription() {
        ArrayList<Description> d = new ArrayList<>();
        d.addAll(descriptions);
        d.addAll(descriptions2);
        d.addAll(descriptions3);
        d.addAll(descriptions4);
        d.addAll(descriptions5);
        return d;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", question='" + question + '\'' +
                '}';
    }
}
