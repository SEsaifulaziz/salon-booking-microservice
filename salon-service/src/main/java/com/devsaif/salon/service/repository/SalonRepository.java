package com.devsaif.salon.service.repository;

import com.devsaif.salon.service.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon,Long> {

    Salon findByOwnerId(Long id);

    // Search salons by a keyword across multiple fields (city, name, address).
    // - Uses LIKE with %keyword% → matches partial text anywhere in the field
    // - Uses LOWER() → makes the search case-insensitive
    // - Returns salons where ANY of the fields contains the keyword

    @Query("""
        SELECT s FROM Salon s WHERE
        lower(s.city) LIKE lower(concat('%', :keyword, '%')) OR
        lower(s.name) LIKE lower(concat('%', :keyword, '%')) OR
        lower(s.address) LIKE lower(concat('%', :keyword, '%'))
        """)
    List<Salon> searchSalons(@Param("keyword") String keyword);



//    List<Salon> findByCityContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
//            String city, String name, String address
//    );




}
