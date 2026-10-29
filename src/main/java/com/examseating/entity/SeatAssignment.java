package com.examseating.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seat_assignments",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "exam_id"}),
        @UniqueConstraint(columnNames = {"exam_id", "room_id", "seat_row", "seat_number"})
    })
public class SeatAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "seat_row", nullable = false)
    private Integer rowNumber;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private Boolean published = false;

    public SeatAssignment() {}

    public SeatAssignment(Student student, Exam exam, Room room, Integer rowNumber, Integer seatNumber) {
        this.student = student;
        this.exam = exam;
        this.room = room;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.published = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }

    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }
}
