package app.sbrecipeapp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.sbrecipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository uMeasureRepository;

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
