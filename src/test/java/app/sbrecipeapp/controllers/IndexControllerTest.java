package app.sbrecipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.services.RecipeService;

public class IndexControllerTest {
    IndexController iController;

    @Mock
    RecipeService rService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        iController = new IndexController(rService);
    }

    @Test
    void testMockmvc() throws Exception{
        MockMvc mMvc = MockMvcBuilders.standaloneSetup(iController).build();

        mMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
    }

    @Test
    void testGetIndexpage() {

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        when(rService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> aCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String iValue = iController.getIndexpage(model);

        //then
        assertEquals("index", iValue);
        verify(rService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), aCaptor.capture());
        Set<Recipe> setInController = aCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}
