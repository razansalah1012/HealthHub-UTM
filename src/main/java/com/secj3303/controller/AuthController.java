package com.secj3303.controller;

import com.secj3303.dao.AuthDao;
import com.secj3303.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.secj3303.dao.MemberDao;
import com.secj3303.model.Member;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthDao authDao;

    @Autowired
    private MemberDao memberDao;   // ✅ THIS WAS MISSING

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {

        User user = authDao.login(email, password);
        
        if (user == null) {
            model.addAttribute("error",
                "Account not found. Please register first.");
            return "auth/login";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());
        session.setAttribute("name", user.getname());

        return switch (user.getRole()) {
            case "member" -> "redirect:/member/dashboard";
            case "trainer" -> "redirect:/trainer/dashboard";
            case "admin" -> "redirect:/admin/dashboard";
            default -> "redirect:/auth/login";
        };
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String email,
                             @RequestParam String name,
                             @RequestParam String password,
                             @RequestParam String role,
                             Model model) {

        if (authDao.emailExists(email)) {
            model.addAttribute("error", "Email already registered.");
            return "auth/register";
        }

        User user = new User(email, name, password, role);
        authDao.register(user);

        // ✅ create Member record ONLY for members
        if ("member".equals(role)) {
            Member member = new Member(user);
            memberDao.save(member);
        }

        model.addAttribute("success", "Registration successful. Please login.");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
