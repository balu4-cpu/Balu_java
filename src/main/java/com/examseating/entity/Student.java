package com.examseating.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Roll number is required")
    @Column(name = "roll_number", unique = true, nullable = false)
    private String rollNumber;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @Column
    private String email;

    @NotBlank(message = "Course is required")
    @Column(nullable = false)
    private String course;

    @NotNull(message = "Year is required")
    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 6, message = "Year must be at most 6")
    @Column(nullable = false)
    private Integer year;

    public Student() {}

    public Student(String rollNumber, String name, String email, String course, Integer year) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.course = course;
        this.year = year;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}
