package com.project.library_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public Book() {
    }

    public Book(Long id, String title, String author, Double price, Integer quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title cannot be empty") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be empty") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Author cannot be empty") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author cannot be empty") String author) {
        this.author = author;
    }

    public @NotBlank(message = "ISBN cannot be empty") String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotBlank(message = "ISBN cannot be empty") String isbn) {
        this.isbn = isbn;
    }

    public @NotNull(message = "Price cannot be empty") @Positive(message = "Price must be greater than 0") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price cannot be empty") @Positive(message = "Price must be greater than 0") Double price) {
        this.price = price;
    }

    public @NotNull(message = "Quantity cannot be empty") @Min(value = 1, message = "Quantity must be at least 1") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull(message = "Quantity cannot be empty") @Min(value = 1, message = "Quantity must be at least 1") Integer quantity) {
        this.quantity = quantity;
    }
}
