package com.examseating.controller;

import com.examseating.entity.Room;
import com.examseating.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "admin/rooms/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("isNew", true);
        return "admin/rooms/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Room room, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", true);
            return "admin/rooms/form";
        }
        if (roomService.existsByRoomCode(room.getRoomCode())) {
            result.rejectValue("roomCode", "duplicate", "Room code already exists");
            model.addAttribute("isNew", true);
            return "admin/rooms/form";
        }
        roomService.save(room);
        redirectAttributes.addFlashAttribute("successMessage", "Room created successfully!");
        return "redirect:/admin/rooms";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return roomService.findById(id).map(room -> {
            model.addAttribute("room", room);
            return "admin/rooms/view";
        }).orElse("redirect:/admin/rooms");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return roomService.findById(id).map(room -> {
            model.addAttribute("room", room);
            model.addAttribute("isNew", false);
            return "admin/rooms/form";
        }).orElse("redirect:/admin/rooms");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Room room,
                          BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", false);
            return "admin/rooms/form";
        }
        return roomService.findById(id).map(existing -> {
            if (!existing.getRoomCode().equals(room.getRoomCode())
                    && roomService.existsByRoomCode(room.getRoomCode())) {
                result.rejectValue("roomCode", "duplicate", "Room code already exists");
                model.addAttribute("isNew", false);
                return "admin/rooms/form";
            }
            room.setId(id);
            roomService.save(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully!");
            return "redirect:/admin/rooms";
        }).orElse("redirect:/admin/rooms");
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roomService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully!");
        return "redirect:/admin/rooms";
    }
}
