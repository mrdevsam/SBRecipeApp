package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.converters.IngredientToIngredientCommand;
import app.sbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import app.sbrecipeapp.domain.Ingredient;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;

public class IngredientServiceTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    //init converters
    public IngredientServiceTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }
    
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
    }

    @Test
    void testFindByRecipeIdAndIngredientId() throws Exception{
        //given
        Recipe recipe = new Recipe();
        Ingredient ing1 = new Ingredient();
        Ingredient ing2 = new Ingredient();
        Ingredient ing3 = new Ingredient();

        recipe.setId(1L);
        ing1.setId(1L);
        ing2.setId(1L);
        ing3.setId(3L);

        recipe.addIngredient(ing1);
        recipe.addIngredient(ing2);
        recipe.addIngredient(ing3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
}
