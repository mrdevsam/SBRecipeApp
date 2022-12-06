package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import reactor.core.publisher.Mono;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.converters.IngredientCommandToIngredient;
import app.sbrecipeapp.converters.IngredientToIngredientCommand;
import app.sbrecipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import app.sbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import app.sbrecipeapp.domain.Ingredient;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;
import app.sbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import app.sbrecipeapp.repositories.reactive.RecipeReactiveRepository;

public class IngredientServiceTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    //init converters
    public IngredientServiceTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }
    
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeReactiveRepository, recipeRepository, unitOfMeasureReactiveRepository);
    }

    @Test
    void testFindByRecipeIdAndIngredientId() throws Exception{
        //given
        Recipe recipe = new Recipe();
        Ingredient ing1 = new Ingredient();
        Ingredient ing2 = new Ingredient();
        Ingredient ing3 = new Ingredient();

        recipe.setId("1");
        ing1.setId("1");
        ing2.setId("1");
        ing3.setId("3");

        recipe.addIngredient(ing1);
        recipe.addIngredient(ing2);
        recipe.addIngredient(ing3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

        //then
        assertEquals(String.valueOf(3L), ingredientCommand.getId());
        //assertEquals(String.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");
        command.setUom(new UnitOfMeasureCommand());
        command.getUom().setId("1234");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals(String.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    void testDeleteById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        //ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById("1", "3");

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }

    
}
