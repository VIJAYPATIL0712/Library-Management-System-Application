package com.project.library_management.controller;

import com.project.library_management.dto.student.StudentRequestDto;
import com.project.library_management.dto.student.StudentResponseDto;

import com.project.library_management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController( StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public String students(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "students/student_list";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model){
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        model.addAttribute("student",studentRequestDto);
        return "students/add_student";

    }

    @PostMapping("/students/save_student")
    public String saveStudent(@Valid @ModelAttribute("student") StudentRequestDto studentRequestDto,
                              BindingResult result,
                              Model model) {

        if (result.hasErrors()) {
            return "students/add_student";
        }

        studentService.saveStudent(studentRequestDto);
        return "redirect:/students";
    }


    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id , Model model){
        StudentResponseDto studentResponseDto = studentService.getStudentForId(id);
        model.addAttribute("student",studentResponseDto);
        return "students/edit_student";

    }

    @PostMapping("/students/save_edit/{id}")
    public String saveEdit(@PathVariable Long id,
                           @Valid @ModelAttribute("student") StudentRequestDto studentRequestDto,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            return "students/edit_student";
        }

        studentService.updateStudent(id, studentRequestDto);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentService.deleteStudentById(id);

        return "redirect:/students";
    }

    @GetMapping("/students/search")
    public String searchStudent(@RequestParam("keyword") String keyword , Model model){
        model.addAttribute("students" , studentService.searchStudent(keyword));
        return "students/student_list";
    }
}
