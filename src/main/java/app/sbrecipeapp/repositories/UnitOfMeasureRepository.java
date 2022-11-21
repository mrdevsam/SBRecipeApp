package app.sbrecipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
