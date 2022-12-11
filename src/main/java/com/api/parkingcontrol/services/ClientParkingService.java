package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ClientParkingModel;
import com.api.parkingcontrol.repositories.ClientParkingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClientParkingService {

    final ClientParkingRepository clientParkingRepository;

    public ClientParkingService(ClientParkingRepository clientParkingRepository) {
        this.clientParkingRepository = clientParkingRepository;
    }

    @Transactional
    public Object save(ClientParkingModel clientParkingModel) {
        return clientParkingRepository.save(clientParkingModel);
    }
}
