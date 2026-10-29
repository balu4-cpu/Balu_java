package com.examseating;

import com.examseating.entity.Exam;
import com.examseating.service.ExamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ExamServiceTest {

    @Autowired
    private ExamService examService;

    @Test
    void testCreateExam() {
        Exam exam = new Exam("TEST_EX01", "Test Exam", "Testing", LocalDate.of(2025, 6, 15), 120);
        Exam saved = examService.save(exam);
        assertNotNull(saved.getId());
        assertEquals("TEST_EX01", saved.getExamCode());
    }

    @Test
    void testFindByExamCode() {
        Exam exam = new Exam("TEST_EX02", "Find Exam", "Finding", LocalDate.of(2025, 7, 20), 90);
        examService.save(exam);
        Optional<Exam> found = examService.findByExamCode("TEST_EX02");
        assertTrue(found.isPresent());
        assertEquals("Find Exam", found.get().getName());
    }

    @Test
    void testDeleteExam() {
        Exam exam = new Exam("TEST_EX03", "Delete Exam", "Deleting", LocalDate.of(2025, 8, 10), 60);
        Exam saved = examService.save(exam);
        Long id = saved.getId();
        examService.deleteById(id);
        assertFalse(examService.findById(id).isPresent());
    }
}
