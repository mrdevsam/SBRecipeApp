package app.sbrecipeapp.services;

import java.util.Set;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.domain.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand findCommandById(Long l);
    
    RecipeCommand saveRecipeCommand(RecipeCommand rCommand);
}