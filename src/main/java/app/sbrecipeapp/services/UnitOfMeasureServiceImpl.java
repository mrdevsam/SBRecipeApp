package app.sbrecipeapp.services;

import reactor.core.publisher.Flux;

import org.springframework.stereotype.Service;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import app.sbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
            UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        // return StreamSupport.stream(unitOfMeasureReactiveRepository.findAll().spliterator(), false)
        //         .map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());

        return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }

}
