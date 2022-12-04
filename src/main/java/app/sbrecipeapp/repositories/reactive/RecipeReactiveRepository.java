package app.sbrecipeapp.repositories.reactive;

import app.sbrecipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}