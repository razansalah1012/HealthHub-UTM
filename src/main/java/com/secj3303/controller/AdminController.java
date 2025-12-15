package com.secj3303.controller;

import com.secj3303.dao.ProgramDao;
import com.secj3303.dao.CategoryDao;
import com.secj3303.model.Program;
import com.secj3303.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private CategoryDao categoryDao;

    /* =========================
       ADMIN DASHBOARD
       ========================= */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "admin/dashboard";
    }

    /* =========================
       PROGRAM MANAGEMENT (CRUD)
       ========================= */
    @GetMapping("/programs")
    public String listPrograms(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("programs", programDao.findAll());
        return "admin/program-list";
    }

    @GetMapping("/program/add")
    public String addProgram(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("program", new Program());
        return "admin/program-form";
    }

    @PostMapping("/program/save")
    public String saveProgram(@ModelAttribute Program program, HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        programDao.save(program);
        return "redirect:/admin/programs";
    }

    @GetMapping("/program/edit/{id}")
    public String editProgram(@PathVariable Integer id, HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("program", programDao.findById(id));
        return "admin/program-form";
    }

    @GetMapping("/program/delete/{id}")
    public String deleteProgram(@PathVariable Integer id, HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        programDao.delete(id);
        return "redirect:/admin/programs";
    }

    /* =========================
       CATEGORY MANAGEMENT
       ========================= */
    @GetMapping("/categories")
    public String listCategories(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("categories", categoryDao.findAll());
        return "admin/category-list";
    }

    @PostMapping("/category/add")
    public String addCategory(@RequestParam String name, HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        categoryDao.save(new Category(name));
        return "redirect:/admin/categories";
    }

    /* =========================
       SYSTEM SUMMARY / REPORTS
       ========================= */
    @GetMapping("/reports")
    public String reports(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("programCount", programDao.findAll().size());
        model.addAttribute("categoryCount", categoryDao.findAll().size());
        return "admin/reports";
    }
}
