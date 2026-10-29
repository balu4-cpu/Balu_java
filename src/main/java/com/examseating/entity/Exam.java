package com.examseating.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Exam code is required")
    @Column(name = "exam_code", unique = true, nullable = false)
    private String examCode;

    @NotBlank(message = "Exam name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Subject is required")
    @Column(nullable = false)
    private String subject;

    @NotNull(message = "Date is required")
    @Column(name = "exam_date", nullable = false)
    private LocalDate date;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(nullable = false)
    private Integer duration;

    public Exam() {}

    public Exam(String examCode, String name, String subject, LocalDate date, Integer duration) {
        this.examCode = examCode;
        this.name = name;
        this.subject = subject;
        this.date = date;
        this.duration = duration;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExamCode() { return examCode; }
    public void setExamCode(String examCode) { this.examCode = examCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}
