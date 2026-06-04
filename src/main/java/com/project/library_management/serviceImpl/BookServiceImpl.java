package com.project.library_management.serviceImpl;

import com.project.library_management.dto.book.BookRequestDto;
import com.project.library_management.dto.book.BookResponseDto;
import com.project.library_management.entity.Book;
import com.project.library_management.entity.IssueStatus;
import com.project.library_management.exception.BookNotFoundException;
import com.project.library_management.repository.BookRepository;
import com.project.library_management.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    Mappers
//    DTO To ENTITY
    private Book toEntity(BookRequestDto dto){
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setQuantity(dto.getQuantity());
        return book;
    }

//    ENTITY TO DTO
    private BookResponseDto toDto(Book book){
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setQuantity(book.getQuantity());
        return dto;
    }


    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudentById(Long id) {
         bookRepository.deleteById(id);
    }

    @Override
    public void saveBook(BookRequestDto bookRequestDto) {
        Book book = toEntity(bookRequestDto);
        bookRepository.save(book);
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        Book book =  bookRepository.findById(id)
                .orElseThrow(()->new BookNotFoundException("Book Not Found Exception : " + id));
        return toDto(book);
    }

    @Override
    public List<BookResponseDto> searchBook(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this :: toDto)
                .collect(Collectors.toList());

    }

    @Override
    public Long count() {
        return bookRepository.count();
    }



    @Override
    public Integer sumAvailableQuantity() {
        return bookRepository.sumQuantity();
    }

    @Override
    public BookResponseDto getBookForId(Long id) {
       Book book =   bookRepository.findById(id)
                 .orElseThrow(()->new BookNotFoundException("Book Not Found Exception : " + id));
       return toDto(book);
    }

    @Override
    public void updateBook(Long id, BookRequestDto bookRequestDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(()->new BookNotFoundException("Book Not Found Exception : " + id));
        existingBook.setTitle(bookRequestDto.getTitle());
        existingBook.setAuthor(bookRequestDto.getAuthor());
        existingBook.setIsbn(bookRequestDto.getIsbn());
        existingBook.setPrice(bookRequestDto.getPrice());
        existingBook.setQuantity(bookRequestDto.getQuantity());
        bookRepository.save(existingBook);
    }
}
