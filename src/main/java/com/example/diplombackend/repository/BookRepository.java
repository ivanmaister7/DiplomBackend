package com.example.diplombackend.repository;

import com.example.diplombackend.model.Book;
import com.example.diplombackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByName(String name);
}
