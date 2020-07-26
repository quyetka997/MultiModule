package com.example.application.service;

import com.example.domain.primary.dto.TransportRequestDTO;
import com.example.domain.primary.entity.DtbTransportRequest;

import java.util.List;

public interface ITransportRequestService {

    TransportRequestDTO insert(TransportRequestDTO dtbTransportRequest);

    List<TransportRequestDTO> getAllPostRequest(Long goodId, String loadingAddress, String deliveryAddress);

    List<TransportRequestDTO> getPostedRequest();

    List<TransportRequestDTO> getReceivedRequest(Long id);

    void remove(long id);

}
