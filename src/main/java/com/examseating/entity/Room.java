package com.examseating.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room code is required")
    @Column(name = "room_code", unique = true, nullable = false)
    private String roomCode;

    @NotBlank(message = "Room name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Row count is required")
    @Min(value = 1, message = "Must have at least 1 row")
    @Column(name = "row_count", nullable = false)
    private Integer rowCount;

    @NotNull(message = "Seats per row is required")
    @Min(value = 1, message = "Must have at least 1 seat per row")
    @Column(name = "seats_per_row", nullable = false)
    private Integer seatsPerRow;

    @Transient
    public int getCapacity() {
        return (rowCount != null && seatsPerRow != null) ? rowCount * seatsPerRow : 0;
    }

    public Room() {}

    public Room(String roomCode, String name, Integer rowCount, Integer seatsPerRow) {
        this.roomCode = roomCode;
        this.name = name;
        this.rowCount = rowCount;
        this.seatsPerRow = seatsPerRow;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getRowCount() { return rowCount; }
    public void setRowCount(Integer rowCount) { this.rowCount = rowCount; }

    public Integer getSeatsPerRow() { return seatsPerRow; }
    public void setSeatsPerRow(Integer seatsPerRow) { this.seatsPerRow = seatsPerRow; }
}
