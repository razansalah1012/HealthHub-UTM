package com.secj3303.controller;

import com.secj3303.dao.ProgramDao;
import com.secj3303.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramDao programDao;

    // 1) LIST
    @GetMapping("/list")
    public String list(Model model) {
        List<Program> programs = programDao.findAll();
        model.addAttribute("programs", programs);
        return "program-list";
    }

    // 2) ADD FORM
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("program", new Program());
        return "program-form";
    }

    // 3) SAVE (INSERT/UPDATE)
    @PostMapping("/save")
    public String save(@ModelAttribute("program") Program program) {
        programDao.save(program);
        return "redirect:/program/list";
    }

    // 4) EDIT FORM
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Program program = programDao.findById(id);
        model.addAttribute("program", program);
        return "program-form";
    }

    // 5) VIEW ONE (extra but useful)
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Program program = programDao.findById(id);
        model.addAttribute("program", program);
        return "program-view";
    }

    // 6) DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        programDao.delete(id);
        return "redirect:/program/list";
    }
}
