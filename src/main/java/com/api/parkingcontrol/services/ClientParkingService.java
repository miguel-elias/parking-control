package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ClientParkingModel;
import com.api.parkingcontrol.repositories.ClientParkingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

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

    public Object findAll(Pageable pageable) {
        return clientParkingRepository.findAll(pageable);
    }

    public Optional<ClientParkingModel> findById(UUID id) {
        return clientParkingRepository.findById(id);
    }
}
