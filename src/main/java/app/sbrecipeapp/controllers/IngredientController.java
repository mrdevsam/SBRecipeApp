package app.sbrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.sbrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService rService;

    public IngredientController(RecipeService rService) {
        this.rService = rService;
    }
    
    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient for recipeId: " + recipeId);

        //use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", rService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }
}
