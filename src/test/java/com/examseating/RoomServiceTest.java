package com.examseating;

import com.examseating.entity.Room;
import com.examseating.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    void testCreateRoom() {
        Room room = new Room("TEST-R1", "Test Room 1", 4, 5);
        Room saved = roomService.save(room);
        assertNotNull(saved.getId());
        assertEquals("TEST-R1", saved.getRoomCode());
        assertEquals(20, saved.getCapacity());
    }

    @Test
    void testCapacityIsTransient() {
        Room room = new Room("TEST-R2", "Test Room 2", 3, 10);
        Room saved = roomService.save(room);
        assertEquals(30, saved.getCapacity());
        // Reload from DB
        Optional<Room> found = roomService.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(30, found.get().getCapacity());
    }

    @Test
    void testDeleteRoom() {
        Room room = new Room("TEST-R3", "Test Room 3", 2, 8);
        Room saved = roomService.save(room);
        Long id = saved.getId();
        roomService.deleteById(id);
        assertFalse(roomService.findById(id).isPresent());
    }
}
