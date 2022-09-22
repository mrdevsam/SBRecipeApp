package app.sbrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.services.RecipeService;

@Controller
public class RecipeController {
    
    private final RecipeService rService;

    public RecipeController(RecipeService rService) {
        this.rService = rService;
    }
    
    @RequestMapping({"/recipe/show/{id}"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", rService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = rService.saveRecipeCommand(command);
        return "redirect:/recipe/show/" + savedCommand.getId();
    }
}
