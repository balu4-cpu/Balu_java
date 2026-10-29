package com.examseating.service;

import com.examseating.entity.Student;
import com.examseating.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean existsByRollNumber(String rollNumber) {
        return studentRepository.existsByRollNumber(rollNumber);
    }

    public long count() {
        return studentRepository.count();
    }
}
