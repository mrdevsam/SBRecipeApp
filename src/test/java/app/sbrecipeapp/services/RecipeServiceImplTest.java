package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;

public class RecipeServiceImplTest {

    RecipeServiceImpl rServiceImpl;

    @Mock
    RecipeRepository repository;//declaring the repository as a Mock

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);//initializing Mokito Mock

        rServiceImpl = new RecipeServiceImpl(repository);
    } 

    @Test
    void testGetRecipes() throws Exception{
        Recipe recipe = new Recipe();
        HashSet rData = new HashSet();
        rData.add(recipe);

        when(repository.findAll()).thenReturn(rData);

        Set<Recipe> recipes = rServiceImpl.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(repository,times(1)).findAll();//verifing
    }
}
    
