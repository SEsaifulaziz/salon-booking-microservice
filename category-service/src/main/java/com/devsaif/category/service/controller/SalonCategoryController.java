package com.devsaif.category.service.controller;

import com.devsaif.category.service.dto.SalonDto;
import com.devsaif.category.service.model.Category;
import com.devsaif.category.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) throws Exception{
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        Category savedCategory = categoryService.createCategory(category,salonDto);
        return ResponseEntity.ok().body(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) throws Exception{
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        categoryService.deleteCategoryById(id,salonDto.getId());
        return ResponseEntity.ok().body("deleted category successfully");
    }

}
