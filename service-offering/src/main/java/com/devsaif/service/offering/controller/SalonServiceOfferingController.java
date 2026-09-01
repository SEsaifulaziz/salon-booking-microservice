package com.devsaif.service.offering.controller;


import com.devsaif.service.offering.dto.CategoryDTO;
import com.devsaif.service.offering.dto.SalonDTO;
import com.devsaif.service.offering.dto.ServiceDTO;
import com.devsaif.service.offering.model.ServiceOffering;
import com.devsaif.service.offering.service.ServiceOfferingService;
import com.devsaif.service.offering.service.client.CategoryFeignClient;
import com.devsaif.service.offering.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient  categoryFeignClient;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDTO salonDTO = salonFeignClient
                .getSalonByOwnerId(jwt).getBody();

        CategoryDTO categoryDTO = categoryFeignClient
                .getCategoriesByIdAndSalon(serviceDTO.getCategoryId(), salonDTO.getId()).getBody();

        ServiceOffering serviceOfferings = serviceOfferingService
                .createService(salonDTO, serviceDTO, categoryDTO);

        return ResponseEntity.ok(serviceOfferings);
    }

    @PatchMapping("/{serviceId}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long serviceId, @RequestBody ServiceOffering serviceOffering) throws Exception {

        ServiceOffering updateServiceOffering = serviceOfferingService.updateService(serviceId, serviceOffering);

        return ResponseEntity.ok(updateServiceOffering);
    }
}
