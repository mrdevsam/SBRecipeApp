package app.sbrecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    
}
