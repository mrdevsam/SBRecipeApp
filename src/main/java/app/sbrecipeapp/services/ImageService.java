package app.sbrecipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    
    void saveImgeFile(String recipeId, MultipartFile file);
}
