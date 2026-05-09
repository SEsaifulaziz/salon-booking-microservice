package com.devsaif.category.service.service.impl;

import com.devsaif.category.service.dto.SalonDto;
import com.devsaif.category.service.model.Category;
import com.devsaif.category.service.repository.CategoryRepository;
import com.devsaif.category.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    @Override
    public Category createCategory(Category category, SalonDto salonDto) {
        Category  newCategory = new Category();

        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDto.getId());
        return categoryRepo.save(newCategory);
    }

    @Override
    public Category updateCategory(Category category, SalonDto salonDto) {
        return null;
    }

    @Override
    public Set<Category> getAllCategoriesBySalonId(Long id) {
        return categoryRepo.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws Exception {
        Category category = categoryRepo.findById(categoryId).orElse(null);

        if (category == null) {
            throw new Exception("category not found with id" + categoryId);
        }

        return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {
        Category category = getCategoryById(id);

        if(!category.getSalonId().equals(salonId)){
            throw new Exception("you don't have permission to delete this Category");
        }
        categoryRepo.deleteById(id);
    }

    @Override
    public void deleteAllCategories() {
        categoryRepo.deleteAll();
    }
}
