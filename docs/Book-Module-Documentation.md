# Book Module Documentation

## Overview

The Book Module is responsible for managing books in the Library Management System.

Features:

* Add Book
* View Books
* Edit Book
* Delete Book
* Search Books
* Manage Book Quantity

---

## Architecture

```text
UI
 ↓
Controller
 ↓
BookRequestDto
 ↓
Service
 ↓
Repository
 ↓
Book Entity
 ↓
Database
```

Response Flow:

```text
Database
 ↓
Book Entity
 ↓
Repository
 ↓
Service
 ↓
BookResponseDto
 ↓
Controller
 ↓
UI
```

---

## Entity

### Book

Represents the Book table in the database.

Fields:

* id
* title
* author
* isbn
* price
* quantity

---

## DTOs

### BookRequestDto

Used when data comes from forms.

Fields:

* title
* author
* isbn
* price
* quantity

Validation is applied in this DTO.

Example Uses:

* Add Book
* Edit Book

---

### BookResponseDto

Used when data is sent to the UI.

Fields:

* id
* title
* author
* isbn
* price
* quantity

Example Uses:

* Display All Books
* Search Books
* Edit Form

---

## Repository Layer

### BookRepository

Extends:

```java
JpaRepository<Book, Long>
```

Responsibilities:

* Save Book
* Update Book
* Delete Book
* Search Book
* Fetch Books

---

## Service Layer

### BookService

Responsibilities:

* Get All Books
* Save Book
* Update Book
* Delete Book
* Search Book
* Count Books

---

## DTO Mapping

### Request DTO → Entity

```text
BookRequestDto
        ↓
      Book
```

Purpose:

Convert user input into a database entity.

---

### Entity → Response DTO

```text
Book
 ↓
BookResponseDto
```

Purpose:

Convert database entity into UI-friendly data.

---

## Add Book Flow

```text
Book Form
 ↓
BookRequestDto
 ↓
Validation
 ↓
Service
 ↓
Book Entity
 ↓
Repository
 ↓
Database
```

---

## Edit Book Flow

```text
Book ID
 ↓
Repository
 ↓
Book Entity
 ↓
BookResponseDto
 ↓
Edit Form
```

Update:

```text
Form
 ↓
BookRequestDto
 ↓
Service
 ↓
Existing Book Entity
 ↓
Repository
 ↓
Database
```

---

## Delete Book Flow

```text
Book ID
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
Database
```

---

## Search Book Flow

```text
Keyword
 ↓
Repository Query
 ↓
Book Entity List
 ↓
BookResponseDto List
 ↓
UI
```

---

## Benefits of DTO Pattern

* Better Security
* Cleaner Architecture
* Easier Validation
* Reduced Entity Exposure
* Industry Standard Design
