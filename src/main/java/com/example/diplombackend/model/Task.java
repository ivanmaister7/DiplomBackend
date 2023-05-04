package com.example.diplombackend.model;

import com.example.diplombackend.model.description.Description;
import com.example.diplombackend.model.description.LineDescription;
import com.example.diplombackend.model.description.PointDescription;
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
    public Collection<UserTask> users;

    @JsonIgnore
    public List<Description> getAllDescription() {
        ArrayList<Description> d = new ArrayList<>();
        d.addAll(descriptions);
        d.addAll(descriptions2);
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
