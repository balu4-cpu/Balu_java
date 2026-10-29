package com.examseating.controller;

import com.examseating.entity.*;
import com.examseating.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/seating")
public class SeatingController {

    private final ExamService examService;
    private final RoomService roomService;
    private final SeatingService seatingService;

    public SeatingController(ExamService examService, RoomService roomService,
                              SeatingService seatingService) {
        this.examService = examService;
        this.roomService = roomService;
        this.seatingService = seatingService;
    }

    @GetMapping
    public String index(Model model) {
        List<Exam> exams = examService.findAll();
        Map<Long, Boolean> examHasPlan = new LinkedHashMap<>();
        Map<Long, Boolean> examIsPublished = new LinkedHashMap<>();
        for (Exam exam : exams) {
            List<SeatAssignment> assignments = seatingService.findByExam(exam);
            examHasPlan.put(exam.getId(), !assignments.isEmpty());
            examIsPublished.put(exam.getId(),
                assignments.stream().anyMatch(SeatAssignment::getPublished));
        }
        model.addAttribute("exams", exams);
        model.addAttribute("examHasPlan", examHasPlan);
        model.addAttribute("examIsPublished", examIsPublished);
        return "admin/seating/index";
    }

    @GetMapping("/generate")
    public String showGenerateForm(Model model) {
        model.addAttribute("exams", examService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "admin/seating/generate";
    }

    @PostMapping("/preview")
    public String preview(@RequestParam Long examId,
                           @RequestParam List<Long> roomIds,
                           Model model, RedirectAttributes redirectAttributes) {
        Optional<Exam> examOpt = examService.findById(examId);
        if (examOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Exam not found");
            return "redirect:/admin/seating/generate";
        }
        Exam exam = examOpt.get();

        // Check if plan already exists
        if (seatingService.existsByExam(exam)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "A seating plan already exists for this exam. Delete it first to regenerate.");
            return "redirect:/admin/seating";
        }

        List<Room> rooms = roomIds.stream()
                .map(roomService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<SeatAssignment> assignments = seatingService.generateSeating(exam, rooms);

        model.addAttribute("exam", exam);
        model.addAttribute("rooms", rooms);
        model.addAttribute("assignments", assignments);
        model.addAttribute("examId", examId);
        model.addAttribute("roomIds", roomIds);

        return "admin/seating/preview";
    }

    @PostMapping("/save")
    public String save(@RequestParam Long examId,
                        @RequestParam List<Long> roomIds,
                        RedirectAttributes redirectAttributes) {
        Optional<Exam> examOpt = examService.findById(examId);
        if (examOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Exam not found");
            return "redirect:/admin/seating";
        }
        Exam exam = examOpt.get();

        List<Room> rooms = roomIds.stream()
                .map(roomService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<SeatAssignment> assignments = seatingService.generateSeating(exam, rooms);
        seatingService.saveAll(assignments);

        redirectAttributes.addFlashAttribute("successMessage",
            "Seating plan saved as draft for " + exam.getName());
        return "redirect:/admin/seating";
    }

    @GetMapping("/{examId}")
    public String view(@PathVariable Long examId, Model model) {
        return examService.findById(examId).map(exam -> {
            List<SeatAssignment> assignments = seatingService.findByExam(exam);
            List<Room> rooms = seatingService.getAssignedRooms(exam);
            boolean isPublished = assignments.stream().anyMatch(SeatAssignment::getPublished);

            model.addAttribute("exam", exam);
            model.addAttribute("assignments", assignments);
            model.addAttribute("rooms", rooms);
            model.addAttribute("isPublished", isPublished);
            return "admin/seating/view";
        }).orElse("redirect:/admin/seating");
    }

    @PostMapping("/{examId}/publish")
    public String publish(@PathVariable Long examId, RedirectAttributes redirectAttributes) {
        examService.findById(examId).ifPresent(exam -> {
            seatingService.publishByExam(exam);
            redirectAttributes.addFlashAttribute("successMessage",
                "Seating plan published for " + exam.getName());
        });
        return "redirect:/admin/seating";
    }

    @PostMapping("/{examId}/delete")
    public String delete(@PathVariable Long examId, RedirectAttributes redirectAttributes) {
        examService.findById(examId).ifPresent(exam -> {
            seatingService.deleteByExam(exam);
            redirectAttributes.addFlashAttribute("successMessage",
                "Seating plan deleted for " + exam.getName());
        });
        return "redirect:/admin/seating";
    }
}
