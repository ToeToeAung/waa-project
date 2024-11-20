package edu.miu.waa.online_market.service;

import edu.miu.waa.online_market.entity.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category);
    List<Category> findAll();
    Category findById(Long id);
    Category findByName(String name);
}
