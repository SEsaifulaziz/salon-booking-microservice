package com.devsaif.service.offering.service.impl;

import com.devsaif.service.offering.dto.CategoryDTO;
import com.devsaif.service.offering.dto.SalonDTO;
import com.devsaif.service.offering.dto.ServiceDTO;
import com.devsaif.service.offering.model.ServiceOffering;
import com.devsaif.service.offering.repository.ServiceOfferingRepository;
import com.devsaif.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {


    private final ServiceOfferingRepository serviceOfferingRepo;


    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {

        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());

        return serviceOfferingRepo.save(serviceOffering);
    }

    @Override
    public ServiceOffering update(Long serviceId, ServiceOffering service) {
        return null;
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long serviceId, Long categoryId) {
        return Set.of();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        return Set.of();
    }
}
