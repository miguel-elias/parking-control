package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ClientParkingDto;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ClientParkingModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ClientParkingService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<Object> getAllClientParking(@PageableDefault(page = 0, size = 10, sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(clientParkingService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClientParking(@PathVariable(value = "id") UUID id){
        Optional<ClientParkingModel> clientParkingModelOptional = clientParkingService.findById(id);
        if (!clientParkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client Parking not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientParkingModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClientParking(@PathVariable(value = "id") UUID id){
        Optional<ClientParkingModel> clientParkingModelOptional = clientParkingService.findById(id);
        if (!clientParkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client Parking not found.");
        }
        clientParkingService.delete(clientParkingModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Client Parking deleted successfully.");
    }

    @PutMapping("/{id}")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ResponseEntity<Object> updateClientParking(@PathVariable(value = "id") UUID id,@RequestBody @Valid ClientParkingDto clientParkingDto){
        Optional<ClientParkingModel> clientParkingModelOptional = clientParkingService.findById(id);
        if (!clientParkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client Parking not found.");
        }
        var clientParkingModel = clientParkingModelOptional.get();
        clientParkingModel.setName(clientParkingDto.getName());
        clientParkingModel.setCpf(clientParkingDto.getCpf());
        clientParkingModel.setEmail(clientParkingDto.getEmail());
        clientParkingModel.setPhone(clientParkingDto.getPhone());
        clientParkingModel.setApartment(clientParkingDto.getApartment());
        clientParkingModel.setBlock(clientParkingDto.getBlock());
        return ResponseEntity.status(HttpStatus.OK).body(clientParkingService.save(clientParkingModel));
    }

}
