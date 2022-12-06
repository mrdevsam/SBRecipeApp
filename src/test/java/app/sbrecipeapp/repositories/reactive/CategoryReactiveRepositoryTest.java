package app.sbrecipeapp.repositories.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.sbrecipeapp.domain.Category;

//this test will run fine, but keeping it disabled for circleci builds
@Disabled
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	@Autowired
	CategoryReactiveRepository catReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		catReactiveRepository.deleteAll().block();
	}

	@Test
	void testSave() throws Exception {
		Category category = new Category();
		category.setDescription("Foo");

		catReactiveRepository.save(category).block();
		Long count = catReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	void testFindByDescription() throws Exception {
		Category category = new Category();
		category.setDescription("Foo");
		
		catReactiveRepository.save(category).then().block();
		Category fetchedCategory = catReactiveRepository.findByDescription("Foo").block();

		assertNotNull(fetchedCategory.getId());
	}
}
