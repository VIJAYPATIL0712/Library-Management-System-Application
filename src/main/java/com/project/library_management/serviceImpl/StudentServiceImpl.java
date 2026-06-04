package com.project.library_management.serviceImpl;

import com.project.library_management.dto.student.StudentRequestDto;
import com.project.library_management.dto.student.StudentResponseDto;
import com.project.library_management.entity.Student;
import com.project.library_management.exception.StudentNotFoundException;
import com.project.library_management.repository.StudentRepository;
import com.project.library_management.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ---------------- MAPPERS ----------------

    private Student toEntity(StudentRequestDto dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setContact(dto.getContact());
        student.setCourse(dto.getCourse());
        return student;
    }

    private StudentResponseDto toDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setContact(student.getContact());
        dto.setCourse(student.getCourse());
        return dto;
    }


    @Override
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void saveStudent(StudentRequestDto studentDto) {
        Student student = toEntity(studentDto);
        studentRepository.save(student);
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found With Id : " + id));

        return toDto(student);
    }

    @Override
    public List<StudentResponseDto> searchStudent(String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return studentRepository.count();
    }

    @Override
    public void updateStudent(Long id, StudentRequestDto studentRequestDto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found With Id : " + id));

        existingStudent.setName(studentRequestDto.getName());
        existingStudent.setEmail(studentRequestDto.getEmail());
        existingStudent.setContact(studentRequestDto.getContact());
        existingStudent.setCourse(studentRequestDto.getCourse());
        studentRepository.save(existingStudent);
    }

    @Override
    public StudentResponseDto getStudentForId(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found With Id : " + id));

        return toDto(student);
    }
}
