package com.devsaif.category.service.controller;

import com.devsaif.category.service.model.Category;
import com.devsaif.category.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/salonId/{id}")
    public ResponseEntity<Set<Category>> getCategoriesBySalon(@PathVariable Long id) {
        Set<Category> categories = categoryService.getAllCategoriesBySalonId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws Exception {
        Category categories = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCategories() {
        categoryService.deleteAllCategories();
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> getAll = categoryService.getAll();
        return new ResponseEntity<>(getAll, HttpStatus.OK);
    }
}
