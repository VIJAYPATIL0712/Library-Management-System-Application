package com.project.library_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name cannot be empty") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be empty") String name) {
        this.name = name;
    }

    public @Email(message = "Invalid email") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Course cannot be empty") String getCourse() {
        return course;
    }

    public void setCourse(@NotBlank(message = "Course cannot be empty") String course) {
        this.course = course;
    }

    public @NotBlank(message = "Contact cannot be empty") String getContact() {
        return contact;
    }

    public void setContact(@NotBlank(message = "Contact cannot be empty") String contact) {
        this.contact = contact;
    }

    public Student() {
    }

    public Student(Long id, String name, String email, String course, String contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.contact = contact;
    }

    @NotBlank(message = "Course cannot be empty")
    private String course;

    @NotBlank(message = "Contact cannot be empty")
    private String contact;
}
