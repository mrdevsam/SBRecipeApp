package app.sbrecipeapp.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeReactiveRepository recipeRepository;
    
    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImgeFile(String recipeId, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId).map(recipe -> {

            Byte[] byteObjects = new Byte[0];

            try {
                byteObjects = new Byte[file.getBytes().length];

                int i = 0;

                for(byte b : file.getBytes()) {
                    byteObjects[i++] = b;
                }

                recipe.setImage(byteObjects);
                return recipe;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
        
    }
    
}
