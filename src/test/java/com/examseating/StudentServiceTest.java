package com.examseating;

import com.examseating.entity.Student;
import com.examseating.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void testCreateStudent() {
        Student student = new Student("TEST001", "Test Student", "test@example.com", "Computer Science", 1);
        Student saved = studentService.save(student);
        assertNotNull(saved.getId());
        assertEquals("TEST001", saved.getRollNumber());
        assertEquals("Test Student", saved.getName());
    }

    @Test
    void testFindByRollNumber() {
        Student student = new Student("TEST002", "Another Student", "another@example.com", "Physics", 2);
        studentService.save(student);
        Optional<Student> found = studentService.findByRollNumber("TEST002");
        assertTrue(found.isPresent());
        assertEquals("Another Student", found.get().getName());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student("TEST003", "Original Name", "orig@example.com", "Math", 3);
        Student saved = studentService.save(student);
        saved.setName("Updated Name");
        saved.setEmail("updated@example.com");
        Student updated = studentService.save(saved);
        assertEquals("Updated Name", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student("TEST004", "Delete Me", "delete@example.com", "Chemistry", 1);
        Student saved = studentService.save(student);
        Long id = saved.getId();
        studentService.deleteById(id);
        Optional<Student> found = studentService.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAllStudents() {
        studentService.save(new Student("LIST001", "Student A", "a@example.com", "CS", 1));
        studentService.save(new Student("LIST002", "Student B", "b@example.com", "CS", 2));
        // Note: DataInitializer may add 10 students, so we check we have at least 2
        List<Student> all = studentService.findAll();
        assertTrue(all.size() >= 2);
    }
}
