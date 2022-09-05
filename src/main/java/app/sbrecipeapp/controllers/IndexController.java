package app.sbrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.sbrecipeapp.services.RecipeService;

@Controller
public class IndexController {
    
    private final RecipeService recipeService;
    
    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"" , "/" , "/index" , "index.html"})
    public String getIndexpage(Model model) {
        
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
