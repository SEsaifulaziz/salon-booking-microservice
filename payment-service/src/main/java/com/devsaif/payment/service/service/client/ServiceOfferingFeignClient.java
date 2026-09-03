package com.devsaif.payment.service.service.client;

import com.devsaif.payment.service.payload.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient("SERVICE-OFFERING")
public interface ServiceOfferingFeignClient {


    @GetMapping("/api/service-offering/List/{ids}")
    public ResponseEntity<Set<ServiceDTO>> getServiceByIds(@PathVariable Set<Long> ids);
}
