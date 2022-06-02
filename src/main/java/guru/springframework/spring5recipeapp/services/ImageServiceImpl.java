/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vijayakumar
 * @Since  25-Apr-2022
 *
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile multipartFile) {
		
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
		
			Byte[] byteObject = new Byte[multipartFile.getBytes().length];
			
			int i = 0;
			
			for (byte b : multipartFile.getBytes()) {
				byteObject[i++] = b;
			}
			
			recipe.setImage(byteObject);
			
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error occurred.");
			e.printStackTrace();
		}
	}
}
