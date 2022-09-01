package app.sbrecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import app.sbrecipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
    
}
