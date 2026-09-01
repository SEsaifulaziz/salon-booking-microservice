package com.devsaif.category.service.repository;

import com.devsaif.category.service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Set<Category> findBySalonId(Long id);
    Category findByIdAndSalonId(Long id,Long salonId);
}
