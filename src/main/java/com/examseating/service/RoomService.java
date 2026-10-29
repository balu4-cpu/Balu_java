package com.examseating.service;

import com.examseating.entity.Room;
import com.examseating.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> findByRoomCode(String roomCode) {
        return roomRepository.findByRoomCode(roomCode);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public boolean existsByRoomCode(String roomCode) {
        return roomRepository.existsByRoomCode(roomCode);
    }

    public long count() {
        return roomRepository.count();
    }
}
