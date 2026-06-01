package com.project.library_management.controller;

import com.project.library_management.service.IssueRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    public IssueRecordController(IssueRecordService issueRecordService) {
        this.issueRecordService = issueRecordService;
    }

    @GetMapping("/issues")
    public String issue(Model model){
        System.out.print("Done");
        model.addAttribute("issueRecords",issueRecordService.findIssuedBook());
        return "issues/issueRecord";

    }
}
