package com.project.library_management.service;

import com.project.library_management.dto.student.StudentRequestDto;
import com.project.library_management.dto.student.StudentResponseDto;

import java.util.List;

public interface StudentService
{
    List<StudentResponseDto> getAllStudents();

    void deleteStudentById(Long id);

    void saveStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto getStudentById(Long id);

    List<StudentResponseDto> searchStudent(String keyword);

    Long count();

    void updateStudent(Long id, StudentRequestDto studentRequestDto);

    StudentResponseDto getStudentForId(Long id);
}
