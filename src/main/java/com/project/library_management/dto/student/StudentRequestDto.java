package com.project.library_management.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentRequestDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Contact cannot be empty")
    private String contact;

    @NotBlank(message = "Course cannot be empty")
    private String course;

    // getters and setters (NO annotations here)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}