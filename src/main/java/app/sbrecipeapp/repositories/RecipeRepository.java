package app.sbrecipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{
    Optional<Recipe> findByDescription(String description);
}
