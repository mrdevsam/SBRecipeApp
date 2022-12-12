package app.sbrecipeapp.controllers;

import javax.validation.Valid;

//import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;

import app.sbrecipeapp.commands.RecipeCommand;
//import app.sbrecipeapp.exceptions.NotFoundException;
import app.sbrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
    
    private static final String RECIPE_RECIPRFORM_URL = "recipe/recipeform";
    private final RecipeService rService;

    public RecipeController(RecipeService rService) {
        this.rService = rService;
    }
    
    @GetMapping({"/recipe/{id}/show"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", rService.findById(String.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_RECIPRFORM_URL;
    }

    @GetMapping({"recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", rService.findCommandById(String.valueOf(id)).block());
        return RECIPE_RECIPRFORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objError -> {
                log.debug(objError.toString());
            });

            return RECIPE_RECIPRFORM_URL;
        }

        RecipeCommand savedCommand = rService.saveRecipeCommand(command).block();
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping({"recipe/{id}/delete"})
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting Id: " + id);
        rService.deleteById(String.valueOf(id));
        return "redirect:/";
    }

    /*
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
    */
}
