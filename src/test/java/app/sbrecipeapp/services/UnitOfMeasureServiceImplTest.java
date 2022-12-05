package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import reactor.core.publisher.Flux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import app.sbrecipeapp.domain.UnitOfMeasure;
import app.sbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand uCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureReactiveRepository repository;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        service = new UnitOfMeasureServiceImpl(repository, uCommand);
    }

    @Test
    void testListAllUoms() throws Exception{

        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");
        unitOfMeasures.add(uom2);

        when(repository.findAll()).thenReturn(Flux.just(uom1, uom2));

        //when
        List<UnitOfMeasureCommand> commands = service.listAllUoms().collectList().block();

        //then
        assertEquals(2, commands.size());
        verify(repository, times(1)).findAll();
    }
}
