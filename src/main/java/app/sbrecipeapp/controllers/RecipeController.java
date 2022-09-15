package app.sbrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
