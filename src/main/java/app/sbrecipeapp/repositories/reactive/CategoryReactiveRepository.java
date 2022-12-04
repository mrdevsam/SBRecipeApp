package app.sbrecipeapp.repositories.reactive;

import app.sbrecipeapp.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

}