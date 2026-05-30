package com.project.library_management.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class IssueRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Book book;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private boolean returned;
}
