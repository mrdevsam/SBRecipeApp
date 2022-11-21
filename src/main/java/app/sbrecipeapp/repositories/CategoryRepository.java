package app.sbrecipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, String>{
    Optional<Category> findByDescription(String description);
}
