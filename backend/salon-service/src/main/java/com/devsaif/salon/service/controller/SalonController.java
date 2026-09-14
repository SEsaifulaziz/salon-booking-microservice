package com.devsaif.salon.service.controller;

import com.devsaif.salon.service.mapper.SalonMapper;
import com.devsaif.salon.service.model.Salon;
import com.devsaif.salon.service.paload.dto.SalonDto;
import com.devsaif.salon.service.paload.dto.UserDto;
import com.devsaif.salon.service.service.SalonService;
import com.devsaif.salon.service.service.clients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;
    private final UserFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<SalonDto> createSalon(
            @RequestBody SalonDto salonDto,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {

        UserDto userDto = userFeignClient.getUserProfile(jwt).getBody();

        Salon salon = salonService.createSalon(salonDto, userDto);
        SalonDto salonDto1 = SalonMapper.mapToDto(salon);
        return new ResponseEntity<>(salonDto1, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<SalonDto>> getSalons() {

        List<Salon> salons = salonService.getSalons();

        List<SalonDto> salonDTOs = salons.stream().map((salon) ->
                 {
                      SalonDto salonDto1 = SalonMapper.mapToDto(salon);
                      return salonDto1;
                 }).toList();
        return new ResponseEntity<>(salonDTOs, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalonDto> updateSalon(
            @PathVariable Long id,
            @RequestBody SalonDto salonDto,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDto userDto = userFeignClient.getUserProfile(jwt).getBody();

        Salon salon = salonService.updateSalon(salonDto, userDto, id);
        SalonDto salonDto1 = SalonMapper.mapToDto(salon);
        return new ResponseEntity<>(salonDto1, HttpStatus.OK);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDto> getSalonById(@PathVariable Long salonId) throws Exception {

        Salon salon = salonService.getSalonById(salonId);
        SalonDto salonDto = SalonMapper.mapToDto(salon);
        return new ResponseEntity<>(salonDto, HttpStatus.OK);
    }


    //     http://localhost:5002/api/salons/search?city = abbottabad
    @GetMapping("/search")
    public ResponseEntity<List<SalonDto>> searchSalons(
            @RequestParam("city") String city) throws Exception {

        List<Salon> salons = salonService.searchSalonByCityName(city);

        List<SalonDto> salonDTOs = salons.stream().map((salon) ->
                {
                    SalonDto salonDto = SalonMapper.mapToDto(salon);
                    return salonDto;
                }
        ).toList();
        return new ResponseEntity<>(salonDTOs, HttpStatus.OK);
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDto> getSalonByOwnerId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

      UserDto userDto = userFeignClient.getUserProfile(jwt).getBody();

      if(userDto==null){
          throw new Exception("User not found from jwt...");
      }

      Salon salon = salonService.getSalonByOwnerId(userDto.getId());
      SalonDto salonDto = SalonMapper.mapToDto(salon);
      return new ResponseEntity<>(salonDto, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Salon> deleteAll(){
        salonService.DeleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Salon> deleteById(@PathVariable Long id) throws Exception {
        Salon salon = salonService.getSalonById(id);
        salonService.DeleteById(salon.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
