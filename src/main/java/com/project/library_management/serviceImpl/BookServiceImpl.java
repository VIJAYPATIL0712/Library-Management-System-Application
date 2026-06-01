package com.project.library_management.serviceImpl;

import com.project.library_management.entity.Book;
import com.project.library_management.entity.IssueStatus;
import com.project.library_management.repository.BookRepository;
import com.project.library_management.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteStudentById(Long id) {
         bookRepository.deleteById(id);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Book Not Found"));
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public Long count() {
        return bookRepository.count();
    }



    @Override
    public Integer sumAvailableQuantity() {
        return bookRepository.sumQuantity();
    }
}
