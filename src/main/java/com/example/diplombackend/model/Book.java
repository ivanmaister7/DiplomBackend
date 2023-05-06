package com.example.diplombackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long book_id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String author;

    @Column(nullable = false)
    public int classYear;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    public Collection<Task> tasks;

    @Override
    public String toString() {
        return name.equals("Own") ? name : name + ", " + classYear + " class, " + author;
    }
}
