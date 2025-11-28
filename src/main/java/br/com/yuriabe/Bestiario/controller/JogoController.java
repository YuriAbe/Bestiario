package br.com.yuriabe.Bestiario.controller;

import br.com.yuriabe.Bestiario.dto.JogoDTO;
import br.com.yuriabe.Bestiario.service.JogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page; // Import para a paginação

@Controller
@RequestMapping("/jogos")
@RequiredArgsConstructor
public class JogoController {

    private final JogoService jogoService;

    // ADICIONANDO PÁGINAÇÃO COM O NOVO CÓDIGO 
    @GetMapping
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<JogoDTO> pagina = jogoService.findPage(page, size);

        model.addAttribute("pagina", pagina);
        model.addAttribute("jogos", pagina.getContent());

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
        try {
            jogoService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Jogo excluído com sucesso!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/jogos";
    }

    // Método SHOW para exibir modal com detalhes do Jogo
    @GetMapping("/{id}")
    public ResponseEntity<JogoDTO> show(@PathVariable Long id) {
        JogoDTO dto = jogoService.findById(id);
        return ResponseEntity.ok(dto);
    }
}
