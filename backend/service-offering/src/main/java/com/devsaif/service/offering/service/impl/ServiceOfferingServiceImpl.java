package com.devsaif.service.offering.service.impl;

import com.devsaif.service.offering.dto.CategoryDTO;
import com.devsaif.service.offering.dto.SalonDTO;
import com.devsaif.service.offering.dto.ServiceDTO;
import com.devsaif.service.offering.model.ServiceOffering;
import com.devsaif.service.offering.repository.ServiceOfferingRepository;
import com.devsaif.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingRepo.findById(serviceId).orElse(null);

        if(serviceOffering == null){
            throw new Exception("service not found");
        }

        serviceOffering.setImage(service.getImage());
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());

        return serviceOfferingRepo.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {

        Set<ServiceOffering> services = serviceOfferingRepo.getBySalonId(salonId);

        if(categoryId != null){
            services = services.stream().filter((service) ->
                    service.getCategoryId() !=null &&
                service.getCategoryId().equals(categoryId)).collect(Collectors.toSet());
        }
        return services;

    }


    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        List<ServiceOffering> services =  serviceOfferingRepo.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepo.findById(id).orElse(null);

        if(serviceOffering == null){
            throw new Exception("service not found");
        }
        return serviceOffering;
    }
}
