package com.examseating;

import com.examseating.entity.*;
import com.examseating.repository.*;
import com.examseating.service.SeatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SeatingServiceTest {

    @Autowired
    private SeatingService seatingService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatAssignmentRepository seatAssignmentRepository;

    private Exam testExam;
    private Room testRoom;

    @BeforeEach
    void setUp() {
        // Clear seat assignments to avoid conflicts with DataInitializer data
        seatAssignmentRepository.deleteAll();

        testExam = new Exam("SEAT_TEST", "Seating Test Exam", "Testing", LocalDate.of(2025, 12, 1), 60);
        testExam = examRepository.save(testExam);

        testRoom = new Room("SEAT-R1", "Seating Test Room", 3, 4);
        testRoom = roomRepository.save(testRoom);
    }

    @Test
    void testGenerateSeating() {
        List<SeatAssignment> assignments = seatingService.generateSeating(testExam, List.of(testRoom));
        assertFalse(assignments.isEmpty());
        // Should not exceed room capacity
        assertTrue(assignments.size() <= testRoom.getCapacity());
        // All assignments should be for our exam
        assignments.forEach(a -> assertEquals(testExam.getId(), a.getExam().getId()));
    }

    @Test
    void testSaveAndFindByExam() {
        List<SeatAssignment> assignments = seatingService.generateSeating(testExam, List.of(testRoom));
        seatingService.saveAll(assignments);

        List<SeatAssignment> found = seatingService.findByExam(testExam);
        assertEquals(assignments.size(), found.size());
    }

    @Test
    void testPublishSeating() {
        List<SeatAssignment> assignments = seatingService.generateSeating(testExam, List.of(testRoom));
        seatingService.saveAll(assignments);

        // Initially not published
        List<SeatAssignment> saved = seatingService.findByExam(testExam);
        saved.forEach(a -> assertFalse(a.getPublished()));

        // Publish
        seatingService.publishByExam(testExam);
        List<SeatAssignment> published = seatingService.findByExam(testExam);
        published.forEach(a -> assertTrue(a.getPublished()));
    }

    @Test
    void testDeleteByExam() {
        List<SeatAssignment> assignments = seatingService.generateSeating(testExam, List.of(testRoom));
        seatingService.saveAll(assignments);
        assertFalse(seatingService.findByExam(testExam).isEmpty());

        seatingService.deleteByExam(testExam);
        assertTrue(seatingService.findByExam(testExam).isEmpty());
    }
}
