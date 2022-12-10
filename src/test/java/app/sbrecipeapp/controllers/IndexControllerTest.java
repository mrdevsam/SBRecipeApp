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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

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

        when(rService.getRecipes()).thenReturn(Flux.empty());

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
        recipe.setId(String.valueOf("1"));
        recipes.add(recipe);

        when(rService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        ArgumentCaptor<List<Recipe>> aCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String iValue = iController.getIndexpage(model);

        //then
        assertEquals("index", iValue);
        verify(rService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), aCaptor.capture());
        List<Recipe> setInController = aCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}
