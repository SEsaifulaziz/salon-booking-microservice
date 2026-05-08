package com.devsaif.category.service.repository;

import com.devsaif.category.service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryService extends JpaRepository<Category,Long> {

    List<Category> findBySalonId(Long id);
}
