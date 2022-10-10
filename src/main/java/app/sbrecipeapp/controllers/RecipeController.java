package app.sbrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
    
    private final RecipeService rService;

    public RecipeController(RecipeService rService) {
        this.rService = rService;
    }
    
    @GetMapping({"/recipe/{id}/show"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", rService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping({"recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", rService.findCommandById(Long.parseLong(id)));
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = rService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping({"recipe/{id}/delete"})
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting Id: " + id);
        rService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
