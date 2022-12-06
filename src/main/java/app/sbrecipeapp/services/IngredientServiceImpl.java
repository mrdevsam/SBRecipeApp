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
import app.sbrecipeapp.repositories.RecipeRepository;
import app.sbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import app.sbrecipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
            IngredientCommandToIngredient ingredientCommandToIngredient, RecipeReactiveRepository recipeReactiveRepository ,RecipeRepository recipeRepository,
            UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        return recipeReactiveRepository.findById(recipeId)
                    .map(recipe -> recipe.getIngredients()
                        .stream()
                        .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                        .findFirst())
                    .filter(Optional::isPresent)
                    .map(ingredient -> {
                        IngredientCommand command = ingredientToIngredientCommand.convert(ingredient.get());
                        command.setRecipeId(recipeId);
                        return command;
                    });

        /* Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

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

        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipeId);

        return ingredientCommandOptional.get(); */
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            // toss error if id not found
            log.error("recipeID not found. recipeId: " + ingredientCommand.getRecipeId());

            return Mono.just(new IngredientCommand());
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
                ingredientFound.setUom(unitOfMeasureReactiveRepository.findById(ingredientCommand.getUom().getId())).block();
                        //.orElseThrow(() -> new RuntimeException("UOM NOT FOUND!!!")));// todo address this

                if (ingredientFound.getUom() == null) {
                    new RuntimeException("UOM NOT FOUND");
                }

            } else {

                // add new ingredient
                Ingredient ingredientz = ingredientCommandToIngredient.convert(ingredientCommand);
                //ingredientz.setRecipe(recipe);
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
            //return Mono.just(ingredientToIngredientCommand.convert(savedIngredientOptional.get()));
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String idToDelete) {
        
        log.debug("Deleting ingredient: " + recipeId + ": " + idToDelete);

        //Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeRepository.findById(recipeId).get();

        if (recipe != null) {
            //Recipe recipeFound = recipeOptional.get();
            //log.debug("recipe found.");

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(idToDelete)).findFirst();

            if (ingredientOptional.isPresent()) {
                log.debug("ingredient found.");

                //Ingredient ingredientToDelete = ingredientOptional.get();
                //ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());

                recipeRepository.save(recipe);
            }
        } else {
            log.debug("recipe id not found!!!. Id: " + recipeId);
        }
        
        return Mono.empty();
    }

    
}
