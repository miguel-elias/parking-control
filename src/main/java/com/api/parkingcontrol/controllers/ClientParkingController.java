package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ClientParkingDto;
import com.api.parkingcontrol.models.ClientParkingModel;
import com.api.parkingcontrol.services.ClientParkingService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import javax.validation.Valid;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client-parking")
public class ClientParkingController {

    final ClientParkingService clientParkingService;

    public ClientParkingController(ClientParkingService clientParkingService) {
        this.clientParkingService = clientParkingService;
    }
    @PostMapping
    public ResponseEntity<Object> saveClientParking(@RequestBody @Valid ClientParkingDto clientParkingDto){
        var clientParkingModel = new ClientParkingModel();
        BeanUtils.copyProperties(clientParkingDto, clientParkingModel);
        clientParkingModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientParkingService.save(clientParkingModel));
    }


}