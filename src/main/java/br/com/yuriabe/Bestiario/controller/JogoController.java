package br.com.yuriabe.Bestiario.controller;

import br.com.yuriabe.Bestiario.dto.JogoDTO;
import br.com.yuriabe.Bestiario.service.JogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jogos")
@RequiredArgsConstructor
public class JogoController {

    private final JogoService jogoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("jogos", jogoService.findAll());
        return "jogos/index";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("jogos/create");
        mv.addObject("jogo", new JogoDTO());
        return mv;
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("jogo") JogoDTO jogoDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("jogo", jogoDTO);
            return "jogos/create";
        }

        jogoService.save(jogoDTO);
        redirectAttributes.addFlashAttribute("message", "Jogo salvo com sucesso!");
        return "redirect:/jogos";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("jogos/edit");
        mv.addObject("jogo", jogoService.findById(id));
        return mv;
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("jogo") JogoDTO jogoDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("jogo", jogoDTO);
            return "jogos/edit";
        }

        jogoService.update(id, jogoDTO);
        redirectAttributes.addFlashAttribute("message", "Jogo atualizado com sucesso!");
        return "redirect:/jogos";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jogoService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Jogo excluído com sucesso!");
        return "redirect:/jogos";
    }
}
