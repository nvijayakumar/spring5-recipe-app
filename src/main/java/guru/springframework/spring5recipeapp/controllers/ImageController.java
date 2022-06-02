/**
 * 
 */
package guru.springframework.spring5recipeapp.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;

/**
 * @author vijayakumar
 * @Since  25-Apr-2022
 *
 */

@Controller
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;
	
	public ImageController(RecipeService recipeService, ImageService imageService) {
		this.recipeService = recipeService;
		this.imageService = imageService;
	}
	
	@GetMapping("/recipe/{recipeId}/image")
	public String getImageForm(@PathVariable Long recipeId, Model model) {
		
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		
		return "recipe/imageuploadform";
	}
	
	@PostMapping("/recipe/{recipeId}/image")
	public String saveImageFile(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {
		
		imageService.saveImageFile(recipeId, multipartFile);
		
		return "redirect:/recipe/"+recipeId+"/show";
	}
	
	@GetMapping("/recipe/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
		
		if (recipeCommand.getImage() != null) {
			byte[] byteArray = new byte[recipeCommand.getImage().length];
			
			int i=0;
			for (Byte b : recipeCommand.getImage()) {
				byteArray[i++] = b;	//auto unboxing
			}
			
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			IOUtils.copy(is, response.getOutputStream());
		}
	}
}
