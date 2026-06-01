package com.project.library_management.controller;

import com.project.library_management.entity.IssueStatus;
import com.project.library_management.service.BookService;
import com.project.library_management.service.IssueRecordService;
import com.project.library_management.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {
    private final BookService bookService;
    private final StudentService studentService;
    private final IssueRecordService issueRecordService;

    public HomeController(BookService bookService, StudentService studentService, IssueRecordService issueRecordService) {
        this.bookService = bookService;
        this.studentService = studentService;
        this.issueRecordService = issueRecordService;
    }

    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("totalStudents", studentService.count());
        model.addAttribute("totalIssuedBooks", issueRecordService.countByStatus(IssueStatus.ISSUED));
        model.addAttribute("totalReturnedBooks", issueRecordService.countByStatus1(IssueStatus.RETURNED));
        model.addAttribute("availableBooks",bookService.sumAvailableQuantity());
        return "index";
    }
}
