package com.examseating.repository;

import com.examseating.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByExamCode(String examCode);
    boolean existsByExamCode(String examCode);
}
