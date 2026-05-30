package com.project.library_management.service;

import com.project.library_management.entity.Student;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface StudentService
{
    List<Student> getAllStudents();

    void deleteStudentById(Long id);

    void saveStudent(Student student);

    Student getStudentById(Long id);

    List<Student> searchStudent(String keyword);
}
