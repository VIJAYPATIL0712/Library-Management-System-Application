package com.project.library_management.controller;

import com.project.library_management.entity.Book;
import com.project.library_management.entity.Student;
import com.project.library_management.repository.StudentRepository;
import com.project.library_management.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentService = studentService;

    }



    @GetMapping("/students")
    public String students(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "students/student_list";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model){
        Student student = new Student();
        model.addAttribute("student",student);
        return "students/add_student";

    }

    @PostMapping("/students/save_student")
    public String saveStudent(@ModelAttribute("student") Student student){
        studentService.saveStudent(student);

        return "redirect:/students";

    }


    @GetMapping("/students/edit/{id}")
    public String editBook(@PathVariable Long id , Model model){
        Student student = studentService.getStudentById(id);
        model.addAttribute("student",student);
        return "students/edit_student";

    }

    @PostMapping("/students/save_edit/{id}")
    public String saveEdit(@PathVariable Long id,@ModelAttribute("student") Student student){
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setContact(student.getContact());
        existingStudent.setCourse(student.getCourse());

        studentService.saveStudent(existingStudent);
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
