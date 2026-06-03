# Student Module Documentation

## Overview

The Student Module is responsible for managing student records in the Library Management System. It follows a layered architecture using:

* Controller Layer
* Service Layer
* Repository Layer
* Entity Layer
* DTO Layer

The module supports:

* View all students
* Add a student
* Edit a student
* Delete a student
* Search students
* Validation of student data

---

# Project Architecture

```text
Browser (Thymeleaf)
       |
       v
Controller
       |
       v
Request DTO
       |
       v
Service
       |
       v
Repository
       |
       v
Entity
       |
       v
Database
```

Response Flow:

```text
Database
   |
Entity
   |
Repository
   |
Service
   |
Response DTO
   |
Controller
   |
View
   |
Browser
```

---

# Components

## 1. Student Entity

Location:

```text
entity/Student.java
```

Purpose:

Represents the Student table in the database.

Fields:

| Field   | Type   |
| ------- | ------ |
| id      | Long   |
| name    | String |
| email   | String |
| course  | String |
| contact | String |

Example:

```java
@Entity
public class Student {
    private Long id;
    private String name;
    private String email;
    private String course;
    private String contact;
}
```

---

## 2. StudentRequestDto

Location:

```text
dto/student/StudentRequestDto.java
```

Purpose:

Used when data is received from forms.

Examples:

* Add Student
* Edit Student

Fields:

| Field   | Validation        |
| ------- | ----------------- |
| name    | @NotBlank         |
| email   | @Email, @NotBlank |
| course  | @NotBlank         |
| contact | @NotBlank         |

Example:

```java
public class StudentRequestDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private String course;
    private String contact;
}
```

---

## 3. StudentResponseDto

Location:

```text
dto/student/StudentResponseDto.java
```

Purpose:

Used to send student data to the UI.

Fields:

| Field   |
| ------- |
| id      |
| name    |
| email   |
| course  |
| contact |

Example:

```java
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;
    private String course;
    private String contact;
}
```

---

## 4. Repository Layer

Location:

```text
repository/StudentRepository.java
```

Purpose:

Provides database access.

Extends:

```java
JpaRepository<Student, Long>
```

Custom Method:

```java
List<Student> findByNameContainingIgnoreCase(String keyword);
```

Generated SQL:

```sql
SELECT *
FROM student
WHERE LOWER(name)
LIKE '%keyword%';
```

---

## 5. Service Layer

Location:

```text
service/StudentService.java
```

Purpose:

Contains business operations.

Methods:

```java
List<StudentResponseDto> getAllStudents();

void saveStudent(StudentRequestDto dto);

void updateStudent(Long id, StudentRequestDto dto);

void deleteStudentById(Long id);

StudentResponseDto getStudentById(Long id);

StudentResponseDto getStudentForId(Long id);

List<StudentResponseDto> searchStudent(String keyword);

Long count();
```

---

## 6. Service Implementation

Location:

```text
serviceImpl/StudentServiceImpl.java
```

Purpose:

Implements business logic and DTO mapping.

### DTO to Entity Mapping

```java
private Student toEntity(StudentRequestDto dto)
```

Converts:

```text
StudentRequestDto
       ↓
Student Entity
```

Used in:

* Save Student

---

### Entity to DTO Mapping

```java
private StudentResponseDto toDto(Student student)
```

Converts:

```text
Student Entity
       ↓
StudentResponseDto
```

Used in:

* List Students
* Search Students
* Edit Student
* View Student

---

# Controller Layer

Location:

```text
controller/StudentController.java
```

Purpose:

Handles HTTP requests and responses.

---

## View All Students

URL:

```text
GET /students
```

Flow:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
    ↓
Entity List
    ↓
Response DTO List
    ↓
View
```

Returns:

```text
students/student_list.html
```

---

## Add Student Form

URL:

```text
GET /students/new
```

Creates:

```java
new StudentRequestDto()
```

Returns:

```text
students/add_student.html
```

---

## Save Student

URL:

```text
POST /students/save_student
```

Flow:

```text
Form Data
    ↓
StudentRequestDto
    ↓
Validation
    ↓
Service
    ↓
Entity
    ↓
Repository
    ↓
Database
```

Validation:

```java
@Valid
```

Error Handling:

```java
if(result.hasErrors())
```

Returns:

```text
students/add_student
```

Success:

```text
redirect:/students
```

---

## Edit Student Form

URL:

```text
GET /students/edit/{id}
```

Flow:

```text
ID
 ↓
Repository
 ↓
Entity
 ↓
Response DTO
 ↓
Form
```

Returns:

```text
students/edit_student.html
```

---

## Update Student

URL:

```text
POST /students/save_edit/{id}
```

Flow:

```text
Form
 ↓
Request DTO
 ↓
Validation
 ↓
Existing Entity
 ↓
Update Fields
 ↓
Save
 ↓
Database
```

Success:

```text
redirect:/students
```

---

## Delete Student

URL:

```text
GET /students/delete/{id}
```

Flow:

```text
Controller
 ↓
Service
 ↓
Repository
 ↓
Database
```

Generated SQL:

```sql
DELETE FROM student
WHERE id = ?;
```

Success:

```text
redirect:/students
```

---

## Search Student

URL:

```text
GET /students/search?keyword=value
```

Flow:

```text
Keyword
 ↓
Repository Search
 ↓
Entity List
 ↓
Response DTO List
 ↓
View
```

Returns:

```text
students/student_list.html
```

---

# Validation

Validation is applied in StudentRequestDto.

Example:

```java
@NotBlank(message = "Name cannot be empty")
private String name;

@Email(message = "Invalid email")
@NotBlank(message = "Email cannot be empty")
private String email;
```

Validation is triggered using:

```java
@Valid
```

Controller Example:

```java
@PostMapping("/students/save_student")
public String saveStudent(
        @Valid @ModelAttribute("student")
        StudentRequestDto dto,
        BindingResult result) {
}
```

---

# Why DTO Pattern Is Used

Advantages:

1. Separates API/UI models from database entities.
2. Improves security by exposing only required fields.
3. Simplifies validation.
4. Reduces coupling between UI and database.
5. Follows industry-standard Spring Boot architecture.
6. Easier maintenance and future scalability.

Example:

Instead of exposing:

```java
Student
{
    id,
    name,
    email,
    password,
    aadhaarNumber
}
```

We expose only:

```java
StudentResponseDto
{
    id,
    name,
    email
}
```

---

# Summary

The Student Module follows a clean layered architecture using DTOs.

Request Flow:

```text
UI
 ↓
Controller
 ↓
StudentRequestDto
 ↓
Service
 ↓
Repository
 ↓
Entity
 ↓
Database
```

Response Flow: 

```text
Database
 ↓
Entity
 ↓
Repository
 ↓
Service
 ↓
StudentResponseDto
 ↓
Controller
 ↓
UI
```

This architecture is scalable, maintainable, secure, and follows Spring Boot industry best practices.
