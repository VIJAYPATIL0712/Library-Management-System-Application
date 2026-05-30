package com.project.library_management.repository;


import com.project.library_management.entity.Student;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByNameContainingIgnoreCase(String keyword);
}
