package com.examseating.controller;

import com.examseating.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final StudentService studentService;
    private final ExamService examService;
    private final RoomService roomService;
    private final SeatingService seatingService;

    public AdminDashboardController(StudentService studentService, ExamService examService,
                                     RoomService roomService, SeatingService seatingService) {
        this.studentService = studentService;
        this.examService = examService;
        this.roomService = roomService;
        this.seatingService = seatingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalStudents", studentService.count());
        model.addAttribute("totalExams", examService.count());
        model.addAttribute("totalRooms", roomService.count());
        model.addAttribute("publishedPlans", seatingService.countPublished());
        return "admin/dashboard";
    }
}
