package com.devsaif.category.service.controller;

import com.devsaif.category.service.dto.SalonDto;
import com.devsaif.category.service.model.Category;
import com.devsaif.category.service.service.CategoryService;
import com.devsaif.category.service.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt

    ) throws Exception{
        SalonDto salonDto = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        Category savedCategory = categoryService.createCategory(category,salonDto);
        return ResponseEntity.ok().body(savedCategory);
    }

    @GetMapping("/salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoryByIdAndSalon(
            @PathVariable Long id,
            @PathVariable Long salonId
    ) throws Exception {
        Category category = categoryService.findByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) throws Exception{
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        categoryService.deleteCategoryById(id,salonDto.getId());
        return ResponseEntity.ok().body("deleted category successfully");
    }


}
