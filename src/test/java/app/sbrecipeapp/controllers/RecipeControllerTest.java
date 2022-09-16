package app.sbrecipeapp.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.services.RecipeService;

public class RecipeControllerTest {

    RecipeController controller;

    @Mock
    RecipeService rService;
    
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);

        controller = new RecipeController(rService);
    }

    @Test
    void testShowById() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(rService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/1")).andExpect(status().isOk()).andExpect(view().name("recipe/show")).andExpect(model().attributeExists("recipe"));
    }
}
