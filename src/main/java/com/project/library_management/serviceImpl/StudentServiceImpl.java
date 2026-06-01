package com.project.library_management.serviceImpl;

import com.project.library_management.entity.Student;
import com.project.library_management.repository.StudentRepository;
import com.project.library_management.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Student Not Found"));
    }

    @Override
    public List<Student> searchStudent(String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Long count() {
        return studentRepository.count();
    }
}
