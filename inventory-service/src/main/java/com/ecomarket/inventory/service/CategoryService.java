package com.ecomarket.inventory.service;

import com.ecomarket.inventory.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
