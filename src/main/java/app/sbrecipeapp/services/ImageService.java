package app.sbrecipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface ImageService {
    
    Mono<Void> saveImgeFile(String recipeId, MultipartFile file);
}
