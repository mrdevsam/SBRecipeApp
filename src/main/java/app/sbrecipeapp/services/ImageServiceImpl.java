package app.sbrecipeapp.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;
    
    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImgeFile(String recipeId, MultipartFile file) {
        
        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] btyeObBytes = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                btyeObBytes[i++] = b;
            }

            recipe.setImage(btyeObBytes);
            recipeRepository.save(recipe);
            
            log.debug("image saved.");
        } catch (IOException e) {
            // TODO: handle better
            log.error("error occured. ", e);
            
            e.printStackTrace();
        }
    }
    
}
