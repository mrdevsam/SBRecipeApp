package app.sbrecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{
    
}
