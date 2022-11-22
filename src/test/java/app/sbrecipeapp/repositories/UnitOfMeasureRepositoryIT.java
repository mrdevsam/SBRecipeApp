package app.sbrecipeapp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.sbrecipeapp.bootstrap.RecipeBootstrap;
import app.sbrecipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
// @Disabled
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository uMeasureRepository;

    @BeforeEach
    void setUp() throws Exception{
        categoryRepository.deleteAll();
        uMeasureRepository.deleteAll();
        recipeRepository.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, uMeasureRepository);

        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    void testFindByDescription() throws Exception{
        Optional<UnitOfMeasure> uOptional = uMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", uOptional.get().getDescription());
    }

    @Test
    void testFindByDescriptionCup() throws Exception{
        Optional<UnitOfMeasure> uOptional = uMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", uOptional.get().getDescription());
    }
}
