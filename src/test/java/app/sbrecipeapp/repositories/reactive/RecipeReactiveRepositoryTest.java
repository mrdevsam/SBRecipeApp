package app.sbrecipeapp.repositories.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.sbrecipeapp.domain.Recipe;

//this test will run fine, but keeping it disabled for circleci builds
@Disabled
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		recipeReactiveRepository.deleteAll().block();
	}

	@Test
	void testSaveRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setDescription("Yummy");

		recipeReactiveRepository.save(recipe).block();
		Long rCount = recipeReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), rCount);
	}
}
