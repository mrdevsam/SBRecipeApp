package app.sbrecipeapp.repositories.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.sbrecipeapp.domain.UnitOfMeasure;

//this test will run fine, but keeping it disabled for circleci builds
@Disabled
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
	private static final String EACH = "Each";

	@Autowired
	UnitOfMeasureReactiveRepository uomReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		uomReactiveRepository.deleteAll().block();
	}

	@Test
	void testSaveUom() throws Exception {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(EACH);

		uomReactiveRepository.save(uom).block();
		Long uCount = uomReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), uCount);
	}

	@Test
	void testFindByDescription() throws Exception {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(EACH);
		
		uomReactiveRepository.save(uom).block();
		UnitOfMeasure fetchedUOM = uomReactiveRepository.findByDescription(EACH).block();

		assertEquals(EACH, fetchedUOM.getDescription());
	}
}
