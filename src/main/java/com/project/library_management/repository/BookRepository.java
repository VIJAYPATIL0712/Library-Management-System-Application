package com.project.library_management.repository;

import com.project.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {



    List<Book> findByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT COALESCE(SUM(b.quantity),0) FROM Book b")
    Integer sumQuantity();

}
