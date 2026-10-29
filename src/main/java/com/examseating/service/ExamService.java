package com.examseating.service;

import com.examseating.entity.Exam;
import com.examseating.repository.ExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    public Optional<Exam> findById(Long id) {
        return examRepository.findById(id);
    }

    public Optional<Exam> findByExamCode(String examCode) {
        return examRepository.findByExamCode(examCode);
    }

    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    public void deleteById(Long id) {
        examRepository.deleteById(id);
    }

    public boolean existsByExamCode(String examCode) {
        return examRepository.existsByExamCode(examCode);
    }

    public long count() {
        return examRepository.count();
    }
}
