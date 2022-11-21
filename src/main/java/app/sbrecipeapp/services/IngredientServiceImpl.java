package app.sbrecipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.converters.IngredientCommandToIngredient;
import app.sbrecipeapp.converters.IngredientToIngredientCommand;
import app.sbrecipeapp.domain.Ingredient;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;
import app.sbrecipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
            IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository,
            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            // toss error if id not found
            log.error("recipeID not found. recipeId: " + ingredientCommand.getRecipeId());

            return new IngredientCommand();
        } else {

            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {

                Ingredient ingredientFound = ingredientOptional.get();

                // set components
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND!!!")));// todo address this
            } else {

                // add new ingredient
                Ingredient ingredientz = ingredientCommandToIngredient.convert(ingredientCommand);
                //ingredientz.setRecipe(recipe);
                recipe.addIngredient(ingredientz);

            }

            // save the recipe
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            // check by description
            if (!savedIngredientOptional.isPresent()) {
                // not totally safe .. but best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            //todo check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(String recipeId, String idToDelete) {
        
        log.debug("Deleting ingredient: " + recipeId + ": " + idToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipeFound = recipeOptional.get();
            log.debug("recipe found.");

            Optional<Ingredient> ingredientOptional = recipeFound.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(idToDelete)).findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("ingredient found.");

                Ingredient ingredientToDelete = ingredientOptional.get();
                //ingredientToDelete.setRecipe(null);
                recipeFound.getIngredients().remove(ingredientOptional.get());

                recipeRepository.save(recipeFound);
            }
        } else {
            log.debug("recipe id not found!!!. Id: " + recipeId);
        }
        
    }

    
}
