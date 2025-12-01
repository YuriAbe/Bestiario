package br.com.yuriabe.Bestiario.controller;

import br.com.yuriabe.Bestiario.dto.UserDTO;
import br.com.yuriabe.Bestiario.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("user") UserDTO userDTO,
            RedirectAttributes redirectAttributes,
            Model model) {

        try {
            userService.register(userDTO);
            redirectAttributes.addFlashAttribute("success", "Conta criada com sucesso! Faça login.");
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // Método - Esqueceu a senha
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam String username,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {

        if (!userService.existsByUsername(username)) {
            model.addAttribute("error", "Usuário não encontrado.");
            return "auth/forgot-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "As senhas não coincidem.");
            return "auth/forgot-password";
        }

        userService.updatePassword(username, newPassword);
        model.addAttribute("success", "Senha redefinida com sucesso! Faça login.");
        return "auth/forgot-password";
    }
}
