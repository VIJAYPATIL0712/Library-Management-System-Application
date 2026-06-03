# Issue Record Module Documentation

## Overview

The Issue Record Module manages book issuance and returns.

Features:

* Issue Book
* Return Book
* Mark Book as Lost
* Mark Book as Damaged
* View Issued Books
* View Returned Books
* Search Records
* Update Book Quantity Automatically

---

## Architecture

Request Flow:

```text
UI
 ↓
IssueRecordRequestDto
 ↓
Controller
 ↓
Service
 ↓
IssueRecord Entity
 ↓
Repository
 ↓
Database
```

Response Flow:

```text
Database
 ↓
IssueRecord Entity
 ↓
Repository
 ↓
Service
 ↓
IssueRecordResponseDto
 ↓
Controller
 ↓
UI
```

---

## Entity

### IssueRecord

Represents a book issue transaction.

Fields:

* id
* student
* book
* issueDate
* dueDate
* returnDate
* status

Relationships:

```java
@ManyToOne
private Student student;

@ManyToOne
private Book book;
```

---

## IssueStatus Enum

Possible values:

```java
ISSUED
RETURNED
LOST
DAMAGED
OVERDUE
```

Stored as String using:

```java
@Enumerated(EnumType.STRING)
```

---

## DTOs

### IssueRecordRequestDto

Used when issuing a book.

Fields:

* studentId
* bookId
* issueDate
* dueDate

Purpose:

Receive data from the issue form.

---

### IssueRecordResponseDto

Used when displaying issue records.

Fields:

* id
* studentName
* bookTitle
* issueDate
* dueDate
* returnDate
* status

Purpose:

Show readable information to the UI.

---

## DTO Mapping

### Request DTO → Entity

```text
studentId
bookId
     ↓
Fetch Student Entity
Fetch Book Entity
     ↓
IssueRecord Entity
```

Purpose:

Create an IssueRecord using existing Student and Book entities.

---

### Entity → Response DTO

```text
IssueRecord
     ↓
IssueRecordResponseDto
```

Conversions:

```text
Student → studentName
Book → bookTitle
```

---

## Issue Book Flow

```text
Issue Form
 ↓
IssueRecordRequestDto
 ↓
Service
 ↓
Find Student
 ↓
Find Book
 ↓
Reduce Quantity
 ↓
Create IssueRecord
 ↓
Repository
 ↓
Database
```

Status Assigned:

```java
IssueStatus.ISSUED
```

Due Date:

```java
LocalDate.now().plusDays(7)
```

---

## Return Book Flow

```text
Return Button
 ↓
Controller
 ↓
updateStatus()
 ↓
IssueRecord
 ↓
Status = RETURNED
 ↓
Increase Book Quantity
 ↓
Database
```

---

## Lost Book Flow

```text
Lost Button
 ↓
Status = LOST
 ↓
No Quantity Increment
```

---

## Damaged Book Flow

```text
Damaged Button
 ↓
Status = DAMAGED
 ↓
No Quantity Increment
```

---

## View Issued Books

Query:

```java
findByStatus(IssueStatus.ISSUED)
```

Flow:

```text
Repository
 ↓
IssueRecord List
 ↓
Response DTO List
 ↓
UI
```

---

## View Returned Books

Query:

```java
findByStatusNot(IssueStatus.ISSUED)
```

Displays:

* Returned
* Lost
* Damaged

Records.

---

## Search Issued Records

Search by Student Name.

Flow:

```text
Keyword
 ↓
Repository Query
 ↓
IssueRecord List
 ↓
Response DTO List
 ↓
UI
```

---

## Quantity Management Logic

### Issue Book

```text
quantity = quantity - 1
```

### Return Book

```text
quantity = quantity + 1
```

### Lost Book

```text
No increment
```

### Damaged Book

```text
No increment
```

---

## Benefits of DTO Pattern

* Decouples Entity from UI
* Cleaner Controller Layer
* Better Security
* Easier Maintenance
* Industry Standard Architecture

---

## Summary

The Issue Record Module manages the complete lifecycle of a library transaction:

```text
Issue
 ↓
Issued
 ↓
Returned / Lost / Damaged
```

while automatically maintaining book inventory and using DTOs for data transfer between layers.
