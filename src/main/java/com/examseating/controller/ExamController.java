package com.examseating.controller;

import com.examseating.entity.Exam;
import com.examseating.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("exams", examService.findAll());
        return "admin/exams/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("exam", new Exam());
        model.addAttribute("isNew", true);
        return "admin/exams/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Exam exam, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", true);
            return "admin/exams/form";
        }
        if (examService.existsByExamCode(exam.getExamCode())) {
            result.rejectValue("examCode", "duplicate", "Exam code already exists");
            model.addAttribute("isNew", true);
            return "admin/exams/form";
        }
        examService.save(exam);
        redirectAttributes.addFlashAttribute("successMessage", "Exam created successfully!");
        return "redirect:/admin/exams";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        return examService.findById(id).map(exam -> {
            model.addAttribute("exam", exam);
            return "admin/exams/view";
        }).orElse("redirect:/admin/exams");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return examService.findById(id).map(exam -> {
            model.addAttribute("exam", exam);
            model.addAttribute("isNew", false);
            return "admin/exams/form";
        }).orElse("redirect:/admin/exams");
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Exam exam,
                          BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", false);
            return "admin/exams/form";
        }
        return examService.findById(id).map(existing -> {
            if (!existing.getExamCode().equals(exam.getExamCode())
                    && examService.existsByExamCode(exam.getExamCode())) {
                result.rejectValue("examCode", "duplicate", "Exam code already exists");
                model.addAttribute("isNew", false);
                return "admin/exams/form";
            }
            exam.setId(id);
            examService.save(exam);
            redirectAttributes.addFlashAttribute("successMessage", "Exam updated successfully!");
            return "redirect:/admin/exams";
        }).orElse("redirect:/admin/exams");
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        examService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Exam deleted successfully!");
        return "redirect:/admin/exams";
    }
}
