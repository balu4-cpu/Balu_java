package com.examseating.service;

import com.examseating.entity.*;
import com.examseating.repository.SeatAssignmentRepository;
import com.examseating.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class SeatingService {

    private final SeatAssignmentRepository seatAssignmentRepository;
    private final StudentRepository studentRepository;

    public SeatingService(SeatAssignmentRepository seatAssignmentRepository,
                          StudentRepository studentRepository) {
        this.seatAssignmentRepository = seatAssignmentRepository;
        this.studentRepository = studentRepository;
    }

    public List<SeatAssignment> findByExam(Exam exam) {
        return seatAssignmentRepository.findByExam(exam);
    }

    public List<SeatAssignment> findByExamId(Long examId) {
        return seatAssignmentRepository.findByExamId(examId);
    }

    public List<SeatAssignment> findByExamAndRoom(Exam exam, Room room) {
        return seatAssignmentRepository.findByExamAndRoom(exam, room);
    }

    public List<SeatAssignment> findPublishedByStudent(Student student) {
        return seatAssignmentRepository.findByStudentAndPublishedTrue(student);
    }

    public boolean existsByExam(Exam exam) {
        return seatAssignmentRepository.existsByExam(exam);
    }

    /**
     * Generate seat assignments for an exam across the selected rooms.
     * Students are assigned row by row across rooms.
     */
    public List<SeatAssignment> generateSeating(Exam exam, List<Room> rooms) {
        List<Student> students = studentRepository.findAll();
        List<SeatAssignment> assignments = new ArrayList<>();

        int studentIndex = 0;
        for (Room room : rooms) {
            for (int row = 1; row <= room.getRowCount() && studentIndex < students.size(); row++) {
                for (int seat = 1; seat <= room.getSeatsPerRow() && studentIndex < students.size(); seat++) {
                    SeatAssignment sa = new SeatAssignment(
                            students.get(studentIndex), exam, room, row, seat);
                    assignments.add(sa);
                    studentIndex++;
                }
            }
        }
        return assignments;
    }

    /**
     * Save all seat assignments (draft — not yet published).
     */
    public List<SeatAssignment> saveAll(List<SeatAssignment> assignments) {
        return seatAssignmentRepository.saveAll(assignments);
    }

    /**
     * Publish all seat assignments for an exam.
     */
    public void publishByExam(Exam exam) {
        List<SeatAssignment> assignments = seatAssignmentRepository.findByExam(exam);
        for (SeatAssignment sa : assignments) {
            sa.setPublished(true);
        }
        seatAssignmentRepository.saveAll(assignments);
    }

    /**
     * Delete all seat assignments for an exam.
     */
    public void deleteByExam(Exam exam) {
        seatAssignmentRepository.deleteByExam(exam);
    }

    public long countPublished() {
        return seatAssignmentRepository.countByPublishedTrue();
    }

    /**
     * Get distinct rooms that have seat assignments for an exam.
     */
    public List<Room> getAssignedRooms(Exam exam) {
        List<SeatAssignment> assignments = seatAssignmentRepository.findByExam(exam);
        Set<Room> rooms = new LinkedHashSet<>();
        for (SeatAssignment sa : assignments) {
            rooms.add(sa.getRoom());
        }
        return new ArrayList<>(rooms);
    }
}
