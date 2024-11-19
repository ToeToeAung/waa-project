package edu.miu.waa.online_market.controller;

import edu.miu.waa.online_market.entity.Category;
import edu.miu.waa.online_market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping()
    public List<Category> findAll(){
        return categoryService.findAll();
    }
    @PostMapping()
    public void createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
    }
}
