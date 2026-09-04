package com.devsaif.notifications.repository;

import com.devsaif.notifications.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserId(Long userId);
    List<NotificationEntity> findBySalonId(Long  salonId);

}