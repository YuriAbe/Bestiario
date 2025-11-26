package br.com.yuriabe.Bestiario.controller;

import br.com.yuriabe.Bestiario.exceptions.ResourceNotFoundException;
import br.com.yuriabe.Bestiario.model.JogoModel; //
import br.com.yuriabe.Bestiario.repository.JogoRepository; //
import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.stereotype.Controller; //
import org.springframework.ui.Model; //
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; //
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping; //
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("jogos")
public class JogoController {

    // add the repository
    @Autowired
    JogoRepository jogoRepository;

    @GetMapping
    public String index(Model model) {
        // retrieve the data from database
        List<JogoModel> jogos = jogoRepository.findAll();
        // add to model (UI)
        model.addAttribute("jogos", jogos);
        // redirect to index
        return "jogos/index";
    }

    // method get, to return a view
    @GetMapping("/create")
    public ModelAndView create() {
        // create an instance of ModelAndView, object that encapsulate view and data
        // in constructor, we inform the endpoint to show the view
        ModelAndView mv = new ModelAndView("jogo/create");
        // instantiate a product, with a category
        JogoModel jogoModel = new JogoModel();
        // add the objects in ModelAndView instance
        mv.addObject("jogo", jogoModel);
        // return ModelAndView
        return mv;
    }

    /*
     * Method post - the annotation @ModelAttribute maps the object received
     * from form, and passes to productModel input param.
     * Redirect attributes is used to inform parameters available in response
     */
    @PostMapping
    public String save(@Valid @ModelAttribute("jogo") JogoModel jogoModel,
            RedirectAttributes redirectAttributes) {
        jogoRepository.save(jogoModel);
        // the FlashAttributes are available once in response
        redirectAttributes.addFlashAttribute("message", "Jogo salvo com sucesso!");
        // redirect to main page of products
        return "redirect:/jogos";
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
        JogoModel jogoModel = jogoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Jogo não encontrado!"));
        // creates an instance of ModelAndView - inform the URL to redirect
        ModelAndView mv = new ModelAndView("jogo/edit");
        // add the data in Model Object
        mv.addObject("jogo", jogoModel);
        // return the ModelAndView Instance
        return mv;
    }

    /*
     * Method put - to update resources
     * Here, we need the path variable and the object to update.
     * We do it using the annotations @PathVariable and @ModelAttribute
     * The redirect attributes is used to show a message
     */
    // @PutMapping("/{id}")
    // public String update(@PathVariable long id,
    // @Valid @ModelAttribute("jogo") JogoModel jogoModel,
    // RedirectAttributes redirectAttributes) {
    // // try to retrive the Product informed
    // var found = jogoRepository.findById(id).orElseThrow(
    // () -> new ResourceNotFoundException("Jogo não encontrado!"));
    // // update the values
    // found.setTitulo(jogoModel.getTitulo());
    // // save
    // jogoRepository.save(found);
    // // add the flash attributes, available once in response.
    // redirectAttributes.addFlashAttribute("message", "Jogo atualizado com
    // sucesso!");
    // // redirects to main page of the products
    // return "redirect:/jogos";
    // }

    @PutMapping("/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute("jogo") JogoModel jogoModel,
            RedirectAttributes redirectAttributes) {

        // 1. Garante que o JogoModel existe antes de atualizar
        jogoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Jogo não encontrado!"));

        // 2. Define o ID no objeto recebido do formulário
        jogoModel.setId(id);

        // 3. Salva o objeto completo. O Hibernate/JPA fará o UPDATE.
        jogoRepository.save(jogoModel);

        // ... (restante do código)
        redirectAttributes.addFlashAttribute("message", "Jogo atualizado com sucesso!");
        return "redirect:/jogos";
    }

    // delete method - using the path param id
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        // try to retrieve the product
        var found = jogoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Jogo não encontrado!"));
        // delete it
        jogoRepository.delete(found);
        // add the flash attributes
        redirectAttributes.addFlashAttribute("message", "Jogo excluído com sucesso!");
        // redirects to main page of products
        return "redirect:/jogos";
    }
}
