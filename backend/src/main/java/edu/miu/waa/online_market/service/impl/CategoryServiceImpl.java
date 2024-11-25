package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Category;
import edu.miu.waa.online_market.repo.CategoryRepo;
import edu.miu.waa.online_market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    @Override
    public void createCategory(Category category){
        Category  newCategory = new Category(category.getName());
        categoryRepo.save(category);
    }
    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll().stream()
                .sorted(Comparator.comparing(Category::getName))
                .toList();
    }
    @Override
    public Category findById(Long id){
        return categoryRepo.findById(id).orElse(null);
    }
    @Override
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }
}
