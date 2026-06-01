package com.project.library_management.controller;

import com.project.library_management.entity.Book;
import com.project.library_management.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    //Books

    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("books",bookService.getAllBooks());
        return "books/book_list";
    }

    @GetMapping("/books/new")
    public String createBookForm(Model model) {

        Book book = new Book();

        model.addAttribute("book", book);

        return "books/add_book";
    }

    @PostMapping("/books/save_books")
    public String saveBook(@ModelAttribute("book") Book book){
        bookService.saveBook(book);
        return "redirect:/books";

    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable Long id , Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book",book);
        return "books/edit_book";

    }

    @PostMapping("/books/save_edit/{id}")
    public String saveEdit(@PathVariable Long id,@ModelAttribute("book") Book book){
        Book existingBook = bookService.getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPrice(book.getPrice());
        existingBook.setQuantity(book.getQuantity());

        bookService.saveBook(existingBook);
        return "redirect:/books";
    }


    @GetMapping("/books/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {

        bookService.deleteStudentById(id);

        return "redirect:/books";
    }

    @GetMapping("/books/search")
    public String bookSearch(@RequestParam("keyword") String keyword , Model model ){
        model.addAttribute("books" , bookService.searchBook(keyword));
        return "books/book_list";
    }

}
