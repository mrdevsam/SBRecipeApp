package app.sbrecipeapp.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testGetDescription() throws Exception{

    }

    @Test
    void testGetId() throws Exception{
        String idValue= "4";

        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    void testGetRecipes() throws Exception{

    }
}
