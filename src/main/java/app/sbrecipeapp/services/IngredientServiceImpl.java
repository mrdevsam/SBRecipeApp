package app.sbrecipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.converters.IngredientToIngredientCommand;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
            RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            // todo impl error handling
            log.error("recipe id not found. recipeId: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            // todo impl error handling
            log.error("ingredient id not found. ingredientId: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

}
