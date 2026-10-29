package com.examseating.repository;

import com.examseating.entity.Exam;
import com.examseating.entity.Room;
import com.examseating.entity.SeatAssignment;
import com.examseating.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatAssignmentRepository extends JpaRepository<SeatAssignment, Long> {
    List<SeatAssignment> findByExam(Exam exam);
    List<SeatAssignment> findByExamAndRoom(Exam exam, Room room);
    List<SeatAssignment> findByExamId(Long examId);
    List<SeatAssignment> findByStudentAndPublishedTrue(Student student);
    List<SeatAssignment> findByStudent(Student student);
    boolean existsByExam(Exam exam);
    void deleteByExam(Exam exam);
    long countByPublishedTrue();
}
