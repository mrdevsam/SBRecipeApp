package app.sbrecipeapp.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import app.sbrecipeapp.domain.Category;
import app.sbrecipeapp.domain.UnitOfMeasure;
import app.sbrecipeapp.repositories.CategoryRepository;
import app.sbrecipeapp.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController {
    
    //constructor based dependency injection
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"" , "/" , "/index" , "index.html"})
    public String indexController() {

        Optional<Category> cOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> uOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category id :" + cOptional.get().getId());
        System.out.println("UOM id : " + uOptional.get().getId());
        
        return "index.html";
    }
}
