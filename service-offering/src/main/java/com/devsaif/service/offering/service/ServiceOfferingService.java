package com.devsaif.service.offering.service;


import com.devsaif.service.offering.dto.CategoryDTO;
import com.devsaif.service.offering.dto.SalonDTO;
import com.devsaif.service.offering.dto.ServiceDTO;
import com.devsaif.service.offering.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering create(SalonDTO salondto, ServiceDTO servicedto, CategoryDTO categoryDto);

    ServiceOffering update(Long serviceId, ServiceOffering service);

    Set<ServiceOffering> getAllServiceBySalonId(Long serviceId, Long categoryId);

    void deleteById(Long id);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);


}
