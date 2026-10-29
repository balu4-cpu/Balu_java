package com.examseating.controller;

import com.examseating.entity.SeatAssignment;
import com.examseating.entity.Student;
import com.examseating.service.SeatingService;
import com.examseating.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class SearchController {

    private final StudentService studentService;
    private final SeatingService seatingService;

    public SearchController(StudentService studentService, SeatingService seatingService) {
        this.studentService = studentService;
        this.seatingService = seatingService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String rollNumber, Model model) {
        model.addAttribute("searched", false);
        model.addAttribute("notFound", false);
        if (rollNumber != null && !rollNumber.isBlank()) {
            model.addAttribute("searched", true);
            model.addAttribute("rollNumber", rollNumber);

            studentService.findByRollNumber(rollNumber.trim()).ifPresentOrElse(
                student -> {
                    model.addAttribute("student", student);
                    List<SeatAssignment> assignments = seatingService.findPublishedByStudent(student);
                    model.addAttribute("assignments", assignments);
                },
                () -> model.addAttribute("notFound", true)
            );
        }
        return "search";
    }
}
