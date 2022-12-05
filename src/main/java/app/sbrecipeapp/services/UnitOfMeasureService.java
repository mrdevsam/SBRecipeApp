package app.sbrecipeapp.services;

import reactor.core.publisher.Flux;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
    
    Flux<UnitOfMeasureCommand> listAllUoms();
}
