/**
 * 
 */
package guru.springframework.spring5recipeapp.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

/**
 * @author vijayakumar
 * @Since  31-Jan-2022
 *
 */
@Controller
public class IndexController {

	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@RequestMapping({"","/","/index"})
	public String getIndexPage() {
		Optional<Category> category = categoryRepository.findByDescription("American");
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByUom("Tablespoon");
		
		System.out.println("Category Id is " + category.get().getId());
		System.out.println("UOM Id is " + uom.get().getId());
		return "index";
	}
}
