package com.devsaif.service.offering.controller;


import com.devsaif.service.offering.model.ServiceOffering;
import com.devsaif.service.offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServiceById(@PathVariable Long salonId,
                                                               @RequestParam(required = false)
                                                               Long categoryId) throws Exception{

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(@PathVariable Long id) throws Exception{
        ServiceOffering serviceOffering = serviceOfferingService.getServiceById(id);
        return new ResponseEntity<>(serviceOffering, HttpStatus.OK);
    }


    @GetMapping("/List/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServiceByIds(@PathVariable Set<Long> ids) throws Exception{
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServiceByIds(ids);
        return new ResponseEntity<>(serviceOfferings, HttpStatus.OK);
    }


}
