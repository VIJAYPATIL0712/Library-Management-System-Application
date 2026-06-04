package com.project.library_management.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFound(
            BookNotFoundException ex,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "redirect:/books";
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public String handleStudentNotFound(
            StudentNotFoundException ex ,
            RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                ex.getMessage()
        );
        return "redirect:/students";
    }

    @ExceptionHandler(IssueRecordNotFoundException.class)
    public String handleIssueRecordNotFound(
            IssueRecordNotFoundException ex ,
            RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                ex.getMessage()
        );
        return "redirect:/issues";
    }

}
