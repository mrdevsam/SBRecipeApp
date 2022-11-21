package app.sbrecipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String>{
    Optional<Recipe> findByDescription(String description);
}
