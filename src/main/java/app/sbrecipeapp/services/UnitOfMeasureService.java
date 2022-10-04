package app.sbrecipeapp.services;

import java.util.Set;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
    
    Set<UnitOfMeasureCommand> listAllUoms();
}
