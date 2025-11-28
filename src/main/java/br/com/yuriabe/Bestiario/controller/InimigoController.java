package br.com.yuriabe.Bestiario.controller;

import br.com.yuriabe.Bestiario.dto.InimigoDTO;
import br.com.yuriabe.Bestiario.service.InimigoService;
import br.com.yuriabe.Bestiario.repository.JogoRepository; // Ainda necessário para popular o dropdown
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inimigos")
public class InimigoController {

    @Autowired
    private InimigoService inimigoService; // Usando a camada Service

    @Autowired
    private JogoRepository jogoRepository; // Mantido para popular o dropdown de Jogos

    @GetMapping
    public String index(Model model) {
        // Agora retorna uma lista de DTOs
        model.addAttribute("inimigos", inimigoService.findAll());
        return "inimigos/index";
    }

    // Testando erro 500
    @GetMapping("/teste-erro")
    public String testeErro() {
        throw new RuntimeException("Erro proposital para teste");
    }

    // method get, to return a view
    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("inimigos/create"); // Corrigido para "inimigos/create"
        mv.addObject("inimigo", new InimigoDTO()); // Passa um DTO vazio
        mv.addObject("jogos", jogoRepository.findAll()); // Lista de JogoModel para o dropdown
        return mv;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("inimigo") InimigoDTO inimigoDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("inimigo", inimigoDTO); // ✔️ faltava isso!
            model.addAttribute("jogos", jogoRepository.findAll());
            return "inimigos/create";
        }

        inimigoService.save(inimigoDTO);
        redirectAttributes.addFlashAttribute("message", "Inimigo salvo com sucesso!");
        return "redirect:/inimigos";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable long id) {
        // Busca o DTO
        InimigoDTO inimigoDTO = inimigoService.findById(id);

        ModelAndView mv = new ModelAndView("inimigos/edit"); // Corrigido para "inimigos/edit"
        mv.addObject("inimigo", inimigoDTO);
        mv.addObject("jogos", jogoRepository.findAll());
        return mv;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute("inimigo") InimigoDTO inimigoDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("jogos", jogoRepository.findAll());
            return "inimigos/edit";
        }

        inimigoService.update(id, inimigoDTO);
        redirectAttributes.addFlashAttribute("message", "Inimigo atualizado com sucesso!");
        return "redirect:/inimigos";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        inimigoService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Inimigo excluído com sucesso!");
        return "redirect:/inimigos";
    }
}
