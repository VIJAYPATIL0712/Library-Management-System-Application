package com.project.library_management.controller;

import com.project.library_management.dto.issue.IssueRecordRequestDto;
import com.project.library_management.entity.IssueStatus;
import com.project.library_management.service.BookService;
import com.project.library_management.service.IssueRecordService;
import com.project.library_management.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class IssueRecordController {
    private final StudentService studentService;
    private final BookService bookService;
    private final IssueRecordService issueRecordService;

    public IssueRecordController(StudentService studentService, BookService bookService, IssueRecordService issueRecordService) {
        this.studentService = studentService;
        this.bookService = bookService;
        this.issueRecordService = issueRecordService;
    }

    @GetMapping("/issues")
    public String issue(Model model) {

        model.addAttribute("issueRecords", issueRecordService.findIssuedBook());
        return "issues/issueRecord";

    }

    @GetMapping("/issues/new")
    public String issueBookForm(Model model) {

        model.addAttribute("issue", new IssueRecordRequestDto());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("books", bookService.getAllBooks());
        return "issues/IssueBook";
    }


    @PostMapping("/issues/issue_book")
    public String saveIssueBook(@ModelAttribute("issue") IssueRecordRequestDto issueRecordRequestDto) {
        issueRecordService.save(issueRecordRequestDto);
        return "redirect:/issues";

    }

    @GetMapping("/returns")
    public String returns(Model model) {
        model.addAttribute("returns", issueRecordService.findReturnedBook());
        return "returns/returnRecord";
    }


    @GetMapping("/issues/return/{id}")
    public String returnStatus(@PathVariable Long id) {
        issueRecordService.updateStatus(id, IssueStatus.RETURNED);
        return "redirect:/returns";
    }

    @GetMapping("/issues/lost/{id}")
    public String lostStatus(@PathVariable Long id) {
        issueRecordService.updateStatus(id, IssueStatus.LOST);
        return "redirect:/returns";
    }

    @GetMapping("/issues/damaged/{id}")
    public String damagedStatus(@PathVariable Long id) {
        issueRecordService.updateStatus(id, IssueStatus.DAMAGED);
        return "redirect:/returns";
    }

    @GetMapping("/issues/search")
    public String issuesSearch(
            @RequestParam("keyword") String keyword,
             Model model
    ) {
        model.addAttribute("issueRecords", issueRecordService.searchIssuedByStudentName(keyword));
        return "issues/issueRecord";
    }

    @GetMapping("/returns/search")
    public String returnsSearch(
            @RequestParam("keyword") String keyword,
            Model model
    ) {
        model.addAttribute("returns", issueRecordService.searchNotIssuedByStudentName(keyword));
        return "returns/returnRecord";
    }

}
