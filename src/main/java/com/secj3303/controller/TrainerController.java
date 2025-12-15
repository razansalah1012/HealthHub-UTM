package com.secj3303.controller;

import com.secj3303.dao.WorkoutPlanDao;
import com.secj3303.model.WorkoutPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private WorkoutPlanDao workoutPlanDao;

    /* =========================
       TRAINER DASHBOARD
       ========================= */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "trainer/dashboard";
    }

    /* =========================
       CREATE WORKOUT PLAN (FORM)
       ========================= */
    @GetMapping("/plan/create")
    public String createPlanForm(HttpSession session) {
        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "trainer/plan-form";
    }

    /* =========================
       SAVE WORKOUT PLAN
       ========================= */
    @PostMapping("/plan/save")
    public String savePlan(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam Integer durationWeeks,
                           HttpSession session) {

        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }

        WorkoutPlan plan = new WorkoutPlan(title, description, durationWeeks);
        workoutPlanDao.save(plan);

        return "redirect:/trainer/dashboard";
    }

    /* =========================
       VIEW ALL WORKOUT PLANS
       (Optional but useful)
       ========================= */
    @GetMapping("/plans")
    public String listPlans(HttpSession session, Model model) {
        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }

        List<WorkoutPlan> plans = workoutPlanDao.findAll();
        model.addAttribute("plans", plans);

        return "trainer/plan-list";
    }

    /* =========================
       ASSIGN PLAN TO MEMBERS
       (Conceptual / Placeholder)
       ========================= */
    @GetMapping("/plan/assign")
    public String assignPlan(HttpSession session) {
        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "trainer/assign-plan";
    }

    /* =========================
       MONITOR MEMBER PROGRESS
       ========================= */
    @GetMapping("/progress")
    public String progress(HttpSession session) {
        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "trainer/progress";
    }

    /* =========================
       SCHEDULE SESSIONS
       ========================= */
    @GetMapping("/sessions")
    public String sessions(HttpSession session) {
        if (!"trainer".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }
        return "trainer/sessions";
    }
}
