package com.project.library_management.service;

import com.project.library_management.dto.book.BookRequestDto;
import com.project.library_management.dto.book.BookResponseDto;
import com.project.library_management.entity.Book;
import com.project.library_management.entity.IssueStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookResponseDto> getAllBooks();

    void deleteStudentById(Long id);

    void saveBook(BookRequestDto bookRequestDto);

   BookResponseDto getBookById(Long id);

    List<BookResponseDto> searchBook(String keyword);

    Long count();


    Integer sumAvailableQuantity();

    BookResponseDto getBookForId(Long id);

    void updateBook(Long id, BookRequestDto bookRequestDto);
}
