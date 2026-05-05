package com.devsaif.salon.booking.microservices.repository;

import com.devsaif.salon.booking.microservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
