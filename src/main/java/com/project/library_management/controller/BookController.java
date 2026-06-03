package com.project.library_management.controller;

import com.project.library_management.dto.book.BookRequestDto;
import com.project.library_management.dto.book.BookResponseDto;

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

    //Books

    //Fetch All Book
    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("books",bookService.getAllBooks());
        return "books/book_list";
    }

    //Add Book Form
    @GetMapping("/books/new")
    public String createBookForm(Model model) {
        BookRequestDto bookRequestDto = new BookRequestDto();
        model.addAttribute("book", bookRequestDto);
        return "books/add_book";
    }

    //Save Form When Hits Submit Button
    @PostMapping("/books/save_books")
    public String saveBook(@ModelAttribute("book") BookRequestDto bookRequestDto){
        bookService.saveBook(bookRequestDto);
        return "redirect:/books";

    }

    //Edit Form With Exisitng Data
    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable Long id , Model model){
        BookResponseDto bookResponseDto = bookService.getBookForId(id);
        model.addAttribute("book",bookResponseDto);
        return "books/edit_book";

    }

    //Update Data
    @PostMapping("/books/save_edit/{id}")
    public String saveEdit(@PathVariable Long id,@ModelAttribute("book") BookRequestDto bookRequestDto , Model model){
        bookService.updateBook(id,bookRequestDto);
        return "redirect:/books";
    }

    //Delete Data
    @GetMapping("/books/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        bookService.deleteStudentById(id);
        return "redirect:/books";
    }

//    Seaarches Data using keyword
    @GetMapping("/books/search")
    public String bookSearch(@RequestParam("keyword") String keyword , Model model ){
        model.addAttribute("books" , bookService.searchBook(keyword));
        return "books/book_list";
    }

}
