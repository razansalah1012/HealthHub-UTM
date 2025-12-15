package com.secj3303.controller;

import com.secj3303.dao.*;
import com.secj3303.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.Year;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired private ProgramDao programDao;
    @Autowired private EnrollmentDao enrollmentDao;
    @Autowired private BmiRecordDao bmiDao;
    @Autowired private MemberDao memberDao;

    private boolean notMember(HttpSession session){
        return session.getAttribute("role") == null || !"member".equals(session.getAttribute("role"));
    }

    private Integer getUserId(HttpSession session){
        return (Integer) session.getAttribute("userId");
    }

    private Member getMemberOrNull(HttpSession session){
        Integer userId = getUserId(session);
        if (userId == null) return null;
        return memberDao.findByUserId(userId);
    }

    /* ================= DASHBOARD ================= */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (notMember(session)) return "redirect:/auth/login";

        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("desc",
                "HealthHub@UTM helps you calculate BMI, track your BMI history, browse fitness programs, and manage your enrollments.");
        return "member/dashboard";
    }

    /* ================= BMI ================= */
    @GetMapping("/bmi/form")
    public String bmiForm(HttpSession session) {
        if (notMember(session)) return "redirect:/auth/login";
        return "member/bmi-form";
    }

    @PostMapping("/bmi/calculate")
    public String bmiResult(@RequestParam double height,
                            @RequestParam double weight,
                            @RequestParam int yob,
                            HttpSession session,
                            Model model) {

        if (notMember(session)) return "redirect:/auth/login";

        Member member = getMemberOrNull(session);
        if (member == null) {
            model.addAttribute("error", "Member profile not found. Please register again as a member.");
            return "member/bmi-form";
        }

        int age = Year.now().getValue() - yob;
        double bmi = weight / (height * height);

        BmiRecord record = new BmiRecord(member, height, weight, bmi, age);
        bmiDao.save(record);

        model.addAttribute("bmi", bmi);
        model.addAttribute("age", age);
        model.addAttribute("category", bmiCategory(bmi));

        return "member/bmi-result";
    }

    @GetMapping("/bmi/history")
    public String bmiHistory(HttpSession session, Model model) {
        if (notMember(session)) return "redirect:/auth/login";

        Member member = getMemberOrNull(session);
        if (member == null) return "redirect:/auth/login";

        model.addAttribute("records", bmiDao.findByMember(member.getId()));
        return "member/bmi-history";
    }

    /* ================= PROGRAMS ================= */
    @GetMapping("/programs")
    public String browsePrograms(HttpSession session, Model model) {

        if (!"member".equals(session.getAttribute("role"))) {
            return "redirect:/auth/login";
        }

        model.addAttribute("programs", programDao.findAll());

        return "member/program-list";
    }


    /* ================= ENROLLMENT ================= */
    @GetMapping("/enroll/{programId}")
    public String enroll(@PathVariable Integer programId, HttpSession session) {
        if (notMember(session)) return "redirect:/auth/login";

        Member member = getMemberOrNull(session);
        if (member == null) return "redirect:/auth/login";

        Program program = programDao.findById(programId);
        if (program == null) return "redirect:/member/programs";

        Enrollment e = new Enrollment(member, program);
        e.setPaymentStatus("UNPAID"); // default
        enrollmentDao.save(e);

        return "redirect:/member/enrollments";
    }

    @GetMapping("/enrollments")
    public String myEnrollments(HttpSession session, Model model) {
        if (notMember(session)) return "redirect:/auth/login";

        Member member = getMemberOrNull(session);
        if (member == null) return "redirect:/auth/login";

        model.addAttribute("enrollments", enrollmentDao.findByMember(member.getId()));
        return "member/enrollments";
    }

    // “Logical” payment approach for demo: click button -> mark paid
    @PostMapping("/enrollments/pay/{enrollmentId}")
    public String pay(@PathVariable Integer enrollmentId, HttpSession session) {
        if (notMember(session)) return "redirect:/auth/login";

        enrollmentDao.updatePaymentStatus(enrollmentId, "PAID");
        return "redirect:/member/enrollments";
    }

    private String bmiCategory(double bmi){
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }
}
