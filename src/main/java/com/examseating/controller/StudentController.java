package com.examseating.controller;

import com.examseating.entity.Student;
import com.examseating.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "admin/students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isNew", true);
        return "admin/students/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Student student, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", true);
            return "admin/students/form";
        }
        if (studentService.existsByRollNumber(student.getRollNumber())) {
            result.rejectValue("rollNumber", "duplicate", "Roll number already exists");
            model.addAttribute("isNew", true);
            return "admin/students/form";
        }
        studentService.save(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student created successfully!");
        return "redirect:/admin/students";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return studentService.findById(id).map(student -> {
            model.addAttribute("student", student);
            return "admin/students/view";
        }).orElse("redirect:/admin/students");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return studentService.findById(id).map(student -> {
            model.addAttribute("student", student);
            model.addAttribute("isNew", false);
            return "admin/students/form";
        }).orElse("redirect:/admin/students");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Student student,
                          BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", false);
            return "admin/students/form";
        }
        return studentService.findById(id).map(existing -> {
            // Check for duplicate roll number only if changed
            if (!existing.getRollNumber().equals(student.getRollNumber())
                    && studentService.existsByRollNumber(student.getRollNumber())) {
                result.rejectValue("rollNumber", "duplicate", "Roll number already exists");
                model.addAttribute("isNew", false);
                return "admin/students/form";
            }
            student.setId(id);
            studentService.save(student);
            redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
            return "redirect:/admin/students";
        }).orElse("redirect:/admin/students");
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/admin/students";
    }
}
