package br.com.yuriabe.Bestiario.controller;

import br.com.yuriabe.Bestiario.exceptions.ResourceNotFoundException;
import br.com.yuriabe.Bestiario.model.InimigoModel; //
import br.com.yuriabe.Bestiario.model.JogoModel; // 1. Importe a classe JogoModel
import br.com.yuriabe.Bestiario.repository.InimigoRepository; //
import br.com.yuriabe.Bestiario.repository.JogoRepository; // 2. Importe a classe JogoRepository
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.stereotype.Controller; //
import org.springframework.ui.Model; //
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; //
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping; //
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/inimigos")
public class InimigoController {

    @Autowired
    private InimigoRepository inimigoRepository;

    @Autowired
    private JogoRepository jogoRepository; // 3. Injete o JogoRepository

    @GetMapping
    public String index(Model model) {
        List<InimigoModel> inimigos = inimigoRepository.findAll();
        model.addAttribute("inimigos", inimigos);
        return "inimigos/index";
    }

    // method get, to return a view
    @GetMapping("/create")
    public ModelAndView create() {
        // create an instance of ModelAndView, object that encapsulate view and data
        // in constructor, we inform the endpoint to show the view
        ModelAndView mv = new ModelAndView("inimigo/create");
        // instantiate a product, with a category
        InimigoModel inimigoModel = new InimigoModel();
        inimigoModel.setJogo(new JogoModel());
        // add the objects in ModelAndView instance
        mv.addObject("inimigo", inimigoModel);
        mv.addObject("jogos", jogoRepository.findAll());
        // return ModelAndView
        return mv;
    }

    /*
     * Method post - the annotation @ModelAttribute maps the object received
     * from form, and passes to productModel input param.
     * Redirect attributes is used to inform parameters available in response
     */
    @PostMapping
    public String save(@Valid @ModelAttribute("inimigo") InimigoModel inimigoModel,
            RedirectAttributes redirectAttributes) {
        inimigoRepository.save(inimigoModel);
        // the FlashAttributes are available once in response
        redirectAttributes.addFlashAttribute("message", "Inimigo salvo com sucesso!");
        // redirect to main page of products
        return "redirect:/inimigos";
    }

    /*
     * Edit a product - method Get - inform path parameter (or path variable) in
     * URL
     * The annotation @ParamVariable maps the path parameter (via URL) to input
     * parameter of this method
     */
    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable long id) {
        // try to retrieve the Product
        InimigoModel inimigoModel = inimigoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inimigo não encontrado!"));
        // creates an instance of ModelAndView - inform the URL to redirect
        ModelAndView mv = new ModelAndView("inimigo/edit");
        // add the data in Model Object
        mv.addObject("inimigo", inimigoModel);
        mv.addObject("jogos", jogoRepository.findAll());
        // return the ModelAndView Instance
        return mv;
    }

    /*
     * Method put - to update resources
     * Here, we need the path variable and the object to update.
     * We do it using the annotations @PathVariable and @ModelAttribute
     * The redirect attributes is used to show a message
     */
    @PutMapping("/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute("inimigo") InimigoModel inimigoModel,
            RedirectAttributes redirectAttributes) {
        // try to retrive the Product informed
        var found = inimigoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inimigo não encontrado!"));
        // update the values
        found.setNome(inimigoModel.getNome());
        found.setJogo(inimigoModel.getJogo());
        // save
        inimigoRepository.save(found);
        // add the flash attributes, available once in response.
        redirectAttributes.addFlashAttribute("message", "Inimigo atualizado com sucesso!");
        // redirects to main page of the products
        return "redirect:/inimigos";
    }

    // delete method - using the path param id
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        // try to retrieve the product
        var found = inimigoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inimigo não encontrado!"));
        // delete it
        inimigoRepository.delete(found);
        // add the flash attributes
        redirectAttributes.addFlashAttribute("message", "Inimigo excluído com sucesso!");
        // redirects to main page of products
        return "redirect:/inimigos";
    }
}