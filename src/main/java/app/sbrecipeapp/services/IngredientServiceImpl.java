package app.sbrecipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.converters.IngredientCommandToIngredient;
import app.sbrecipeapp.converters.IngredientToIngredientCommand;
import app.sbrecipeapp.domain.Ingredient;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import app.sbrecipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
            IngredientCommandToIngredient ingredientCommandToIngredient, RecipeReactiveRepository recipeReactiveRepository ,
            UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        return recipeReactiveRepository
        				.findById(recipeId)
        				.flatMapIterable(Recipe::getIngredients)
                        .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                        .single()
                    .map(ingredient -> {
                        IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                        command.setRecipeId(recipeId);
                        return command;
                    });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {

        Recipe recipe = recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).block();

        if (recipe == null) {
            // toss error if id not found
            log.error("recipeID not found. recipeId: " + ingredientCommand.getRecipeId());

            return Mono.just(new IngredientCommand());
        } else {

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {

                Ingredient ingredientFound = ingredientOptional.get();

                // set components
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setUom(unitOfMeasureReactiveRepository.findById(ingredientCommand.getUom().getId()).block());

                if (ingredientFound.getUom() == null) {
                    new RuntimeException("UOM NOT FOUND");
                }

            } else {

                // add new ingredient
                Ingredient ingredientz = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredientz);

            }

            // save the recipe
            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

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
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String idToDelete) {
        
        log.debug("Deleting ingredient: " + recipeId + ": " + idToDelete);

        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();

        if (recipe != null) {
            
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(idToDelete)).findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("ingredient found.");

                recipe.getIngredients().remove(ingredientOptional.get());

                recipeReactiveRepository.save(recipe).block();
            }
        } else {
            log.debug("recipe id not found!!!. Id: " + recipeId);
        }
        
        return Mono.empty();
    }

    
}
