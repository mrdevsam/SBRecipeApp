package app.sbrecipeapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    @Override
    public void saveImgeFile(Long recipeId, MultipartFile file) {
        
        log.debug("Received a file.");
    }
    
}
