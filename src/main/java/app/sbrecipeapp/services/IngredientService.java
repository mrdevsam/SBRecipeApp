package app.sbrecipeapp.services;

import app.sbrecipeapp.commands.IngredientCommand;

public interface IngredientService {
    
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
