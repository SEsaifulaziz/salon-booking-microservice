package com.devsaif.category.service.service;

import com.devsaif.category.service.dto.SalonDto;
import com.devsaif.category.service.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category createCategory(Category category, SalonDto salonDto);

    Category updateCategory(Category category, SalonDto salonDto);

    Set<Category> getAllCategoriesBySalonId(Long id);

    Category getCategoryById(Long categoryId) throws Exception;

    void deleteCategoryById(Long id, Long salonId) throws Exception;

    void deleteAllCategories();
}
