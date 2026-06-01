package com.project.library_management.service;

import com.project.library_management.entity.Book;
import com.project.library_management.entity.IssueStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();

    void deleteStudentById(Long id);

    void saveBook(Book book);

   Book getBookById(Long id);

    List<Book> searchBook(String keyword);

    Long count();


    Integer sumAvailableQuantity();
}
