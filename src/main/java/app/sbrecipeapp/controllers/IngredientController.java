package app.sbrecipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.services.IngredientService;
import app.sbrecipeapp.services.RecipeService;
import app.sbrecipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService rService;
    private final IngredientService iService;
    private final UnitOfMeasureService uService;

    public IngredientController(RecipeService rService, IngredientService iService, UnitOfMeasureService uService) {
        this.rService = rService;
        this.iService = iService;
        this.uService = uService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient for recipeId: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", rService.findCommandById(String.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        model.addAttribute("ingredient",
                iService.findByRecipeIdAndIngredientId(String.valueOf(recipeId), String.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        model.addAttribute("ingredient",
                iService.findByRecipeIdAndIngredientId(String.valueOf(recipeId), String.valueOf(id)));
        model.addAttribute("uomList", uService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {

        IngredientCommand savedCommand = iService.saveIngredientCommand(ingredientCommand);

        //log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        //make sure we have a good id value
        RecipeCommand recipeCommand = rService.findCommandById(String.valueOf(recipeId));
        // TODO raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();

        //ingredientCommand.setRecipeId(String.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList", uService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {

        log.debug("deleting ingredient id: " + id);

        iService.deleteById(String.valueOf(recipeId), String.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
